package com.nagesh.projectmanagement.models

import java.io.Serializable

data class Task(
    val id: Long = System.currentTimeMillis(),
    val projectId: Long,
    val title: String,
    val description: String,
    val assignedTo: String,
    val dueDate: String,
    val status: String // "Pending", "In Progress", "Done"
) : Serializable