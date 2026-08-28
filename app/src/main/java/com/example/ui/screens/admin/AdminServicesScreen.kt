package com.example.ui.screens.admin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.model.AppLanguage
import com.example.data.model.ServiceEntity
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun AdminServicesScreen(
  viewModel: AstrologyViewModel,
  modifier: Modifier = Modifier
) {
  val services by viewModel.servicesList.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var showAddEditDialog by remember { mutableStateOf(false) }
  var serviceToEdit by remember { mutableStateOf<ServiceEntity?>(null) }

  if (showAddEditDialog) {
    AdminAddEditServiceDialog(
      service = serviceToEdit,
      appLanguage = appLanguage,
      onDismiss = {
        showAddEditDialog = false
        serviceToEdit = null
      },
      onSave = { entity ->
        viewModel.saveService(entity)
        showAddEditDialog = false
        serviceToEdit = null
      }
    )
  }

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          serviceToEdit = null
          showAddEditDialog = true
        },
        containerColor = GeometricGoldPrimary,
        contentColor = Color.White,
        modifier = Modifier.testTag("admin_add_service_fab")
      ) {
        Icon(Icons.Default.Add, contentDescription = "Add Service")
      }
    },
    containerColor = GeometricBackground
  ) { paddingVals ->
    LazyColumn(
      modifier = modifier
        .fillMaxSize()
        .padding(paddingVals)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            TraditionalMotifBadge(
              text = if (appLanguage == AppLanguage.TAMIL) "சேவை & கட்டணம்" else "Services & Pricing"
            )
            Text(
              text = if (appLanguage == AppLanguage.TAMIL) "ஜோதிட சேவைகள் மேலாண்மை" else "Astrological Services",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GeometricTextPrimary
              )
            )
          }

          Surface(
            color = GeometricGoldContainer,
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = "${services.count { it.isActive }} செயலில் / ${services.size}",
              style = MaterialTheme.typography.labelSmall.copy(
                color = GeometricGoldDark,
                fontWeight = FontWeight.Bold
              ),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      items(services) { service ->
        AdminServiceItemCard(
          service = service,
          appLanguage = appLanguage,
          onToggleActive = { viewModel.toggleServiceActive(service) },
          onEdit = {
            serviceToEdit = service
            showAddEditDialog = true
          },
          onDelete = { viewModel.deleteService(service.id) }
        )
      }
    }
  }
}

@Composable
fun AdminServiceItemCard(
  service: ServiceEntity,
  appLanguage: AppLanguage,
  onToggleActive: () -> Unit,
  onEdit: () -> Unit,
  onDelete: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = GeometricSurface),
    border = CardDefaults.outlinedCardBorder().copy(
      width = 1.dp,
      brush = androidx.compose.ui.graphics.SolidColor(
        if (service.isActive) GeometricGoldMetallic.copy(alpha = 0.35f) else GeometricDivider
      )
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
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = service.titleTamil,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricTextPrimary
            )
          )
          Text(
            text = service.titleEnglish,
            style = MaterialTheme.typography.bodySmall.copy(
              color = GeometricTextSecondary,
              fontSize = 11.sp
            )
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "₹${service.priceAmount.toInt()}",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = GeometricForestGreen
            )
          )
          Spacer(modifier = Modifier.width(8.dp))
          Switch(
            checked = service.isActive,
            onCheckedChange = { onToggleActive() },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.White,
              checkedTrackColor = GeometricForestGreen,
              uncheckedThumbColor = GeometricTextSecondary,
              uncheckedTrackColor = GeometricSurfaceSub
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = service.descriptionTamil,
        style = MaterialTheme.typography.bodySmall.copy(
          color = GeometricTextSecondary,
          lineHeight = 18.sp
        )
      )

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = "⏱ ${service.durationTamil}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = GeometricGoldDark,
                fontWeight = FontWeight.SemiBold
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }

          Surface(
            color = GeometricSurfaceSub,
            shape = RoundedCornerShape(4.dp)
          ) {
            Text(
              text = service.category,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = GeometricTextSecondary
              ),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          IconButton(onClick = onEdit, modifier = Modifier.size(30.dp)) {
            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = GeometricGoldPrimary, modifier = Modifier.size(16.dp))
          }
          IconButton(onClick = onDelete, modifier = Modifier.size(30.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = GeometricMaroon, modifier = Modifier.size(16.dp))
          }
        }
      }
    }
  }
}

@Composable
fun AdminAddEditServiceDialog(
  service: ServiceEntity?,
  appLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSave: (ServiceEntity) -> Unit
) {
  var id by remember(service) { mutableStateOf(service?.id ?: "service_${System.currentTimeMillis()}") }
  var titleTa by remember(service) { mutableStateOf(service?.titleTamil ?: "") }
  var titleEn by remember(service) { mutableStateOf(service?.titleEnglish ?: "") }
  var descTa by remember(service) { mutableStateOf(service?.descriptionTamil ?: "") }
  var descEn by remember(service) { mutableStateOf(service?.descriptionEnglish ?: "") }
  var priceStr by remember(service) { mutableStateOf((service?.priceAmount ?: 500.0).toInt().toString()) }
  var durationTa by remember(service) { mutableStateOf(service?.durationTamil ?: "30-45 நிமிடங்கள்") }
  var category by remember(service) { mutableStateOf(service?.category ?: "பொதுவானவை") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = if (service != null) "சேவையைத் திருத்துக" else "புதிய சேவை சேர்க்க",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GeometricTextPrimary)
      )
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedTextField(
          value = titleTa,
          onValueChange = { titleTa = it },
          label = { Text("சேவை பெயர் (தமிழ்)*") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = titleEn,
          onValueChange = { titleEn = it },
          label = { Text("சேவை பெயர் (English)*") },
          modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          OutlinedTextField(
            value = priceStr,
            onValueChange = { priceStr = it },
            label = { Text("கட்டணம் (₹)*") },
            modifier = Modifier.weight(1f)
          )
          OutlinedTextField(
            value = durationTa,
            onValueChange = { durationTa = it },
            label = { Text("கால அளவு") },
            modifier = Modifier.weight(1f)
          )
        }

        OutlinedTextField(
          value = category,
          onValueChange = { category = it },
          label = { Text("பிரிவு (Category)") },
          modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
          value = descTa,
          onValueChange = { descTa = it },
          label = { Text("விளக்கம் (தமிழ்)") },
          modifier = Modifier.fillMaxWidth().height(80.dp),
          maxLines = 3
        )

        OutlinedTextField(
          value = descEn,
          onValueChange = { descEn = it },
          label = { Text("Description (English)") },
          modifier = Modifier.fillMaxWidth().height(80.dp),
          maxLines = 3
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (titleTa.isNotBlank()) {
            val entity = ServiceEntity(
              id = id,
              titleTamil = titleTa.trim(),
              titleEnglish = titleEn.trim().ifEmpty { titleTa.trim() },
              descriptionTamil = descTa.trim(),
              descriptionEnglish = descEn.trim(),
              priceAmount = priceStr.toDoubleOrNull() ?: 500.0,
              durationTamil = durationTa.trim(),
              durationEnglish = durationTa.trim(),
              category = category.trim(),
              isActive = service?.isActive ?: true,
              displayOrder = service?.displayOrder ?: 1
            )
            onSave(entity)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = GeometricGoldPrimary)
      ) {
        Text("சேமி", color = Color.White, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("ரத்து", color = GeometricTextSecondary)
      }
    },
    containerColor = GeometricSurface,
    shape = RoundedCornerShape(18.dp)
  )
}
