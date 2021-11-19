package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.repository.datasource.database.starlist.DbModelStarlist
import com.alex.comicdiscovery.repository.models.RpModelList

// region - from database to repository

fun List<DbModelStarlist>.toRpModel() = map { RpModelList(it.id, it.name) }

// endregion