package com.um.visamate.utils

import android.content.Context
import com.um.visamate.data.models.Role
import com.um.visamate.data.models.Submission
import com.um.visamate.data.models.User
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object FakeDatabase {
    private val mutex = Mutex()

    private val users: MutableList<User> = mutableListOf()
    private val submissions: MutableList<Submission> = mutableListOf()
    private var finalSubmissionLocked: Boolean = false

    fun initialize(context: Context) {
        if (users.isEmpty()) seedSampleUsers()
    }

    private fun seedSampleUsers() {
        users.add(User(name = "Ahmed Khan", email = "alice@student.um.edu", role = Role.APPLICANT, id = "user-ahmed-khan", studentNumber = "S2024001"))
        users.add(User(name = "Sarah Lee", email = "sarah@student.um.edu", role = Role.APPLICANT, id = "user-sarah-lee", studentNumber = "S2024021"))
        users.add(User(name = "Bob Reviewer", email = "bob@faculty.um.edu", role = Role.REVIEWER))
        users.add(User(name = "Carol Admin", email = "carol@um.edu", role = Role.ADMIN))
    }

    suspend fun createSubmission(submission: Submission): Submission {
        mutex.withLock {
            if (finalSubmissionLocked) throw FinalLockedException("Final submission is locked")
            submissions.add(submission)
            return submission
        }
    }

    suspend fun updateSubmission(submission: Submission): Submission {
        mutex.withLock {
            if (finalSubmissionLocked) throw FinalLockedException("Final submission is locked")
            val idx = submissions.indexOfFirst { it.id == submission.id }
            if (idx >= 0) {
                submissions[idx] = submission
                return submission
            } else throw IllegalArgumentException("Submission not found")
        }
    }

    suspend fun addUser(user: User): User {
        mutex.withLock {
            users.add(user)
            return user
        }
    }

    fun findUserByName(name: String): User? = users.find { it.name == name }

    fun getSubmissionForUser(userId: String): Submission? {
        return submissions.find { it.userId == userId }
    }

    fun findUserById(id: String): User? = users.find { it.id == id }

    fun setFinalSubmissionLocked(locked: Boolean) {
        finalSubmissionLocked = locked
    }
}

class FinalLockedException(message: String) : Exception(message)
class UnauthorizedException(message: String) : Exception(message)
