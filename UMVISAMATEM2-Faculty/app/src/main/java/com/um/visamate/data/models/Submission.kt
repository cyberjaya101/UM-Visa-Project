package com.um.visamate.data.models

import java.util.UUID

// Submission status enum
enum class SubmissionStatus {
    DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED
}

// Submission model

data class Submission(
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    var status: SubmissionStatus = SubmissionStatus.DRAFT,
    val createdAt: Long = System.currentTimeMillis(),
    var submittedAt: Long? = null,
    val attachments: MutableList<Attachment> = mutableListOf(),
    val metadata: MutableMap<String, String> = mutableMapOf(),
    var hasConfirmationLetter: Boolean = false,
    var hasResultTranscript: Boolean = false
)

