package com.alex.comicdiscovery.util.mapping

import com.alex.comicdiscovery.repository.api.models.ApiModelImage
import com.alex.comicdiscovery.repository.models.RpModelImage

object ImageMapper {

    // from api to repository

    fun mapApiModelToRpModel(input: ApiModelImage): RpModelImage {
        return RpModelImage(input.smallUrl)
    }

    // from string to repository

    fun mapStringToRpModel(input: String) = RpModelImage(input)
}