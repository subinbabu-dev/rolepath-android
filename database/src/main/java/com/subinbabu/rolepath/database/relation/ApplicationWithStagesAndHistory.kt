package com.subinbabu.rolepath.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.subinbabu.rolepath.database.entity.ApplicationStageEntity
import com.subinbabu.rolepath.database.entity.ApplicationStatusHistoryEntity
import com.subinbabu.rolepath.database.entity.JobApplicationEntity

data class ApplicationWithStagesAndHistory(
    @Embedded
    val application: JobApplicationEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "applicationId",
    )
    val stages: List<ApplicationStageEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "applicationId",
    )
    val statusHistory: List<ApplicationStatusHistoryEntity>,
)