package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.DsModelTheme
import com.alex.comicdiscovery.repository.models.RpModelTheme

// from DataStore to Repository
fun DsModelTheme.toRpModel() = RpModelTheme.values()[ordinal]

// from Repository to DataStore
fun RpModelTheme.toDsModel() = DsModelTheme.values()[ordinal]