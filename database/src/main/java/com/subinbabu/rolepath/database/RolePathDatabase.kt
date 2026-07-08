package com.subinbabu.rolepath.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.subinbabu.rolepath.database.dao.ApplicationStageDao
import com.subinbabu.rolepath.database.dao.ApplicationStatusHistoryDao
import com.subinbabu.rolepath.database.dao.JobApplicationDao
import com.subinbabu.rolepath.database.entity.ApplicationStageEntity
import com.subinbabu.rolepath.database.entity.ApplicationStatusHistoryEntity
import com.subinbabu.rolepath.database.entity.JobApplicationEntity

@Database(
    entities = [
        JobApplicationEntity::class,
        ApplicationStageEntity::class,
        ApplicationStatusHistoryEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class RolePathDatabase : RoomDatabase() {

    abstract fun jobApplicationDao(): JobApplicationDao

    abstract fun applicationStageDao(): ApplicationStageDao

    abstract fun applicationStatusHistoryDao(): ApplicationStatusHistoryDao

    companion object {
        const val DATABASE_NAME = "rolepath.db"
    }
}