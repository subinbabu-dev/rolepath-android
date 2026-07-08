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

    @Query(
        """
        SELECT * FROM job_applications
        WHERE archivedAtEpochMillis IS NULL
        ORDER BY updatedAtEpochMillis DESC
        """
    )
    fun observeActiveApplications(): Flow<List<JobApplicationEntity>>

    @Transaction
    @Query("SELECT * FROM job_applications WHERE id = :applicationId")
    fun observeApplicationDetails(
        applicationId: String,
    ): Flow<ApplicationWithStagesAndHistory?>

    @Query("SELECT * FROM job_applications WHERE id = :applicationId")
    suspend fun getApplicationById(
        applicationId: String,
    ): JobApplicationEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertApplication(
        application: JobApplicationEntity,
    )
}