package com.example.jetcaster.tv.model

import androidx.compose.runtime.Immutable
import com.example.jetcaster.core.model.CategoryInfo

data class CategorySelection(val categoryInfo: CategoryInfo, val isSelected: Boolean = false)

@Immutable
data class CategorySelectionList(val member: List<CategorySelection>) : List<CategorySelection> by member
