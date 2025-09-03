package com.typer.typerush.core.di

import com.typer.typerush.auth.data.datasource.AuthRemoteDataSource
import com.typer.typerush.auth.data.datasource.UserRemoteDataSource
import com.typer.typerush.auth.data.repository.AuthRepositoryImpl
import com.typer.typerush.auth.data.repository.UserRepositoryImpl
import com.typer.typerush.auth.domain.repository.AuthRepository
import com.typer.typerush.auth.domain.repository.UserRepository
import com.typer.typerush.auth.presentation.AuthViewModel
import com.typer.typerush.compete.data.handler.CompeteHandler
import com.typer.typerush.compete.data.repository.CompeteRepositoryImpl
import com.typer.typerush.compete.domain.repository.CompeteRepository
import com.typer.typerush.compete.presentation.CompeteViewModel
import com.typer.typerush.core.CurrentContest
import com.typer.typerush.core.CurrentGameProvider
import com.typer.typerush.core.api.FirebaseTokenProvider
import com.typer.typerush.core.httpClient.HttpClientFactory
import com.typer.typerush.core.session.SessionManager
import com.typer.typerush.core.websocket.EventBus
import com.typer.typerush.core.websocket.WebSocketHandler
import com.typer.typerush.core.websocket.WebSocketService
import com.typer.typerush.create_contest.data.handler.CreateContestHandler
import com.typer.typerush.create_contest.data.repository.CreateContestRepositoryImpl
import com.typer.typerush.create_contest.domain.repository.CreateContestRepository
import com.typer.typerush.create_contest.presentation.CreateContestViewModel
import com.typer.typerush.landing_home.LandingHomeViewModel
import com.typer.typerush.navigation.NavigationManager
import com.typer.typerush.practice.data.datasource.PracticeRemoteDatasource
import com.typer.typerush.practice.data.repository.PracticeRepositoryImpl
import com.typer.typerush.practice.domain.repository.PracticeRepository
import com.typer.typerush.practice.presentation.PracticeViewModel
import com.typer.typerush.splash.SplashViewModel
import com.typer.typerush.typetest.data.datasource.TypeTestRemoteDatasource
import com.typer.typerush.typetest.data.handlers.TypeTestHandlers
import com.typer.typerush.typetest.data.repository.TypeTestRepositoryImpl
import com.typer.typerush.typetest.domain.repository.TypeTestRepository
import com.typer.typerush.typetest.presentation.TypeTestViewModel
import com.typer.typerush.waiting_page.data.handlers.WaitingHandler
import com.typer.typerush.waiting_page.data.repository.WaitingRepositoryImpl
import com.typer.typerush.waiting_page.domain.repository.WaitingRepository
import com.typer.typerush.waiting_page.presentation.WaitingViewModel
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

    single<HttpClient> (named("withWebSocket")) {
        HttpClientFactory.createWithWebSocket(tokenProvider = get())
    }

    singleOf(::SessionManager)
    singleOf(::CurrentContest)
    singleOf(::CurrentGameProvider)
    singleOf(::CurrentGameProvider)
    singleOf(::EventBus)

    single {
        WebSocketService(
            client = get(named("withWebSocket")), eventBus = get(),
            handlers = getAll<WebSocketHandler>()
        )
    }

    //handler
    singleOf(::CompeteHandler) bind(WebSocketHandler::class)
    singleOf(::CreateContestHandler) bind(WebSocketHandler::class)
    singleOf(::WaitingHandler) bind(WebSocketHandler::class)
    singleOf(::TypeTestHandlers) bind(WebSocketHandler::class)

    //datasource
    factory { AuthRemoteDataSource(client = get(named("withTokenHeader"))) }
    factory { UserRemoteDataSource(client = get(named("withTokenHeader"))) }
    factory { PracticeRemoteDatasource(client = get(named("withTokenHeader"))) }
    factory { TypeTestRemoteDatasource(client = get(named("withTokenHeader"))) }

    //repository
    singleOf(::AuthRepositoryImpl) bind(AuthRepository::class)
    singleOf(::UserRepositoryImpl) bind(UserRepository::class)
    singleOf(::PracticeRepositoryImpl) bind(PracticeRepository::class)
    singleOf(::CompeteRepositoryImpl) bind(CompeteRepository::class)
    singleOf(::CreateContestRepositoryImpl) bind(CreateContestRepository::class)
    singleOf(::WaitingRepositoryImpl) bind(WaitingRepository::class)
    singleOf(::TypeTestRepositoryImpl) bind(TypeTestRepository::class)

    //viewModels
    viewModelOf(::AuthViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::PracticeViewModel)
    viewModelOf(::LandingHomeViewModel)
    viewModelOf(::CompeteViewModel)
    viewModelOf(::CreateContestViewModel)
    viewModelOf(::WaitingViewModel)
    viewModelOf(::TypeTestViewModel)
}