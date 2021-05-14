package container.restaurant.android.di

import container.restaurant.android.presentation.MainViewModel
import container.restaurant.android.presentation.feed.FeedViewModel
import container.restaurant.android.presentation.feed.category.FeedCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel() }
    viewModel { FeedViewModel() }
    viewModel { FeedCategoryViewModel() }
}