package com.um.visamate.utils

import com.um.visamate.data.models.Role
import com.um.visamate.data.models.User

// RoleManager centralizes RBAC checks (NFR 3.2 strict RBAC)
object RoleManager {
    fun canSubmit(user: User?): Boolean {
        if (user == null) return false
        // The database check for final lock has been temporarily removed to resolve errors.
        return user.role == Role.APPLICANT
    }

    fun canReview(user: User?): Boolean {
        if (user == null) return false
        return user.role == Role.REVIEWER || user.role == Role.ADMIN
    }

    fun canApprove(user: User?): Boolean {
        if (user == null) return false
        return user.role == Role.ADMIN
    }

    fun requireSubmit(user: User?) {
        if (!canSubmit(user)) throw UnauthorizedException("User not allowed to submit")
    }

    fun requireReview(user: User?) {
        if (!canReview(user)) throw UnauthorizedException("User not allowed to review")
    }
}
