package com.subinbabu.rolepath.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.subinbabu.rolepath.database.entity.ApplicationStatusHistoryEntity

@Dao
interface ApplicationStatusHistoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStatusHistory(
        history: ApplicationStatusHistoryEntity,
    )
}