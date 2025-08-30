package com.typer.typerush.core.di

import com.typer.typerush.auth.data.datasource.AuthRemoteDataSource
import com.typer.typerush.auth.data.datasource.UserRemoteDataSource
import com.typer.typerush.auth.data.repository.AuthRepositoryImpl
import com.typer.typerush.auth.data.repository.UserRepositoryImpl
import com.typer.typerush.auth.domain.repository.AuthRepository
import com.typer.typerush.auth.domain.repository.UserRepository
import com.typer.typerush.auth.presentation.AuthViewModel
import com.typer.typerush.core.api.FirebaseTokenProvider
import com.typer.typerush.core.api.HttpClientFactory
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.landing_home.LandingHomeViewModel
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.practice.data.datasource.PracticeRemoteDatasource
import com.typer.typerush.practice.data.repository.PracticeRepositoryImpl
import com.typer.typerush.practice.domain.repository.PracticeRepository
import com.typer.typerush.practice.presentation.PracticeViewModel
import com.typer.typerush.splash.SplashViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    //miscellaneous
    singleOf(::NavigationManager)
    singleOf(::FirebaseTokenProvider)

    single<HttpClientEngine> {
        Android.create()
    }

    single<HttpClient> (named("withoutTokenHeader")) {
        HttpClientFactory.create(engine = get())
    }

    single<HttpClient> (named("withTokenHeader")) {
        HttpClientFactory.createWithToken(engine = get(), tokenProvider = get())
    }

    singleOf(::SessionManager)

    //datasource
    factory { AuthRemoteDataSource(client = get(named("withTokenHeader"))) }
    factory { UserRemoteDataSource(client = get(named("withTokenHeader"))) }
    factory { PracticeRemoteDatasource(client = get(named("withTokenHeader"))) }

    //repository
    singleOf(::AuthRepositoryImpl) bind(AuthRepository::class)
    singleOf(::UserRepositoryImpl) bind(UserRepository::class)
    singleOf(::PracticeRepositoryImpl) bind(PracticeRepository::class)

    //viewModels
    viewModelOf(::AuthViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::PracticeViewModel)
    viewModelOf(::LandingHomeViewModel)
}