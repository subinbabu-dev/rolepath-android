package com.subinbabu.rolepath.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "application_status_history",
    foreignKeys = [
        ForeignKey(
            entity = JobApplicationEntity::class,
            parentColumns = ["id"],
            childColumns = ["applicationId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["applicationId", "changedAtEpochMillis"]),
    ]
)
data class ApplicationStatusHistoryEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val previousStatus: String?,
    val newStatus: String,
    val changedAtEpochMillis: Long,
)