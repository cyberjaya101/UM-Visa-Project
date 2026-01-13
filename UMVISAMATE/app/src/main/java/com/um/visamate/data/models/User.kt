package com.um.visamate.data.models

import java.util.UUID

// User model
// Fields: id, name, role
// Used across auth, RBAC, and FakeDatabase

data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String? = null,
    val role: Role
)

