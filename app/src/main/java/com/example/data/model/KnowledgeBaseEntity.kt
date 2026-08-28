package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class KnowledgeStatus(val labelTamil: String) {
  ACTIVE("செயலில் உள்ளது"),
  DRAFT("வரைவு"),
  ARCHIVED("காப்பகப்படுத்தப்பட்டது")
}

@Entity(tableName = "knowledge_base")
data class KnowledgeBaseEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val questionTamil: String,
  val answerTamil: String,
  val categoryTamil: String,
  val intentKey: String,
  val triggerKeywords: String, // comma separated Tamil, Tanglish, English terms
  val languageSource: String = "தமிழ் / கலப்பு",
  val lastModified: Long = System.currentTimeMillis(),
  val status: KnowledgeStatus = KnowledgeStatus.ACTIVE,
  val helpfulCount: Int = 0
)
