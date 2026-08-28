package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class MessageSender {
  USER,
  ASSISTANT
}

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val conversationId: String = "default_session",
  val sender: MessageSender,
  val text: String,
  val intentKey: String = "",
  val isEscalated: Boolean = false,
  val timestamp: Long = System.currentTimeMillis()
)
