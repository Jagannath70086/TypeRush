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
import com.typer.typerush.navigation.NavigationManager
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
    single { AuthRemoteDataSource(client = get(named("withTokenHeader"))) }
    single { UserRemoteDataSource(client = get(named("withTokenHeader"))) }

    //repository
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    //viewModels
    viewModelOf(::AuthViewModel)
    viewModelOf(::SplashViewModel)
}