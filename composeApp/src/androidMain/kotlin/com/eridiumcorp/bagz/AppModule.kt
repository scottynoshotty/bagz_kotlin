package com.eridiumcorp.bagz

import androidx.lifecycle.SavedStateHandle
import com.eridiumcorp.bagz.app.repositories.AccountsRepository
import com.eridiumcorp.bagz.app.repositories.ActivityRepository
import com.eridiumcorp.bagz.app.repositories.ReportsRepository
import com.eridiumcorp.bagz.app.repositories.TransactionsRepository
import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.app.services.JarvisService
import com.eridiumcorp.bagz.app.services.PlaidService
import com.eridiumcorp.bagz.components.accounts.details.AccountDetailsViewModel
import com.eridiumcorp.bagz.components.accounts.list.AccountListViewModel
import com.eridiumcorp.bagz.components.activity.ActivityScreenViewModel
import com.eridiumcorp.bagz.components.home.widgets.bag.BagWidgetViewModel
import com.eridiumcorp.bagz.components.home.screen.HomeViewModel
import com.eridiumcorp.bagz.components.home.widgets.accounts.AccountsWidgetViewModel
import com.eridiumcorp.bagz.components.home.widgets.activity.ActivityWidgetViewModel
import com.eridiumcorp.bagz.components.jarvis.JarvisViewModel
import com.eridiumcorp.bagz.components.landing.LandingViewModel
import com.eridiumcorp.bagz.components.link.LinkHostViewModel
import com.eridiumcorp.bagz.components.signin.SignInViewModel
import com.eridiumcorp.bagz.components.transactions.list.screen.TransactionsScreenViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.functions
import com.google.firebase.vertexai.vertexAI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { FirebaseFirestore.getInstance() }
    single { Firebase.auth }
    single { Firebase.functions }
    single { Firebase.vertexAI.generativeModel("gemini-1.5-flash") }
    single { AuthService(get()) }
    single { PlaidService(get()) }
    single { AccountsRepository(get(), get()) }
    single { ActivityRepository(get(), get()) }
    single { TransactionsRepository(get(), get()) }
    single { ReportsRepository(get(), get()) }
    single { JarvisService(get()) }
    viewModel { TransactionsScreenViewModel(get(), get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { LandingViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LinkHostViewModel(get()) }
    viewModel { BagWidgetViewModel(get()) }
    viewModel { AccountsWidgetViewModel(get()) }
    viewModel { AccountListViewModel(get()) }
    viewModel { ActivityScreenViewModel(get()) }
    viewModel { (savedStateHandle: SavedStateHandle) ->
        AccountDetailsViewModel(get(), get(), savedStateHandle)
    }
    viewModel { JarvisViewModel(get()) }
    viewModel { ActivityWidgetViewModel(get()) }
}