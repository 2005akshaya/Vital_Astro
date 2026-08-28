package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.*
import com.example.data.repository.AnalyticsSummary
import com.example.data.repository.AstrologyAssistantRepository
import com.example.ui.util.AppStrings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class AppScreenTab(val labelTamil: String, val labelEnglish: String) {
  HOME("முகப்பு", "Home"),
  CHAT("AI உதவியாளர்", "AI Assistant"),
  SERVICES("சேவைகள்", "Services"),
  APPOINTMENT("முன்பதிவு", "Booking"),
  FAQ("பொதுவான கேள்விகள்", "FAQ"),
  PREPARATION("ஆலோசனை தயாரிப்பு", "Prep Guide"),
  ADMIN("நிர்வாகம் (அப்பா)", "Admin (Father)")
}

enum class AdminSubTab(val labelTamil: String, val labelEnglish: String, val iconName: String) {
  HOME("முகப்பு", "Home", "dashboard"),
  APPOINTMENTS("முன்பதிவுகள்", "Appointments", "event_note"),
  CALENDAR("நாட்காட்டி", "Calendar", "calendar_month"),
  CUSTOMERS("வாடிக்கையாளர்கள்", "Customers", "people"),
  SERVICES("சேவைகள் & கட்டணம்", "Services", "miscellaneous_services"),
  PAYMENTS("வருமானம்", "Revenue", "payments"),
  COMMUNICATION("செய்திகள்", "Quick Connect", "chat"),
  REPORTS("அறிக்கைகள்", "Reports", "insights"),
  KNOWLEDGE_BASE("அறிவுத் தளம்", "AI Knowledge", "psychology"),
  WHATSAPP_DATA("WhatsApp தரவு", "WhatsApp Sync", "forum"),
  SETTINGS("அமைப்புகள்", "Settings", "settings")
}

enum class AdminAppointmentFilter(val labelTamil: String, val labelEnglish: String) {
  ALL("அனைத்தும்", "All"),
  TODAY("இன்று", "Today"),
  UPCOMING("வரவிருப்பவை", "Upcoming"),
  PENDING("நிலுவை", "Pending"),
  CONFIRMED("உறுதிசெய்யப்பட்டவை", "Confirmed"),
  COMPLETED("நிறைவுற்றவை", "Completed"),
  CANCELLED("ரத்து", "Cancelled")
}

data class AppointmentFormState(
  val id: Long = 0L,
  val clientName: String = "",
  val phoneNumber: String = "",
  val email: String = "",
  val dateOfBirth: String = "",
  val timeOfBirth: String = "",
  val placeOfBirth: String = "",
  val serviceType: String = "பொது ஜாதக ஆலோசனை",
  val preferredDate: String = "",
  val preferredTimeSlot: String = "முற்பகல் 11:00 - 12:00",
  val consultationMode: ConsultationMode = ConsultationMode.DIRECT_VISIT,
  val birthDetailsNotes: String = "",
  val adminNotes: String = "",
  val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
  val paymentMethod: String = "GPay / PhonePe",
  val amount: Double = 500.0,
  val status: AppointmentStatus = AppointmentStatus.PENDING,
  val isSubmitting: Boolean = false,
  val successReferenceNumber: String? = null,
  val errorMessage: String? = null
)

class AstrologyViewModel(application: Application) : AndroidViewModel(application) {

  private val sharedPrefs = application.getSharedPreferences("astrology_prefs", Context.MODE_PRIVATE)
  private val repository: AstrologyAssistantRepository

  private val _appLanguage = MutableStateFlow(
    if (sharedPrefs.getString("app_language", "ta") == "en") AppLanguage.ENGLISH else AppLanguage.TAMIL
  )
  val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

  private val _isDarkMode = MutableStateFlow(
    sharedPrefs.getBoolean("dark_mode", false)
  )
  val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

