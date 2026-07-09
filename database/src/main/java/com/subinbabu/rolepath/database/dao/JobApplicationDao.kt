package com.subinbabu.rolepath.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.subinbabu.rolepath.database.entity.JobApplicationEntity
import com.subinbabu.rolepath.database.relation.ApplicationWithStagesAndHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface JobApplicationDao {

    /** Observes non-archived applications ordered by most recently updated first. */
    @Query(
        """
        SELECT * FROM job_applications
        WHERE archivedAtEpochMillis IS NULL
        ORDER BY updatedAtEpochMillis DESC
        """
    )
    fun observeActiveApplications(): Flow<List<JobApplicationEntity>>

    /** Observes one application with its stages and status history for the details screen. */
    @Transaction
    @Query("SELECT * FROM job_applications WHERE id = :applicationId")
    fun observeApplicationDetails(
        applicationId: String,
    ): Flow<ApplicationWithStagesAndHistory?>

    /** Returns the current application row once, mainly for validation and transactional operations. */
    @Query("SELECT * FROM job_applications WHERE id = :applicationId")
    suspend fun getApplicationById(
        applicationId: String,
    ): JobApplicationEntity?

    /** Inserts a new application row. Initial status history is inserted separately in a transaction. */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplication(
        application: JobApplicationEntity,
    )

    /** Updates only the current status. Status-history insertion must be coordinated by the repository. */
    @Query(
        """
    UPDATE job_applications
    SET status = :status,
        updatedAtEpochMillis = :updatedAtEpochMillis
    WHERE id = :applicationId
    """
    )
    suspend fun updateApplicationStatus(
        applicationId: String,
        status: String,
        updatedAtEpochMillis: Long,
    ): Int

    /** Marks an application as archived without changing its application status. */
    @Query(
        """
    UPDATE job_applications
    SET archivedAtEpochMillis = :archivedAtEpochMillis,
        updatedAtEpochMillis = :updatedAtEpochMillis
    WHERE id = :applicationId
    """
    )
    suspend fun archiveApplication(
        applicationId: String,
        archivedAtEpochMillis: Long,
        updatedAtEpochMillis: Long,
    ): Int

    /** Restores an archived application without changing its application status. */
    @Query(
        """
    UPDATE job_applications
    SET archivedAtEpochMillis = NULL,
        updatedAtEpochMillis = :updatedAtEpochMillis
    WHERE id = :applicationId
    """
    )
    suspend fun restoreArchivedApplication(
        applicationId: String,
        updatedAtEpochMillis: Long,
    ): Int

    /** Deletes an application. Dependent stages and status history are removed by foreign-key cascade. */
    @Query("DELETE FROM job_applications WHERE id = :applicationId")
    suspend fun deleteApplication(
        applicationId: String,
    ): Int
}