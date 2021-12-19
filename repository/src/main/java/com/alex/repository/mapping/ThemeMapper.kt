package com.alex.repository.mapping

import com.alex.comicdiscovery.DsModelTheme
import com.alex.repository.models.RpModelTheme

// from DataStore to Repository
internal fun DsModelTheme.toRpModel() = RpModelTheme.values()[ordinal]

// from Repository to DataStore
internal fun RpModelTheme.toDsModel() = DsModelTheme.values()[ordinal]