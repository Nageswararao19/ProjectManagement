package com.nagesh.projectmanagement.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Based on PDF Requirements
@Parcelize
data class Project(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val status: String, // "Planned", "In Progress", "Completed"
) : Parcelable

