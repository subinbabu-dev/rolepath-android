package com.subinbabu.rolepath.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.subinbabu.rolepath.database.entity.ApplicationStageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ApplicationStageDao {

    @Query(
        """
        SELECT * FROM application_stages
        WHERE applicationId = :applicationId
        ORDER BY position ASC
        """
    )
    fun observeStages(
        applicationId: String,
    ): Flow<List<ApplicationStageEntity>>

    @Query("SELECT * FROM application_stages WHERE id = :stageId")
    suspend fun getStageById(
        stageId: String,
    ): ApplicationStageEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStage(
        stage: ApplicationStageEntity,
    )

    /** Updates editable stage fields without changing parent application or position. */
    @Query(
        """
    UPDATE application_stages
    SET title = :title,
        type = :type,
        status = :status,
        outcome = :outcome,
        scheduledAtEpochMillis = :scheduledAtEpochMillis,
        scheduledZoneId = :scheduledZoneId,
        reminderOffsetMinutes = :reminderOffsetMinutes,
        meetingLink = :meetingLink,
        preparationNotes = :preparationNotes,
        updatedAtEpochMillis = :updatedAtEpochMillis
    WHERE id = :stageId
    """
    )
    suspend fun updateStageDetails(
        stageId: String,
        title: String,
        type: String,
        status: String,
        outcome: String,
        scheduledAtEpochMillis: Long?,
        scheduledZoneId: String?,
        reminderOffsetMinutes: Long?,
        meetingLink: String?,
        preparationNotes: String?,
        updatedAtEpochMillis: Long,
    ): Int

    /** Deletes a stage row. Parent timestamp and reminder cancellation are coordinated by the repository. */
    @Query("DELETE FROM application_stages WHERE id = :stageId")
    suspend fun deleteStage(
        stageId: String,
    ): Int

    /** Updates a stage position during pipeline reordering. */
    @Query(
        """
    UPDATE application_stages
    SET position = :position,
        updatedAtEpochMillis = :updatedAtEpochMillis
    WHERE id = :stageId
    """
    )
    suspend fun updateStagePosition(
        stageId: String,
        position: Int,
        updatedAtEpochMillis: Long,
    ): Int
}