package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.DsModelTheme
import com.alex.comicdiscovery.repository.models.RpModelTheme

object ThemeMapper {

    // from datastore to repository

    fun mapDsModelThemeToRpModelTheme(input: DsModelTheme): RpModelTheme {
        return RpModelTheme.values()[input.ordinal]
    }

    // from repository to datastore

    fun mapRpModelThemeToDsModelTheme(input: RpModelTheme): DsModelTheme {
        return DsModelTheme.values()[input.ordinal]
    }
}