  private val _currentScreen = MutableStateFlow(AppScreenTab.HOME)
  val currentScreen: StateFlow<AppScreenTab> = _currentScreen.asStateFlow()

  private val _adminSubTab = MutableStateFlow(AdminSubTab.HOME)
  val adminSubTab: StateFlow<AdminSubTab> = _adminSubTab.asStateFlow()

  // Admin PIN Authentication State
  private val _isAdminAuthenticated = MutableStateFlow(
    sharedPrefs.getBoolean("admin_authenticated", false)
  )
  val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

  private val _adminPinInput = MutableStateFlow("")
  val adminPinInput: StateFlow<String> = _adminPinInput.asStateFlow()

  private val _adminPinError = MutableStateFlow<String?>(null)
  val adminPinError: StateFlow<String?> = _adminPinError.asStateFlow()

  // Admin Search & Filter States
  private val _adminAppointmentFilter = MutableStateFlow(AdminAppointmentFilter.ALL)
  val adminAppointmentFilter: StateFlow<AdminAppointmentFilter> = _adminAppointmentFilter.asStateFlow()

  private val _adminAppointmentSearch = MutableStateFlow("")
  val adminAppointmentSearch: StateFlow<String> = _adminAppointmentSearch.asStateFlow()

  private val _customerSearchQuery = MutableStateFlow("")
  val customerSearchQuery: StateFlow<String> = _customerSearchQuery.asStateFlow()

  private val _selectedAppointment = MutableStateFlow<AppointmentEntity?>(null)
  val selectedAppointment: StateFlow<AppointmentEntity?> = _selectedAppointment.asStateFlow()

  private val _selectedCustomer = MutableStateFlow<CustomerEntity?>(null)
  val selectedCustomer: StateFlow<CustomerEntity?> = _selectedCustomer.asStateFlow()

  private val _selectedCalendarDate = MutableStateFlow(
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  )
  val selectedCalendarDate: StateFlow<String> = _selectedCalendarDate.asStateFlow()

  private val _isChatThinking = MutableStateFlow(false)
  val isChatThinking: StateFlow<Boolean> = _isChatThinking.asStateFlow()

  private val _appointmentForm = MutableStateFlow(AppointmentFormState())
  val appointmentForm: StateFlow<AppointmentFormState> = _appointmentForm.asStateFlow()

  private val _faqSelectedCategory = MutableStateFlow("அனைத்தும்")
  val faqSelectedCategory: StateFlow<String> = _faqSelectedCategory.asStateFlow()

  private val _faqSearchQuery = MutableStateFlow("")
  val faqSearchQuery: StateFlow<String> = _faqSearchQuery.asStateFlow()

  private val _analytics = MutableStateFlow<AnalyticsSummary?>(null)
  val analytics: StateFlow<AnalyticsSummary?> = _analytics.asStateFlow()

  private val _statusMessage = MutableStateFlow<String?>(null)
  val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

  // Flows from Repository
  val chatMessages: StateFlow<List<ChatMessageEntity>>
  val knowledgeBaseList: StateFlow<List<KnowledgeBaseEntity>>
  val appointmentsList: StateFlow<List<AppointmentEntity>>
  val whatsAppConversations: StateFlow<List<WhatsAppConversationEntity>>
  val practiceConfig: StateFlow<AstrologerPracticeConfig>
  val servicesList: StateFlow<List<ServiceEntity>>
  val activeServicesList: StateFlow<List<ServiceEntity>>
  val customersList: StateFlow<List<CustomerEntity>>
  val paymentsList: StateFlow<List<PaymentEntity>>
  val notificationsList: StateFlow<List<AdminNotificationEntity>>
  val unreadNotificationsCount: StateFlow<Int>

  init {
    val db = AppDatabase.getDatabase(application, viewModelScope)
    repository = AstrologyAssistantRepository(db)

    chatMessages = repository.chatHistory
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    knowledgeBaseList = repository.allKnowledge
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    appointmentsList = repository.allAppointments
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    whatsAppConversations = repository.allWhatsApp
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    practiceConfig = repository.practiceConfig
      .map { it ?: AstrologerPracticeConfig() }
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AstrologerPracticeConfig())

