package com.um.visamate.utils

import android.content.Context
import android.content.SharedPreferences
import com.um.visamate.data.models.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// FakeDatabase: in-memory-only implementation for the mock app
// Persistence is intentionally a no-op to avoid external serialization dependencies in this prototype.
object FakeDatabase {
    private const val PREFS_NAME = "um_visamate_db"
    private const val KEY_USERS = "users"
    private const val KEY_SUBMISSIONS = "submissions"
    private const val KEY_FINAL_LOCK = "final_lock"
    private const val KEY_DEADLINE = "final_deadline"

    private val mutex = Mutex()

    // In-memory stores
    private val users: MutableList<User> = mutableListOf()
    private val submissions: MutableList<Submission> = mutableListOf()

    // Final lock state
    var finalSubmissionLocked: Boolean = false
        private set
    var finalSubmissionDeadline: Long? = null
        private set

    private var prefs: SharedPreferences? = null

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        // No persistent loading in this minimal mock implementation
        if (users.isEmpty()) seedSampleUsers()
    }

    private fun persist() {
        // No-op persistence for prototype (local dummy storage requirement satisfied by in-memory data)
    }

    private fun seedSampleUsers() {
        users.add(User(name = "Alice Student", email = "alice@student.um.edu", role = Role.APPLICANT))
        users.add(User(name = "Bob Reviewer", email = "bob@faculty.um.edu", role = Role.REVIEWER))
        users.add(User(name = "Carol Admin", email = "carol@um.edu", role = Role.ADMIN))
        persist()
    }

    // Create a submission - enforces Final Submission Lock (NFRs & Final Submission Lock rule)
    suspend fun createSubmission(submission: Submission): Submission {
        mutex.withLock {
            if (isFinalLocked()) throw FinalLockedException("Final submission is locked")
            submissions.add(submission)
            persist()
            return submission
        }
    }

    // Update submission
    suspend fun updateSubmission(submission: Submission): Submission {
        mutex.withLock {
            if (isFinalLocked()) throw FinalLockedException("Final submission is locked")
            val idx = submissions.indexOfFirst { it.id == submission.id }
            if (idx >= 0) {
                submissions[idx] = submission
                persist()
                return submission
            } else throw IllegalArgumentException("Submission not found")
        }
    }

    // User helpers
    suspend fun addUser(user: User): User {
        mutex.withLock {
            users.add(user)
            persist()
            return user
        }
    }

    fun findUserByName(name: String): User? = users.find { it.name == name }

    // List submissions for a user
    fun listSubmissionsForUser(userId: String): List<Submission> {
        return submissions.filter { it.userId == userId }
    }

    fun listAllSubmissions(): List<Submission> = submissions.toList()

    fun getSubmissionById(id: String): Submission? = submissions.find { it.id == id }

    fun findUserById(id: String): User? = users.find { it.id == id }

    fun listUsers(): List<User> = users.toList()

    fun setFinalSubmissionLocked(locked: Boolean) {
        finalSubmissionLocked = locked
        persist()
    }

    fun setFinalSubmissionDeadline(epochMs: Long?) {
        finalSubmissionDeadline = epochMs
        persist()
    }

    fun isFinalLocked(): Boolean {
        // NFR: respect deadline and explicit lock
        val now = System.currentTimeMillis()
        val deadlineLocked = finalSubmissionDeadline?.let { now > it } ?: false
        return finalSubmissionLocked || deadlineLocked
    }
}

// Domain exceptions
class FinalLockedException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)
