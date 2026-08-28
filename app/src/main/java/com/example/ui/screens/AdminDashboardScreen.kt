package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.screens.admin.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AdminSubTab
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminDashboardScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val isAuthenticated by viewModel.isAdminAuthenticated.collectAsState()
  val activeSubTab by viewModel.adminSubTab.collectAsState()
  val statusMessage by viewModel.statusMessage.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()
  val pendingCount by viewModel.pendingReviewCount.collectAsState()
  val appointments by viewModel.appointmentsList.collectAsState()
  val pendingAppointmentsCount = remember(appointments) {
    appointments.count { it.status == AppointmentStatus.PENDING }
  }

  var showAddAppointmentFromHome by remember { mutableStateOf(false) }

  if (!isAuthenticated) {
    AdminAuthScreen(viewModel = viewModel, modifier = modifier)
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(GeometricBackground)
      .testTag("admin_dashboard_screen")
  ) {
    // Status SnackBar Notice
    AnimatedVisibility(visible = statusMessage != null) {
      Surface(
        color = GeometricForestGreen,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = statusMessage ?: "",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontWeight = FontWeight.Bold)
          )
        }
      }
    }

    // Admin Sub-Tab Navigation Bar with Badges
    Surface(
      color = GeometricSurface,
      tonalElevation = 1.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(AdminSubTab.values()) { subTab ->
          val isSelected = activeSubTab == subTab
          val badgeCount = when (subTab) {
            AdminSubTab.APPOINTMENTS -> if (pendingAppointmentsCount > 0) pendingAppointmentsCount else 0
            AdminSubTab.WHATSAPP_DATA -> if (pendingCount > 0) pendingCount else 0
            else -> 0
          }

          FilterChip(
            selected = isSelected,
            onClick = { viewModel.setAdminSubTab(subTab) },
            label = {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = if (appLanguage == AppLanguage.TAMIL) subTab.labelTamil else subTab.labelEnglish,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 11.5.sp
                  )
                )
                if (badgeCount > 0) {
                  Spacer(modifier = Modifier.width(4.dp))
                  Surface(
                    color = GeometricMaroon,
                    shape = RoundedCornerShape(10.dp)
                  ) {
                    Text(
                      text = "$badgeCount",
                      style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                      ),
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                  }
                }
              }
            },
            leadingIcon = {
              Icon(
                imageVector = when (subTab) {
                  AdminSubTab.HOME -> Icons.Default.Dashboard
                  AdminSubTab.APPOINTMENTS -> Icons.Default.CalendarMonth
                  AdminSubTab.CALENDAR -> Icons.Default.Schedule
                  AdminSubTab.CUSTOMERS -> Icons.Default.People
                  AdminSubTab.SERVICES -> Icons.Default.MiscellaneousServices
                  AdminSubTab.PAYMENTS -> Icons.Default.CurrencyRupee
                  AdminSubTab.COMMUNICATION -> Icons.Default.Chat
                  AdminSubTab.REPORTS -> Icons.Default.Insights
                  AdminSubTab.KNOWLEDGE_BASE -> Icons.Default.Psychology
                  AdminSubTab.WHATSAPP_DATA -> Icons.Default.Forum
                  AdminSubTab.SETTINGS -> Icons.Default.Settings
                },
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isSelected) Color.White else GeometricGoldDark
              )
            },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = GeometricGoldPrimary,
              selectedLabelColor = Color.White,
              selectedLeadingIconColor = Color.White,
              containerColor = GeometricSurfaceSub,
              labelColor = GeometricTextPrimary
            ),
            border = FilterChipDefaults.filterChipBorder(
              enabled = true,
              selected = isSelected,
              borderColor = if (isSelected) GeometricGoldPrimary else GeometricGoldBorderSubtle
            ),
            shape = RoundedCornerShape(16.dp)
          )
        }
      }
    }

    HorizontalDivider(
      thickness = 1.dp,
      color = GeometricGoldMetallic.copy(alpha = 0.20f)
    )

    // Sub-Screen Content
    Box(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
    ) {
      when (activeSubTab) {
        AdminSubTab.HOME -> AdminHomeScreen(
          viewModel = viewModel,
          onNavigateToTab = { viewModel.setAdminSubTab(it) },
          onOpenNewAppointmentDialog = { viewModel.setAdminSubTab(AdminSubTab.APPOINTMENTS) }
        )
        AdminSubTab.APPOINTMENTS -> AdminAppointmentsScreen(viewModel = viewModel)
        AdminSubTab.CALENDAR -> AdminCalendarScreen(viewModel = viewModel)
        AdminSubTab.CUSTOMERS -> AdminCustomersScreen(viewModel = viewModel)
        AdminSubTab.SERVICES -> AdminServicesScreen(viewModel = viewModel)
        AdminSubTab.PAYMENTS -> AdminPaymentsScreen(viewModel = viewModel)
        AdminSubTab.COMMUNICATION -> AdminCommunicationScreen(viewModel = viewModel)
        AdminSubTab.REPORTS -> AdminReportsScreen(viewModel = viewModel)
        AdminSubTab.KNOWLEDGE_BASE -> AdminKnowledgeBaseContent(viewModel = viewModel)
        AdminSubTab.WHATSAPP_DATA -> AdminWhatsAppReviewContent(viewModel = viewModel)
        AdminSubTab.SETTINGS -> AdminSettingsScreen(viewModel = viewModel)
      }
    }
  }
}

