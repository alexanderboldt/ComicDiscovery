package com.alex.repository.util

internal const val idPrefix = "4005-"
internal const val fields = "id,name,real_name,image,gender,aliases,birth,powers,origin,deck"

// assembles the actual id with a prefix
internal val Int.withPrefix: String
    get() = idPrefix + this