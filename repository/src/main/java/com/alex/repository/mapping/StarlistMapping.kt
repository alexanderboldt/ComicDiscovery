package com.alex.repository.mapping

import com.alex.repository.datasource.database.starlist.DbModelStarlist
import com.alex.repository.models.RpModelList

// region - from database to repository

internal fun List<DbModelStarlist>.toRpModel() = map { RpModelList(it.id, it.name) }

// endregion