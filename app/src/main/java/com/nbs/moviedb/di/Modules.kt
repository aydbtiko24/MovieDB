package com.nbs.moviedb.di

import com.nbs.moviedb.data.repository.FavoriteRepositoryImpl
import com.nbs.moviedb.data.repository.MovieRepositoryImpl
import com.nbs.moviedb.data.source.local.AppDatabase
import com.nbs.moviedb.data.source.local.FavoriteLocalDataSource
import com.nbs.moviedb.data.source.local.FavoriteLocalDataSourceImpl
import com.nbs.moviedb.data.source.remote.ApiService
import com.nbs.moviedb.data.source.remote.MovieRemoteDataSource
import com.nbs.moviedb.data.source.remote.MovieRemoteDataSourceImpl
import com.nbs.moviedb.domain.repository.FavoriteRepository
import com.nbs.moviedb.domain.repository.MovieRepository
import com.nbs.moviedb.domain.usecase.favorite.AddFavorite
import com.nbs.moviedb.domain.usecase.favorite.GetFavorites
import com.nbs.moviedb.domain.usecase.favorite.GetIsFavorite
import com.nbs.moviedb.domain.usecase.favorite.RemoveFavorite
import com.nbs.moviedb.domain.usecase.movie.GetDetailMovie
import com.nbs.moviedb.domain.usecase.movie.GetHomeComingSoonMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomeDiscoverMovies
import com.nbs.moviedb.domain.usecase.movie.GetHomePopularMovies
import com.nbs.moviedb.domain.usecase.movie.GetMovieCast
import com.nbs.moviedb.domain.usecase.movie.GetPopularMovies
import com.nbs.moviedb.presentation.detail.DetailViewModel
import com.nbs.moviedb.presentation.detail.GetDetailMovieImpl
import com.nbs.moviedb.presentation.detail.GetMovieCastImpl
import com.nbs.moviedb.presentation.favorite.AddFavoriteImpl
import com.nbs.moviedb.presentation.favorite.FavoriteViewModel
import com.nbs.moviedb.presentation.favorite.GetFavoritesImpl
import com.nbs.moviedb.presentation.favorite.GetIsFavoriteImpl
import com.nbs.moviedb.presentation.favorite.RemoveFavoriteImpl
import com.nbs.moviedb.presentation.home.GetHomeComingSoonMoviesImpl
import com.nbs.moviedb.presentation.home.GetHomeDiscoverMovieImpl
import com.nbs.moviedb.presentation.home.GetHomePopularMoviesImpl
import com.nbs.moviedb.presentation.home.HomeViewModel
import com.nbs.moviedb.presentation.main.MainViewModel
import com.nbs.moviedb.presentation.popular.GetPopularMoviesImpl
import com.nbs.moviedb.presentation.popular.PopularViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
val dataModule = module {
    single { AppDatabase.create(androidApplication()) }
    single { ApiService.create() }
    // data source
    single<FavoriteLocalDataSource> {
        FavoriteLocalDataSourceImpl(get(AppDatabase::class.java).favoriteDao())
    }
    single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get()) }
    // repository
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}
val appModule = module {
    // movie usecase
    single<GetHomeDiscoverMovies> { GetHomeDiscoverMovieImpl(get()) }
    single<GetHomePopularMovies> { GetHomePopularMoviesImpl(get()) }
    single<GetHomeComingSoonMovies> { GetHomeComingSoonMoviesImpl(get()) }
    single<GetPopularMovies> { GetPopularMoviesImpl(get()) }
    single<GetDetailMovie> { GetDetailMovieImpl(get()) }
    single<GetMovieCast> { GetMovieCastImpl(get()) }
    // favorite usecase
    single<AddFavorite> { AddFavoriteImpl(get()) }
    single<GetFavorites> { GetFavoritesImpl(get()) }
    single<GetIsFavorite> { GetIsFavoriteImpl(get()) }
    single<RemoveFavorite> { RemoveFavoriteImpl(get()) }
    // viewmodel
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { PopularViewModel(get()) }
    viewModel { FavoriteViewModel(get(), get()) }
    viewModel { DetailViewModel(get(), get(), get(), get(), get(), get()) }
}