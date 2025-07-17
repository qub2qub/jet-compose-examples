package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.repository.CategoryStore
import com.example.jetcaster.core.model.CategoryInfo
import com.example.jetcaster.core.model.FilterableCategoriesModel
import com.example.jetcaster.core.model.asExternalModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case for categories that can be used to filter podcasts.
 */
class FilterableCategoriesUseCase @Inject constructor(private val categoryStore: CategoryStore) {
    /**
     * Created a [FilterableCategoriesModel] from the list of categories in [categoryStore].
     * @param selectedCategory the currently selected category. If null, the first category
     *        returned by the backing category list will be selected in the returned
     *        FilterableCategoriesModel
     */
    operator fun invoke(selectedCategory: CategoryInfo?): Flow<FilterableCategoriesModel> = categoryStore.categoriesSortedByPodcastCount()
        .map { categories ->
            FilterableCategoriesModel(
                categories = categories.map { it.asExternalModel() },
                selectedCategory = selectedCategory
                    ?: categories.firstOrNull()?.asExternalModel(),
            )
        }
}
