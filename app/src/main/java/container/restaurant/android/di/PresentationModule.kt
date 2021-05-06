package container.restaurant.android.di

import container.restaurant.android.presentation.MainViewModel
import container.restaurant.android.presentation.feed.FeedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    viewModel { MainViewModel() }
    viewModel { FeedViewModel() }
}