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
}