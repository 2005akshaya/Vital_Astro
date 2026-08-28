package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.TraditionalMotifBadge
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun FaqScreen(
  viewModel: AstrologyViewModel,
  onAskChatQuestion: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val knowledgeList by viewModel.knowledgeBaseList.collectAsState()
  val selectedCategory by viewModel.faqSelectedCategory.collectAsState()
  val searchQuery by viewModel.faqSearchQuery.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var expandedFaqId by remember { mutableStateOf<Long?>(1L) }

  val allCategoriesLabel = AppStrings.allCategories(appLanguage)

  val categories = remember(knowledgeList, appLanguage) {
    val cats = knowledgeList.map { it.categoryTamil }.distinct()
    listOf(allCategoriesLabel) + cats
  }

  val filteredFaqs = remember(knowledgeList, selectedCategory, searchQuery, allCategoriesLabel) {
    knowledgeList.filter { item ->
      val matchesCategory = selectedCategory == allCategoriesLabel ||
        selectedCategory == "அனைத்தும்" ||
        selectedCategory == "All" ||
        item.categoryTamil == selectedCategory
      val matchesSearch = searchQuery.isBlank() ||
        item.questionTamil.contains(searchQuery, ignoreCase = true) ||
        item.answerTamil.contains(searchQuery, ignoreCase = true) ||
        item.triggerKeywords.contains(searchQuery, ignoreCase = true)
      matchesCategory && matchesSearch
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("faq_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Column {
        TraditionalMotifBadge(text = AppStrings.faqBadge(appLanguage))
        Text(
          text = AppStrings.faqTitle(appLanguage),
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = AppStrings.faqSubtitle(appLanguage),
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 18.sp
          )
        )
      }
    }

    // Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { viewModel.setFaqSearchQuery(it) },
        placeholder = { Text(AppStrings.searchPlaceholder(appLanguage)) },
        leadingIcon = {
          Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { viewModel.setFaqSearchQuery("") }) {
              Icon(imageVector = Icons.Default.Close, contentDescription = "Clear", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          }
        },
        modifier = Modifier.fillMaxWidth().testTag("faq_search_input"),
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface,
          focusedBorderColor = MaterialTheme.colorScheme.primary,
          unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        singleLine = true
      )
    }

    // Category Filter Chips
    if (categories.size > 1) {
      item {
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(categories) { category ->
            val isSelected = selectedCategory == category || (selectedCategory == "அனைத்தும்" && category == allCategoriesLabel)
            FilterChip(
              selected = isSelected,
              onClick = { viewModel.setFaqCategory(category) },
              label = {
                Text(
                  text = category,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 11.5.sp
                  )
                )
              },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = Color.White,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                labelColor = MaterialTheme.colorScheme.onSurface
              ),
              border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
              ),
              shape = RoundedCornerShape(16.dp)
            )
          }
        }
      }
    }

    // FAQ Items
    if (filteredFaqs.isEmpty()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = AppStrings.noFaqFound(appLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
      }
    } else {
      items(filteredFaqs) { faq ->
        val isExpanded = expandedFaqId == faq.id
        val question = faq.questionTamil
        val answer = faq.answerTamil
        val categoryName = faq.categoryTamil

        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(
              if (isExpanded) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline
            )
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 2.dp else 0.dp),
          modifier = Modifier
            .fillMaxWidth()
            .clickable { expandedFaqId = if (isExpanded) null else faq.id }
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${faq.id}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (isExpanded) Color.White else MaterialTheme.colorScheme.primary,
                      fontSize = 11.sp
                    )
                  )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                  Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(bottom = 2.dp)
                  ) {
                    Text(
                      text = categoryName,
                      style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.5.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                      ),
                      modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                    )
                  }

                  Text(
                    text = question,
                    style = MaterialTheme.typography.titleSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurface,
                      fontSize = 13.5.sp
                    )
                  )
                }
              }

              Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                tint = MaterialTheme.colorScheme.primary
              )
            }

            AnimatedVisibility(visible = isExpanded) {
              Column(modifier = Modifier.padding(top = 10.dp, start = 38.dp)) {
                HorizontalDivider(
                  thickness = 1.dp,
                  color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                  modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                  text = answer,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                  )
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                  onClick = { onAskChatQuestion(question) },
                  contentPadding = PaddingValues(horizontal = 0.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.ChatBubbleOutline,
                    contentDescription = null,
                    tint = GeometricForestGreen,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = AppStrings.askAiMore(appLanguage),
                    style = MaterialTheme.typography.labelSmall.copy(
                      color = GeometricForestGreen,
                      fontWeight = FontWeight.Bold
                    )
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
