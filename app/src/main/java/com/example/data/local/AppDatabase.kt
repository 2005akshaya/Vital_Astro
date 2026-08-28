package com.example.data.local

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Converters {
  @TypeConverter
  fun fromKnowledgeStatus(status: KnowledgeStatus): String = status.name

  @TypeConverter
  fun toKnowledgeStatus(value: String): KnowledgeStatus =
    try { KnowledgeStatus.valueOf(value) } catch (e: Exception) { KnowledgeStatus.ACTIVE }

  @TypeConverter
  fun fromAppointmentStatus(status: AppointmentStatus): String = status.name

  @TypeConverter
  fun toAppointmentStatus(value: String): AppointmentStatus =
    try { AppointmentStatus.valueOf(value) } catch (e: Exception) { AppointmentStatus.PENDING }

  @TypeConverter
  fun fromConsultationMode(mode: ConsultationMode): String = mode.name

  @TypeConverter
  fun toConsultationMode(value: String): ConsultationMode =
    try { ConsultationMode.valueOf(value) } catch (e: Exception) { ConsultationMode.DIRECT_VISIT }

  @TypeConverter
  fun fromPaymentStatus(status: PaymentStatus): String = status.name

  @TypeConverter
  fun toPaymentStatus(value: String): PaymentStatus =
    try { PaymentStatus.valueOf(value) } catch (e: Exception) { PaymentStatus.PENDING }

  @TypeConverter
  fun fromWhatsAppReviewStatus(status: WhatsAppReviewStatus): String = status.name

  @TypeConverter
  fun toWhatsAppReviewStatus(value: String): WhatsAppReviewStatus =
    try { WhatsAppReviewStatus.valueOf(value) } catch (e: Exception) { WhatsAppReviewStatus.PENDING_REVIEW }

  @TypeConverter
  fun fromMessageSender(sender: MessageSender): String = sender.name

  @TypeConverter
  fun toMessageSender(value: String): MessageSender =
    try { MessageSender.valueOf(value) } catch (e: Exception) { MessageSender.ASSISTANT }
}

@Database(
  entities = [
    KnowledgeBaseEntity::class,
    AppointmentEntity::class,
    WhatsAppConversationEntity::class,
    ChatMessageEntity::class,
    AstrologerPracticeConfig::class,
    ServiceEntity::class,
    CustomerEntity::class,
    PaymentEntity::class,
    AdminNotificationEntity::class
  ],
  version = 4,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun knowledgeBaseDao(): KnowledgeBaseDao
  abstract fun appointmentDao(): AppointmentDao
  abstract fun whatsAppDao(): WhatsAppDao
  abstract fun chatMessageDao(): ChatMessageDao
  abstract fun configDao(): ConfigDao
  abstract fun serviceDao(): ServiceDao
  abstract fun customerDao(): CustomerDao
  abstract fun paymentDao(): PaymentDao
  abstract fun adminNotificationDao(): AdminNotificationDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "tamil_astrology_assistant_db"
        )
          .fallbackToDestructiveMigration(dropAllTables = true)
          .addCallback(DatabaseCallback(scope))
          .build()
        INSTANCE = instance
        instance
      }
    }
  }

  private class DatabaseCallback(
    private val scope: CoroutineScope
  ) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      INSTANCE?.let { database ->
        scope.launch(Dispatchers.IO) {
          populateInitialData(database)
        }
      }
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
      super.onDestructiveMigration(db)
      INSTANCE?.let { database ->
        scope.launch(Dispatchers.IO) {
          populateInitialData(database)
        }
      }
    }

    suspend fun populateInitialData(database: AppDatabase) {
      database.knowledgeBaseDao().insertKnowledgeList(InitialDataProvider.defaultKnowledgeBase)
      database.configDao().insertConfig(AstrologerPracticeConfig())
      database.serviceDao().insertServicesList(InitialDataProvider.defaultServices)
      database.customerDao().insertCustomersList(InitialDataProvider.defaultCustomers)
      database.paymentDao().insertPaymentsList(InitialDataProvider.defaultPayments)
      database.adminNotificationDao().insertNotificationsList(InitialDataProvider.defaultNotifications)
      database.whatsAppDao().insertWhatsAppList(InitialDataProvider.defaultWhatsAppConversations)
      for (appointment in InitialDataProvider.defaultAppointments) {
        database.appointmentDao().insertAppointment(appointment)
      }
      // Insert initial welcome message in chat history
      database.chatMessageDao().insertMessage(
        ChatMessageEntity(
          conversationId = "default_session",
          sender = MessageSender.ASSISTANT,
          text = "வணக்கம் 🙏\nஸ்ரீ விட்டல் ஜோதிடாலயத்திற்கு தங்களை அன்புடன் வரவேற்கிறோம்.\n\nஜோதிட ஸ்ரீ ராஜகோபால் அவர்களின் ஆலோசனை கட்டணம், தொடர்பு நேரம், முன்பதிவு முறை, சேவைகள் அல்லது அலுவலக முகவரி குறித்த தகவல்களை அறிய எவ்வாறு உதவலாம்?",
          intentKey = "WELCOME"
        )
      )
    }
  }
}

