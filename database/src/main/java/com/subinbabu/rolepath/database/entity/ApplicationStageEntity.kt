package com.subinbabu.rolepath.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "application_stages",
    foreignKeys = [
        ForeignKey(
            entity = JobApplicationEntity::class,
            parentColumns = ["id"],
            childColumns = ["applicationId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["applicationId", "position"]),
        Index("scheduledAtEpochMillis"),
        Index(value = ["applicationId", "status"]),
    ],
)
data class ApplicationStageEntity(
    @PrimaryKey val id: String,
    val applicationId: String,
    val position: Int,
    val title: String,
    val type: String,
    val status: String,
    val outcome: String,
    val scheduledAtEpochMillis: Long?,
    val scheduledZoneId: String?,
    val reminderOffsetMinutes: Long?,
    val meetingLink: String?,
    val preparationNotes: String?,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
)