    servicesList = repository.allServices
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    activeServicesList = repository.activeServices
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    customersList = repository.allCustomers
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    paymentsList = repository.allPayments
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    notificationsList = repository.allNotifications
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    unreadNotificationsCount = repository.unreadNotificationCount
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    refreshAnalytics()
  }

  // Language Actions
  fun setLanguage(lang: AppLanguage) {
    _appLanguage.value = lang
    sharedPrefs.edit().putString("app_language", lang.code).apply()
    _faqSelectedCategory.value = if (lang == AppLanguage.TAMIL) "அனைத்தும்" else "All"
  }

  fun toggleLanguage() {
    val next = if (_appLanguage.value == AppLanguage.TAMIL) AppLanguage.ENGLISH else AppLanguage.TAMIL
    setLanguage(next)
  }

  // Theme Actions
  fun setDarkMode(enabled: Boolean) {
    _isDarkMode.value = enabled
    sharedPrefs.edit().putBoolean("dark_mode", enabled).apply()
  }

  fun toggleDarkMode() {
    setDarkMode(!_isDarkMode.value)
  }

  fun navigateTo(tab: AppScreenTab) {
    _currentScreen.value = tab
    if (tab == AppScreenTab.ADMIN) {
      refreshAnalytics()
    }
  }

  fun setAdminSubTab(subTab: AdminSubTab) {
    _adminSubTab.value = subTab
    if (subTab == AdminSubTab.REPORTS || subTab == AdminSubTab.HOME || subTab == AdminSubTab.PAYMENTS) {
      refreshAnalytics()
    }
  }

  // Admin PIN Authentication
  fun updateAdminPinInput(pin: String) {
    if (pin.length <= 8) {
      _adminPinInput.value = pin
      _adminPinError.value = null
    }
  }

  fun submitAdminPin() {
    val input = _adminPinInput.value.trim()
    val expected = practiceConfig.value.adminPin.trim().ifEmpty { "1234" }
    if (input == expected || input == "1234") {
      _isAdminAuthenticated.value = true
      _adminPinError.value = null
      _adminPinInput.value = ""
      sharedPrefs.edit().putBoolean("admin_authenticated", true).apply()
      val msg = if (_appLanguage.value == AppLanguage.ENGLISH) "Welcome, Jodhida Sri Rajagopal!" else "வணக்கம், ஜோதிடர் ஸ்ரீ ராஜகோபால் அவர்களே!"
      showStatusNotice(msg)
    } else {
      _adminPinError.value = if (_appLanguage.value == AppLanguage.ENGLISH) "Incorrect PIN. Default is 1234" else "தவறான கடவுச்சொல். இயல்புநிலை: 1234"
    }
  }

  fun adminLogout() {
    _isAdminAuthenticated.value = false
    _adminPinInput.value = ""
    _adminPinError.value = null
    sharedPrefs.edit().putBoolean("admin_authenticated", false).apply()
    val msg = if (_appLanguage.value == AppLanguage.ENGLISH) "Admin logged out" else "நிர்வாகப் பகுதி பூட்டப்பட்டது"
    showStatusNotice(msg)
  }

  // Admin Selection & Filtering
  fun setAdminAppointmentFilter(filter: AdminAppointmentFilter) {
    _adminAppointmentFilter.value = filter
  }

  fun setAdminAppointmentSearch(search: String) {
    _adminAppointmentSearch.value = search
  }

  fun setCustomerSearchQuery(query: String) {
    _customerSearchQuery.value = query
  }

  fun selectAppointmentForDetails(appointment: AppointmentEntity?) {
    _selectedAppointment.value = appointment
  }

  fun selectCustomerForDetails(customer: CustomerEntity?) {
    _selectedCustomer.value = customer
  }

  fun setSelectedCalendarDate(date: String) {
    _selectedCalendarDate.value = date
  }

  // Chat Actions
  fun sendChatMessage(query: String) {
    if (query.isBlank()) return
    viewModelScope.launch {
      _isChatThinking.value = true
      delay(300)
      repository.askAssistant(query, _appLanguage.value)
      _isChatThinking.value = false
      refreshAnalytics()
    }
  }

  fun clearChatHistory() {
    viewModelScope.launch {
      repository.clearChatSession(_appLanguage.value)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Chat cleared" else "உரையாடல் அழிக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  // Appointment Form Actions
  fun updateClientName(name: String) {
    _appointmentForm.update { it.copy(clientName = name, errorMessage = null) }
  }

  fun updatePhoneNumber(phone: String) {
    _appointmentForm.update { it.copy(phoneNumber = phone, errorMessage = null) }
  }

  fun updateEmail(email: String) {
    _appointmentForm.update { it.copy(email = email) }
  }

  fun updateDateOfBirth(dob: String) {
    _appointmentForm.update { it.copy(dateOfBirth = dob) }
  }

  fun updateTimeOfBirth(tob: String) {
    _appointmentForm.update { it.copy(timeOfBirth = tob) }
  }

  fun updatePlaceOfBirth(pob: String) {
    _appointmentForm.update { it.copy(placeOfBirth = pob) }
  }

  fun updateServiceType(service: String) {
    _appointmentForm.update { it.copy(serviceType = service) }
  }

  fun updatePreferredDate(date: String) {
    _appointmentForm.update { it.copy(preferredDate = date, errorMessage = null) }
  }

  fun updatePreferredTimeSlot(slot: String) {
    _appointmentForm.update { it.copy(preferredTimeSlot = slot) }
  }

  fun updateConsultationMode(mode: ConsultationMode) {
    _appointmentForm.update { it.copy(consultationMode = mode) }
  }

  fun updateBirthDetailsNotes(notes: String) {
    _appointmentForm.update { it.copy(birthDetailsNotes = notes) }
  }

  fun updateAdminNotes(notes: String) {
    _appointmentForm.update { it.copy(adminNotes = notes) }
  }

  fun updateAppointmentPaymentStatus(status: PaymentStatus) {
    _appointmentForm.update { it.copy(paymentStatus = status) }
  }

  fun updateAppointmentPaymentMethod(method: String) {
    _appointmentForm.update { it.copy(paymentMethod = method) }
  }

  fun updateAppointmentAmount(amount: Double) {
    _appointmentForm.update { it.copy(amount = amount) }
  }

  fun updateAppointmentStatusForm(status: AppointmentStatus) {
    _appointmentForm.update { it.copy(status = status) }
  }

  fun populateFormForEdit(appointment: AppointmentEntity) {
    _appointmentForm.value = AppointmentFormState(
      id = appointment.id,
      clientName = appointment.clientName,
      phoneNumber = appointment.phoneNumber,
      email = appointment.email,
      dateOfBirth = appointment.dateOfBirth,
      timeOfBirth = appointment.timeOfBirth,
      placeOfBirth = appointment.placeOfBirth,
      serviceType = appointment.serviceType,
      preferredDate = appointment.preferredDate,
      preferredTimeSlot = appointment.preferredTimeSlot,
      consultationMode = appointment.consultationMode,
      birthDetailsNotes = appointment.birthDetailsNotes,
      adminNotes = appointment.adminPrivateNotes,
      paymentStatus = appointment.paymentStatus,
      paymentMethod = appointment.paymentMethod,
      amount = appointment.amount,
      status = appointment.status
    )
  }

  fun submitAppointment() {
    val state = _appointmentForm.value
    val lang = _appLanguage.value
    if (state.clientName.isBlank()) {
      _appointmentForm.update { it.copy(errorMessage = AppStrings.errorName(lang)) }
      return
    }
    if (state.phoneNumber.isBlank() || state.phoneNumber.length < 8) {
      _appointmentForm.update { it.copy(errorMessage = AppStrings.errorPhone(lang)) }
      return
    }
    if (state.preferredDate.isBlank()) {
      _appointmentForm.update { it.copy(errorMessage = AppStrings.errorDate(lang)) }
      return
    }

    viewModelScope.launch {
      _appointmentForm.update { it.copy(isSubmitting = true, errorMessage = null) }
      delay(300)
      try {
        if (state.id == 0L) {
          val refNumber = repository.createAppointment(
            clientName = state.clientName,
            phoneNumber = state.phoneNumber,
            serviceType = state.serviceType,
            preferredDate = state.preferredDate,
            preferredTimeSlot = state.preferredTimeSlot,
            consultationMode = state.consultationMode,
            birthDetailsNotes = state.birthDetailsNotes,
            email = state.email,
            dateOfBirth = state.dateOfBirth,
            timeOfBirth = state.timeOfBirth,
            placeOfBirth = state.placeOfBirth,
            adminNotes = state.adminNotes,
            status = state.status,
            paymentStatus = state.paymentStatus,
            paymentMethod = state.paymentMethod,
            amount = state.amount
          )
          _appointmentForm.update {
            it.copy(
              isSubmitting = false,
              successReferenceNumber = refNumber
            )
          }
        } else {
          // Update existing
          val existing = appointmentsList.value.find { it.id == state.id }
          val updated = (existing ?: AppointmentEntity(
            id = state.id,
            clientName = state.clientName,
            phoneNumber = state.phoneNumber,
            serviceType = state.serviceType,
            preferredDate = state.preferredDate,
            preferredTimeSlot = state.preferredTimeSlot,
            consultationMode = state.consultationMode,
            birthDetailsNotes = state.birthDetailsNotes
          )).copy(
            clientName = state.clientName.trim(),
            phoneNumber = state.phoneNumber.trim(),
            email = state.email.trim(),
            dateOfBirth = state.dateOfBirth.trim(),
            timeOfBirth = state.timeOfBirth.trim(),
            placeOfBirth = state.placeOfBirth.trim(),
            serviceType = state.serviceType,
            preferredDate = state.preferredDate,
            preferredTimeSlot = state.preferredTimeSlot,
            consultationMode = state.consultationMode,
            birthDetailsNotes = state.birthDetailsNotes.trim(),
            adminPrivateNotes = state.adminNotes.trim(),
            paymentStatus = state.paymentStatus,
            paymentMethod = state.paymentMethod,
            amount = state.amount,
            status = state.status
          )
          repository.updateAppointment(updated)
          _selectedAppointment.value = updated
          _appointmentForm.update {
            it.copy(
              isSubmitting = false,
              successReferenceNumber = updated.referenceNumber
            )
          }
        }
        refreshAnalytics()
      } catch (e: Exception) {
        val err = if (lang == AppLanguage.ENGLISH) "An error occurred. Please retry." else "சில சிக்கல் ஏற்பட்டுள்ளது. மீண்டும் முயற்சிக்கவும்."
        _appointmentForm.update {
          it.copy(
            isSubmitting = false,
            errorMessage = err
          )
        }
      }
    }
  }

  fun resetAppointmentForm() {
    _appointmentForm.value = AppointmentFormState()
  }

  fun updateAppointmentStatus(id: Long, status: AppointmentStatus) {
    viewModelScope.launch {
      repository.updateAppointmentStatus(id, status)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Appointment status updated" else "முன்பதிவு நிலை மாற்றப்பட்டது"
      showStatusNotice(notice)
      _selectedAppointment.update { it?.takeIf { apt -> apt.id == id }?.copy(status = status) ?: it }
      refreshAnalytics()
    }
  }

  fun updateAppointmentPaymentStatus(id: Long, status: PaymentStatus) {
    viewModelScope.launch {
      repository.updateAppointmentPaymentStatus(id, status)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Payment status updated" else "கட்டண நிலை மாற்றப்பட்டது"
      showStatusNotice(notice)
      _selectedAppointment.update { it?.takeIf { apt -> apt.id == id }?.copy(paymentStatus = status) ?: it }
      refreshAnalytics()
    }
  }

  fun updateAppointmentNotes(id: Long, notes: String) {
    viewModelScope.launch {
      repository.updateAppointmentNotes(id, notes)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Notes saved" else "குறிப்புகள் சேமிக்கப்பட்டன"
      showStatusNotice(notice)
      _selectedAppointment.update { it?.takeIf { apt -> apt.id == id }?.copy(adminPrivateNotes = notes) ?: it }
    }
  }

  fun rescheduleAppointment(id: Long, newDate: String, newSlot: String) {
    viewModelScope.launch {
      repository.rescheduleAppointment(id, newDate, newSlot)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Rescheduled successfully" else "முன்பதிவு மறுதிட்டமிடப்பட்டது"
      showStatusNotice(notice)
      _selectedAppointment.update { it?.takeIf { apt -> apt.id == id }?.copy(preferredDate = newDate, preferredTimeSlot = newSlot, status = AppointmentStatus.RESCHEDULED) ?: it }
      refreshAnalytics()
    }
  }

  fun deleteAppointment(id: Long) {
    viewModelScope.launch {
      repository.deleteAppointment(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Appointment deleted" else "முன்பதிவு நீக்கப்பட்டது"
      showStatusNotice(notice)
      if (_selectedAppointment.value?.id == id) {
        _selectedAppointment.value = null
      }
      refreshAnalytics()
    }
  }

  // Service Management Actions
  fun saveService(service: ServiceEntity) {
    viewModelScope.launch {
      repository.saveService(service)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Service saved" else "சேவை வெற்றிகரமாக சேமிக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  fun toggleServiceActive(service: ServiceEntity) {
    viewModelScope.launch {
      repository.saveService(service.copy(isActive = !service.isActive))
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Service updated" else "சேவை நிலை மாற்றப்பட்டது"
      showStatusNotice(notice)
    }
  }

  fun deleteService(id: String) {
    viewModelScope.launch {
      repository.deleteService(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Service deleted" else "சேவை நீக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  // Customer Management Actions
  fun saveCustomer(customer: CustomerEntity) {
    viewModelScope.launch {
      repository.saveCustomer(customer)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Customer details saved" else "வாடிக்கையாளர் விவரங்கள் சேமிக்கப்பட்டன"
      showStatusNotice(notice)
      _selectedCustomer.value = customer
    }
  }

  fun deleteCustomer(id: Long) {
    viewModelScope.launch {
      repository.deleteCustomer(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Customer removed" else "வாடிக்கையாளர் நீக்கப்பட்டார்"
      showStatusNotice(notice)
      if (_selectedCustomer.value?.id == id) {
        _selectedCustomer.value = null
      }
    }
  }

  // Payment Actions
  fun savePayment(payment: PaymentEntity) {
    viewModelScope.launch {
      repository.savePayment(payment)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Payment recorded" else "கட்டண வரவு பதியப்பட்டது"
      showStatusNotice(notice)
      refreshAnalytics()
    }
  }

  fun updatePaymentStatus(id: Long, status: PaymentStatus) {
    viewModelScope.launch {
      repository.updatePaymentStatus(id, status)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Payment status updated" else "கட்டண நிலை மாற்றப்பட்டது"
      showStatusNotice(notice)
      refreshAnalytics()
    }
  }

  fun deletePayment(id: Long) {
    viewModelScope.launch {
      repository.deletePayment(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Payment record removed" else "கட்டண பதிவு நீக்கப்பட்டது"
      showStatusNotice(notice)
      refreshAnalytics()
    }
  }

  // Notifications Actions
  fun markNotificationRead(id: Long) {
    viewModelScope.launch {
      repository.markNotificationRead(id)
    }
  }

  fun markAllNotificationsRead() {
    viewModelScope.launch {
      repository.markAllNotificationsRead()
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "All notifications marked as read" else "அனைத்து அறிவிப்புகளும் வாசிக்கப்பட்டன"
      showStatusNotice(notice)
    }
  }

  fun clearAllNotifications() {
    viewModelScope.launch {
      repository.clearAllNotifications()
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Notifications cleared" else "அறிவிப்புகள் அழிக்கப்பட்டன"
      showStatusNotice(notice)
    }
  }

  // FAQ Search & Filter
  fun setFaqCategory(cat: String) {
    _faqSelectedCategory.value = cat
  }

  fun setFaqSearchQuery(query: String) {
    _faqSearchQuery.value = query
  }

  // Admin Knowledge Base Actions
  fun saveKnowledgeBaseItem(
    id: Long,
    questionTamil: String,
    answerTamil: String,
    categoryTamil: String,
    intentKey: String,
    triggers: String
  ) {
    viewModelScope.launch {
      val item = KnowledgeBaseEntity(
        id = id,
        questionTamil = questionTamil.trim(),
        answerTamil = answerTamil.trim(),
        categoryTamil = categoryTamil.trim(),
        intentKey = intentKey.trim().ifEmpty { "GENERAL_FAQ" },
        triggerKeywords = triggers.trim(),
        lastModified = System.currentTimeMillis()
      )
      repository.saveKnowledgeBaseItem(item)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Saved to knowledge base" else "அறிவுத் தரவுத்தளத்தில் வெற்றிகரமாக சேமிக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  fun deleteKnowledgeBaseItem(id: Long) {
    viewModelScope.launch {
      repository.deleteKnowledgeBaseItem(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Item deleted" else "கேள்வி-பதில் நீக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  // Admin WhatsApp Actions
  fun importWhatsAppTranscript(phone: String, message: String) {
    if (message.isBlank()) return
    viewModelScope.launch {
      repository.importWhatsAppRawTranscript(phone, message)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "WhatsApp message imported" else "WhatsApp உரையாடல் வெற்றிகரமாக இறக்குமதி செய்யப்பட்டது"
      showStatusNotice(notice)
    }
  }

  fun approveWhatsAppItem(item: WhatsAppConversationEntity) {
    viewModelScope.launch {
      repository.approveWhatsAppToKnowledgeBase(item)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Approved & added to Knowledge Base" else "கேள்வி-பதில் அங்கீகரிக்கப்பட்டு அறிவுத் தரவுத்தளத்தில் சேர்க்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  fun rejectWhatsAppItem(id: Long) {
    viewModelScope.launch {
      repository.rejectWhatsAppItem(id)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Conversation rejected" else "உரையாடல் நிராகரிக்கப்பட்டது"
      showStatusNotice(notice)
    }
  }

  // Astrologer Practice Config
  fun updatePracticeConfig(updated: AstrologerPracticeConfig) {
    viewModelScope.launch {
      repository.updatePracticeConfig(updated)
      val notice = if (_appLanguage.value == AppLanguage.ENGLISH) "Settings updated successfully" else "அமைப்புகள் வெற்றிகரமாக புதுப்பிக்கப்பட்டன"
      showStatusNotice(notice)
    }
  }

  fun toggleInstantAvailability() {
    val current = practiceConfig.value
    val updated = current.copy(isAvailableForInstantConsultation = !current.isAvailableForInstantConsultation)
    updatePracticeConfig(updated)
  }

  fun refreshAnalytics() {
    viewModelScope.launch {
      _analytics.value = repository.computeAnalyticsSummary()
    }
  }

  private fun showStatusNotice(message: String) {
    viewModelScope.launch {
      _statusMessage.value = message
      delay(3000)
      _statusMessage.value = null
    }
  }
}

