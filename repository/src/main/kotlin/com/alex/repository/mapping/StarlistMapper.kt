package com.alex.repository.mapping

import com.alex.database.starlist.DbModelStarlist
import com.alex.repository.model.RpModelList

// region - from database to repository

internal fun List<DbModelStarlist>.toRpModel() = map { RpModelList(it.id, it.name) }

// endregion