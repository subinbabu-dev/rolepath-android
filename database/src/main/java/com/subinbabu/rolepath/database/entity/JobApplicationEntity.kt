package com.subinbabu.rolepath.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "job_applications",
    indices = [
        Index("status"),
        Index("updatedAtEpochMillis"),
        Index("followUpEpochMillis"),
        Index("archivedAtEpochMillis"),
    ],
)
data class JobApplicationEntity(
    @PrimaryKey val id: String,
    val companyName: String,
    val jobTitle: String,
    val status: String,
    val dateAppliedEpochDay: Long?,
    val location: String?,
    val workMode: String,

    val jobUrl: String?,
    val applicationSource: String?,
    val recruiterName: String?,
    val recruiterEmail: String?,
    val compensationText: String?,
    val followUpEpochMillis: Long?,
    val followUpZoneId: String?,
    val followUpReminderOffsetMinutes: Long?,
    val notes: String?,

    val archivedAtEpochMillis: Long?,
    val createdAtEpochMillis: Long,
    val updatedAtEpochMillis: Long,
)