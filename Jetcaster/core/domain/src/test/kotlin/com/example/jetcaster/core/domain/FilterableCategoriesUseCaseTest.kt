package com.example.jetcaster.core.domain

import com.example.jetcaster.core.data.database.model.Category
import com.example.jetcaster.core.data.testing.repository.TestCategoryStore
import com.example.jetcaster.core.model.asExternalModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterableCategoriesUseCaseTest {

    private val categoriesStore = TestCategoryStore()
    private val testCategories = listOf(
        Category(1, "News"),
        Category(2, "Arts"),
        Category(4, "Technology"),
        Category(2, "TV & Film"),
    )

    val useCase = FilterableCategoriesUseCase(
        categoryStore = categoriesStore,
    )

    @Before
    fun setUp() {
        categoriesStore.setCategories(testCategories)
    }

    @Test
    fun whenNoSelectedCategory_onEmptySelectedCategoryInvoked() = runTest {
        val filterableCategories = useCase(null).first()
        assertEquals(
            filterableCategories.categories[0],
            filterableCategories.selectedCategory,
        )
    }

    @Test
    fun whenSelectedCategory_correctFilterableCategoryIsSelected() = runTest {
        val selectedCategory = testCategories[2]
        val filterableCategories = useCase(selectedCategory.asExternalModel()).first()
        assertEquals(
            selectedCategory.asExternalModel(),
            filterableCategories.selectedCategory,
        )
    }
}
