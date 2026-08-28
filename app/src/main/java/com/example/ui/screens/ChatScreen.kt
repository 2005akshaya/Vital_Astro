package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.data.model.ChatMessageEntity
import com.example.data.model.MessageSender
import com.example.ui.components.EscalationCard
import com.example.ui.theme.*
import com.example.ui.util.AppStrings
import com.example.ui.viewmodel.AstrologyViewModel

@Composable
fun ChatScreen(
  viewModel: AstrologyViewModel,
  onNavigateToBooking: () -> Unit,
  modifier: Modifier = Modifier
) {
  val messages by viewModel.chatMessages.collectAsState()
  val isThinking by viewModel.isChatThinking.collectAsState()
  val config by viewModel.practiceConfig.collectAsState()
  val appLanguage by viewModel.appLanguage.collectAsState()

  var inputText by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  val quickQuestions = if (appLanguage == AppLanguage.ENGLISH) {
    listOf(
      "Consultation Fee?",
      "Timings to contact?",
      "How to book appointment?",
      "Online consultation?",
      "Required birth details",
      "Office Address",
      "When will my marriage happen?"
    )
  } else {
    listOf(
      "ஆலோசனை கட்டணம் எவ்வளவு?",
      "தொடர்பு நேரம் எப்போது?",
      "முன்பதிவு செய்வது எப்படி?",
      "ஆன்லைன் வீடியோ ஆலோசனை",
      "தேவையான தகவல்கள்",
      "அலுவலக முகவரி",
      "marriage eppo nadakkum?"
    )
  }

  // Scroll to bottom on new message
  LaunchedEffect(messages.size, isThinking) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("chat_screen")
  ) {
    // 1. Top action header to clear chat
    Surface(
      color = MaterialTheme.colorScheme.surface,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = AppStrings.chatHeaderTitle(appLanguage),
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        )

        TextButton(
          onClick = { viewModel.clearChatHistory() },
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
          modifier = Modifier.testTag("clear_chat_button")
        ) {
          Icon(
            imageVector = Icons.Default.DeleteSweep,
            contentDescription = AppStrings.clearChat(appLanguage),
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = AppStrings.clearChat(appLanguage),
            style = MaterialTheme.typography.labelSmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 11.sp
            )
          )
        }
      }
    }

    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

    // 2. MESSAGES LIST
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(top = 16.dp, bottom = 8.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      items(messages) { msg ->
        ChatMessageItem(
          message = msg,
          config = config,
          appLanguage = appLanguage,
          onNavigateToBooking = onNavigateToBooking
        )
      }

      if (isThinking) {
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
              contentAlignment = Alignment.Center
            ) {
              CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
              color = MaterialTheme.colorScheme.surface,
              shape = RoundedCornerShape(topStart = 0.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
              border = CardDefaults.outlinedCardBorder().copy(
                width = 1.dp,
                brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
              ),
              shadowElevation = 1.dp
            ) {
              Text(
                text = AppStrings.thinkingText(appLanguage),
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                ),
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
              )
            }
          }
        }
      }
    }

    // 3. QUICK QUESTION CHIPS
    Surface(
      color = MaterialTheme.colorScheme.background,
      modifier = Modifier.fillMaxWidth()
    ) {
      LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(quickQuestions) { query ->
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            border = CardDefaults.outlinedCardBorder().copy(
              width = 1.dp,
              brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
            ),
            modifier = Modifier
              .clickable { viewModel.sendChatMessage(query) }
          ) {
            Text(
              text = query,
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
              ),
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
            )
          }
        }
      }
    }

    // 4. CHAT INPUT FIELD
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = MaterialTheme.colorScheme.surface
    ) {
      Column {
        HorizontalDivider(
          thickness = 1.dp,
          color = MaterialTheme.colorScheme.outline
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 8.dp)
            .navigationBarsPadding(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Input pill
          Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(28.dp),
            border = CardDefaults.outlinedCardBorder().copy(
              width = 1.dp,
              brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
            ),
            modifier = Modifier
              .weight(1f)
              .heightIn(min = 48.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              TextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                  Text(
                    text = AppStrings.chatPlaceholder(appLanguage),
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontSize = 12.5.sp,
                      color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                  )
                },
                colors = TextFieldDefaults.colors(
                  focusedContainerColor = Color.Transparent,
                  unfocusedContainerColor = Color.Transparent,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                  focusedTextColor = MaterialTheme.colorScheme.onSurface,
                  unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier
                  .weight(1f)
                  .testTag("chat_input_field"),
                maxLines = 3
              )

              if (inputText.isNotEmpty()) {
                IconButton(
                  onClick = { inputText = "" },
                  modifier = Modifier.size(24.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          // Circular Gold/Primary Send Button
          IconButton(
            onClick = {
              if (inputText.isNotBlank()) {
                val textToSend = inputText
                inputText = ""
                viewModel.sendChatMessage(textToSend)
              }
            },
            enabled = inputText.isNotBlank(),
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(if (inputText.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
              .testTag("chat_send_button")
          ) {
            Icon(
              imageVector = Icons.Default.Send,
              contentDescription = "Send",
              tint = Color.White,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun ChatMessageItem(
  message: ChatMessageEntity,
  config: com.example.data.model.AstrologerPracticeConfig,
  appLanguage: AppLanguage,
  onNavigateToBooking: () -> Unit
) {
  val isUser = message.sender == MessageSender.USER

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
  ) {
    Row(
      verticalAlignment = Alignment.Top,
      horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
      modifier = Modifier.fillMaxWidth(0.92f)
    ) {
      Surface(
        color = if (isUser) GeometricForestGreen else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(
          topStart = if (isUser) 16.dp else 0.dp,
          topEnd = if (isUser) 0.dp else 16.dp,
          bottomStart = 16.dp,
          bottomEnd = 16.dp
        ),
        border = if (!isUser) {
          CardDefaults.outlinedCardBorder().copy(
            width = 1.dp,
            brush = androidx.compose.ui.graphics.SolidColor(MaterialTheme.colorScheme.outline)
          )
        } else null,
        shadowElevation = if (isUser) 2.dp else 1.dp
      ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)) {
          Text(
            text = message.text,
            style = MaterialTheme.typography.bodyMedium.copy(
              color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface,
              lineHeight = 22.sp,
              fontSize = 13.5.sp
            )
          )
        }
      }
    }

    // Timestamp
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = if (appLanguage == AppLanguage.ENGLISH) (if (isUser) "10:31 AM" else "10:30 AM") else (if (isUser) "முற்பகல் 10:31" else "முற்பகல் 10:30"),
      style = MaterialTheme.typography.labelSmall.copy(
        fontSize = 10.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
      ),
      modifier = Modifier.padding(
        start = if (!isUser) 4.dp else 0.dp,
        end = if (isUser) 4.dp else 0.dp
      )
    )

    // If this response escalated an astrology prediction question, show Escalation Card
    if (!isUser && message.isEscalated) {
      Spacer(modifier = Modifier.height(4.dp))
      EscalationCard(
        config = config,
        appLanguage = appLanguage,
        onNavigateToBooking = onNavigateToBooking,
        modifier = Modifier.fillMaxWidth(0.92f)
      )
    }
  }
}
