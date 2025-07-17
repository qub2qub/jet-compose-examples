package com.example.jetcaster.core.model

import com.example.jetcaster.core.data.database.model.Category

data class CategoryInfo(val id: Long, val name: String)

const val CategoryTechnology = "Technology"

fun Category.asExternalModel() = CategoryInfo(
    id = id,
    name = name,
)
