package com.example.data.local

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface KnowledgeBaseDao {
  @Query("SELECT * FROM knowledge_base ORDER BY lastModified DESC")
  fun getAllKnowledge(): Flow<List<KnowledgeBaseEntity>>

  @Query("SELECT * FROM knowledge_base WHERE status = :status ORDER BY lastModified DESC")
  fun getKnowledgeByStatus(status: KnowledgeStatus): Flow<List<KnowledgeBaseEntity>>

  @Query("SELECT * FROM knowledge_base WHERE id = :id")
  suspend fun getKnowledgeById(id: Long): KnowledgeBaseEntity?

  @Query("SELECT * FROM knowledge_base WHERE intentKey = :intentKey AND status = 'ACTIVE' LIMIT 1")
  suspend fun getKnowledgeByIntent(intentKey: String): KnowledgeBaseEntity?

  @Query("SELECT * FROM knowledge_base WHERE status = 'ACTIVE'")
  suspend fun getActiveKnowledgeList(): List<KnowledgeBaseEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertKnowledge(entry: KnowledgeBaseEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertKnowledgeList(entries: List<KnowledgeBaseEntity>)

  @Update
  suspend fun updateKnowledge(entry: KnowledgeBaseEntity)

  @Delete
  suspend fun deleteKnowledge(entry: KnowledgeBaseEntity)

  @Query("DELETE FROM knowledge_base WHERE id = :id")
  suspend fun deleteKnowledgeById(id: Long)
}

@Dao
interface AppointmentDao {
  @Query("SELECT * FROM appointments ORDER BY createdAt DESC")
  fun getAllAppointments(): Flow<List<AppointmentEntity>>

  @Query("SELECT * FROM appointments WHERE status = :status ORDER BY createdAt DESC")
  fun getAppointmentsByStatus(status: AppointmentStatus): Flow<List<AppointmentEntity>>

  @Query("SELECT * FROM appointments WHERE id = :id")
  suspend fun getAppointmentById(id: Long): AppointmentEntity?

  @Query("SELECT * FROM appointments WHERE clientName LIKE '%' || :query || '%' OR phoneNumber LIKE '%' || :query || '%' OR referenceNumber LIKE '%' || :query || '%' ORDER BY createdAt DESC")
  fun searchAppointments(query: String): Flow<List<AppointmentEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAppointment(appointment: AppointmentEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAppointmentsList(list: List<AppointmentEntity>)

  @Update
  suspend fun updateAppointment(appointment: AppointmentEntity)

  @Query("UPDATE appointments SET status = :newStatus WHERE id = :id")
  suspend fun updateStatus(id: Long, newStatus: AppointmentStatus)

  @Query("UPDATE appointments SET paymentStatus = :newPaymentStatus WHERE id = :id")
  suspend fun updatePaymentStatus(id: Long, newPaymentStatus: PaymentStatus)

  @Query("UPDATE appointments SET adminPrivateNotes = :notes WHERE id = :id")
  suspend fun updateNotes(id: Long, notes: String)

  @Query("UPDATE appointments SET preferredDate = :newDate, preferredTimeSlot = :newSlot, status = 'RESCHEDULED' WHERE id = :id")
  suspend fun rescheduleAppointment(id: Long, newDate: String, newSlot: String)

  @Delete
  suspend fun deleteAppointment(appointment: AppointmentEntity)

  @Query("DELETE FROM appointments WHERE id = :id")
  suspend fun deleteAppointmentById(id: Long)
}

@Dao
interface ServiceDao {
  @Query("SELECT * FROM astrology_services ORDER BY displayOrder ASC")
  fun getAllServices(): Flow<List<ServiceEntity>>

  @Query("SELECT * FROM astrology_services WHERE isActive = 1 ORDER BY displayOrder ASC")
  fun getActiveServices(): Flow<List<ServiceEntity>>

  @Query("SELECT * FROM astrology_services WHERE id = :id")
  suspend fun getServiceById(id: String): ServiceEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertService(service: ServiceEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertServicesList(list: List<ServiceEntity>)

  @Update
  suspend fun updateService(service: ServiceEntity)

  @Delete
  suspend fun deleteService(service: ServiceEntity)

  @Query("DELETE FROM astrology_services WHERE id = :id")
  suspend fun deleteServiceById(id: String)
}

@Dao
interface CustomerDao {
  @Query("SELECT * FROM customers ORDER BY createdAt DESC")
  fun getAllCustomers(): Flow<List<CustomerEntity>>

  @Query("SELECT * FROM customers WHERE id = :id")
  suspend fun getCustomerById(id: Long): CustomerEntity?

  @Query("SELECT * FROM customers WHERE phoneNumber = :phone LIMIT 1")
  suspend fun getCustomerByPhone(phone: String): CustomerEntity?

  @Query("SELECT * FROM customers WHERE name LIKE '%' || :query || '%' OR phoneNumber LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' ORDER BY createdAt DESC")
  fun searchCustomers(query: String): Flow<List<CustomerEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomer(customer: CustomerEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomersList(list: List<CustomerEntity>)

  @Update
  suspend fun updateCustomer(customer: CustomerEntity)

  @Delete
  suspend fun deleteCustomer(customer: CustomerEntity)

  @Query("DELETE FROM customers WHERE id = :id")
  suspend fun deleteCustomerById(id: Long)
}

@Dao
interface PaymentDao {
  @Query("SELECT * FROM payments ORDER BY timestamp DESC")
  fun getAllPayments(): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE status = :status ORDER BY timestamp DESC")
  fun getPaymentsByStatus(status: PaymentStatus): Flow<List<PaymentEntity>>

  @Query("SELECT * FROM payments WHERE appointmentId = :appointmentId LIMIT 1")
  suspend fun getPaymentByAppointmentId(appointmentId: Long): PaymentEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPayment(payment: PaymentEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPaymentsList(list: List<PaymentEntity>)

  @Update
  suspend fun updatePayment(payment: PaymentEntity)

  @Query("UPDATE payments SET status = :status WHERE id = :id")
  suspend fun updateStatus(id: Long, status: PaymentStatus)

  @Delete
  suspend fun deletePayment(payment: PaymentEntity)

  @Query("DELETE FROM payments WHERE id = :id")
  suspend fun deletePaymentById(id: Long)
}

@Dao
interface AdminNotificationDao {
  @Query("SELECT * FROM admin_notifications ORDER BY timestamp DESC")
  fun getAllNotifications(): Flow<List<AdminNotificationEntity>>

  @Query("SELECT COUNT(*) FROM admin_notifications WHERE isRead = 0")
  fun getUnreadCount(): Flow<Int>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotification(notification: AdminNotificationEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotificationsList(list: List<AdminNotificationEntity>)

  @Query("UPDATE admin_notifications SET isRead = 1 WHERE id = :id")
  suspend fun markAsRead(id: Long)

  @Query("UPDATE admin_notifications SET isRead = 1")
  suspend fun markAllAsRead()

  @Query("DELETE FROM admin_notifications")
  suspend fun clearAll()
}

@Dao
interface WhatsAppDao {
  @Query("SELECT * FROM whatsapp_conversations ORDER BY timestamp DESC")
  fun getAllWhatsAppConversations(): Flow<List<WhatsAppConversationEntity>>

  @Query("SELECT * FROM whatsapp_conversations WHERE status = :status ORDER BY timestamp DESC")
  fun getWhatsAppByStatus(status: WhatsAppReviewStatus): Flow<List<WhatsAppConversationEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertWhatsAppConversation(item: WhatsAppConversationEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertWhatsAppList(list: List<WhatsAppConversationEntity>)

  @Update
  suspend fun updateWhatsAppConversation(item: WhatsAppConversationEntity)

  @Query("UPDATE whatsapp_conversations SET status = :newStatus WHERE id = :id")
  suspend fun updateReviewStatus(id: Long, newStatus: WhatsAppReviewStatus)

  @Query("DELETE FROM whatsapp_conversations WHERE id = :id")
  suspend fun deleteById(id: Long)
}

@Dao
interface ChatMessageDao {
  @Query("SELECT * FROM chat_messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
  fun getMessages(conversationId: String): Flow<List<ChatMessageEntity>>

  @Query("SELECT * FROM chat_messages ORDER BY timestamp DESC")
  fun getAllHistory(): Flow<List<ChatMessageEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMessage(message: ChatMessageEntity): Long

  @Query("DELETE FROM chat_messages WHERE conversationId = :conversationId")
  suspend fun clearSession(conversationId: String)
}

@Dao
interface ConfigDao {
  @Query("SELECT * FROM astrologer_config WHERE id = 1 LIMIT 1")
  fun getConfig(): Flow<AstrologerPracticeConfig?>

  @Query("SELECT * FROM astrologer_config WHERE id = 1 LIMIT 1")
  suspend fun getConfigOnce(): AstrologerPracticeConfig?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertConfig(config: AstrologerPracticeConfig)
}