// -------------------------------------------------------------
// KNOWLEDGE BASE MANAGEMENT SUB-VIEW
// -------------------------------------------------------------
@Composable
fun AdminKnowledgeBaseContent(viewModel: AstrologyViewModel) {
  val knowledgeList by viewModel.knowledgeBaseList.collectAsState()

  var showAddDialog by remember { mutableStateOf(false) }
  var editItem by remember { mutableStateOf<KnowledgeBaseEntity?>(null) }

  var questionInput by remember { mutableStateOf("") }
  var answerInput by remember { mutableStateOf("") }
  var categoryInput by remember { mutableStateOf("பொதுவானவை") }
  var triggersInput by remember { mutableStateOf("") }

  if (showAddDialog || editItem != null) {
    AlertDialog(
      onDismissRequest = {
        showAddDialog = false
        editItem = null
      },
      title = {
        Text(
          text = if (editItem != null) "கேள்வி-பதிலைத் திருத்துக" else "புதிய கேள்வி-பதில் சேர்க்க",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
        )
      },
      text = {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = questionInput,
            onValueChange = { questionInput = it },
            label = { Text("கேள்வி (தமிழ்)") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = answerInput,
            onValueChange = { answerInput = it },
            label = { Text("பதில் (தமிழ்)") },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            maxLines = 4
          )
          OutlinedTextField(
            value = categoryInput,
            onValueChange = { categoryInput = it },
            label = { Text("பிரிவு (Category)") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = triggersInput,
            onValueChange = { triggersInput = it },
            label = { Text("தூண்டுதல் சொற்கள் (Keywords - Tanglish / English / தமிழ்)") },
            placeholder = { Text("fees, cost, evlo, கட்டணம்") },
            modifier = Modifier.fillMaxWidth()
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (questionInput.isNotBlank() && answerInput.isNotBlank()) {
              viewModel.saveKnowledgeBaseItem(
                id = editItem?.id ?: 0L,
                questionTamil = questionInput,
                answerTamil = answerInput,
                categoryTamil = categoryInput,
                intentKey = editItem?.intentKey ?: "GENERAL_FAQ",
                triggers = triggersInput
              )
              showAddDialog = false
              editItem = null
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
        ) {
          Text("சேமி", color = Color.White, fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = {
          showAddDialog = false
          editItem = null
        }) {
          Text("ரத்து செய்", color = GeometricTextSecondary)
        }
      },
      containerColor = GeometricSurface,
      shape = RoundedCornerShape(16.dp)
    )
  }

  androidx.compose.foundation.lazy.LazyColumn(
    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          TraditionalMotifBadge(text = "அறிவுத் தளம்")
          Text(
            text = "கேள்வி-பதில் தரவுத்தளம்",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )
        }
        Button(
          onClick = {
            questionInput = ""
            answerInput = ""
            categoryInput = "பொதுவானவை"
            triggersInput = ""
            showAddDialog = true
          },
          colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary),
          shape = RoundedCornerShape(8.dp)
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("புதிய பதிவு", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
      }
    }

    items(knowledgeList) { item ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.30f))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              color = GeometricGoldContainer,
              shape = RoundedCornerShape(4.dp)
            ) {
              Text(
                text = item.categoryTamil,
                style = MaterialTheme.typography.labelSmall.copy(color = GeometricGoldDark, fontSize = 10.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }

            Row {
              IconButton(
                onClick = {
                  editItem = item
                  questionInput = item.questionTamil
                  answerInput = item.answerTamil
                  categoryInput = item.categoryTamil
                  triggersInput = item.triggerKeywords
                },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "திருத்து", tint = GeometricTextSecondary, modifier = Modifier.size(16.dp))
              }
              Spacer(modifier = Modifier.width(4.dp))
              IconButton(
                onClick = { viewModel.deleteKnowledgeBaseItem(item.id) },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "நீக்கு", tint = GeometricMaroon, modifier = Modifier.size(16.dp))
              }
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = item.questionTamil,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = item.answerTamil,
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, lineHeight = 18.sp)
          )

          if (item.triggerKeywords.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "தூண்டுதல் சொற்கள்: ${item.triggerKeywords}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = GeometricTextTertiary)
            )
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// WHATSAPP TRANSCRIPT IMPORT & REVIEW SUB-VIEW
// -------------------------------------------------------------
@Composable
fun AdminWhatsAppReviewContent(viewModel: AstrologyViewModel) {
  val whatsAppList by viewModel.whatsAppConversations.collectAsState()
  var importPhoneInput by remember { mutableStateOf("+91 ") }
  var importMessageInput by remember { mutableStateOf("") }

  androidx.compose.foundation.lazy.LazyColumn(
    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(text = "தரவு இறக்குமதி")
        Text(
          text = "WhatsApp உரையாடல் மதிப்பாய்வு",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
        )
        Text(
          text = "வாடிக்கையாளர் WhatsApp செய்திகளை இறக்குமதி செய்து தமிழ் அறிவுத் தளத்தில் இணைக்கலாம்.",
          style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary)
        )
      }
    }

    // Quick Import Input Card
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.30f))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = "புதிய WhatsApp செய்தியை இறக்குமதி செய்",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
          )
          OutlinedTextField(
            value = importPhoneInput,
            onValueChange = { importPhoneInput = it },
            label = { Text("தொலைபேசி எண்") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
          )
          OutlinedTextField(
            value = importMessageInput,
            onValueChange = { importMessageInput = it },
            label = { Text("WhatsApp செய்தி உரை (Tanglish / English / தமிழ்)") },
            placeholder = { Text("e.g. sir consultation fee evlo? time slots irukka?") },
            modifier = Modifier.fillMaxWidth().height(80.dp),
            maxLines = 3
          )
          Button(
            onClick = {
              if (importMessageInput.isNotBlank()) {
                viewModel.importWhatsAppTranscript(importPhoneInput, importMessageInput)
                importMessageInput = ""
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("இறக்குமதி செய்து வகைப்படுத்து", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    item {
      Text(
        text = "மதிப்பாய்வு செய்ய வேண்டிய உரையாடல்கள்",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
      )
    }

    items(whatsAppList) { item ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = GeometricSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          width = 1.dp,
          brush = androidx.compose.ui.graphics.SolidColor(GeometricGoldMetallic.copy(alpha = 0.30f))
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(item.senderPhone, style = MaterialTheme.typography.labelSmall.copy(color = GeometricTextTertiary))
            Surface(
              color = when (item.status) {
                WhatsAppReviewStatus.APPROVED -> GeometricForestGreenContainer
                WhatsAppReviewStatus.REJECTED -> GeometricMaroonContainer
                WhatsAppReviewStatus.PENDING_REVIEW -> GeometricGoldContainer
              },
              shape = RoundedCornerShape(4.dp)
            ) {
              Text(
                text = item.status.labelTamil,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = when (item.status) {
                    WhatsAppReviewStatus.APPROVED -> GeometricForestGreen
                    WhatsAppReviewStatus.REJECTED -> GeometricMaroon
                    WhatsAppReviewStatus.PENDING_REVIEW -> GeometricGoldDark
                  }
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "மூல உரை: \"${item.rawMessage}\"",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium, color = GeometricTextPrimary)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "பரிந்துரைக்கப்பட்ட தமிழ் பதில்:\n${item.proposedTamilAnswer}",
            style = MaterialTheme.typography.bodySmall.copy(color = GeometricTextSecondary, fontSize = 12.sp)
          )

          if (item.status == WhatsAppReviewStatus.PENDING_REVIEW) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Button(
                onClick = { viewModel.approveWhatsAppItem(item) },
                colors = ButtonDefaults.buttonColors(containerColor = GeometricForestGreen),
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("அங்கீகரி", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
              OutlinedButton(
                onClick = { viewModel.rejectWhatsAppItem(item.id) },
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier.weight(1f)
              ) {
                Text("நிராகரி", color = GeometricMaroon, fontSize = 12.sp)
              }
            }
          }
        }
      }
    }
  }
}
