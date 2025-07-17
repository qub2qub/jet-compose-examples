package com.example.jetcaster.tv.model

import androidx.compose.runtime.Immutable
import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.model.CategoryInfo
import com.example.jetcaster.core.model.asExternalModel

@Immutable
data class CategoryInfoList(val member: List<CategoryInfo>) : List<CategoryInfo> by member {

    fun intoCategoryList(): List<Category> {
        return map(CategoryInfo::intoCategory)
    }

    companion object {
        fun from(list: List<Category>): CategoryInfoList {
            val member = list.map(Category::asExternalModel)
            return CategoryInfoList(member)
        }
    }
}

private fun CategoryInfo.intoCategory(): Category {
    return Category(id, name)
}
