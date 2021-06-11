package container.restaurant.android.di

import androidx.appcompat.app.AppCompatActivity
import container.restaurant.android.presentation.MainViewModel
import container.restaurant.android.presentation.NavigationController
import container.restaurant.android.presentation.auth.AuthViewModel
import container.restaurant.android.presentation.auth.AuthViewModelDelegate
import container.restaurant.android.presentation.feed.FeedViewModel
import container.restaurant.android.presentation.feed.all.FeedAllViewModel
import container.restaurant.android.presentation.feed.category.FeedCategoryViewModel
import container.restaurant.android.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val presentationModule = module {
    single { (activity: AppCompatActivity) -> NavigationController(activity) }
    single<AuthViewModelDelegate> { AuthViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { FeedViewModel() }
    viewModel { FeedCategoryViewModel(get(), it[0]) }
    viewModel { FeedAllViewModel() }
}