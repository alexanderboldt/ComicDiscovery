package com.alex.repository.mapping

import com.alex.datastore.settings.model.DsModelTheme
import com.alex.repository.model.RpModelTheme

// from DataStore to Repository
internal fun DsModelTheme.toRpModel() = RpModelTheme.values()[ordinal]

// from Repository to DataStore
internal fun RpModelTheme.toDsModel() = DsModelTheme.values()[ordinal]