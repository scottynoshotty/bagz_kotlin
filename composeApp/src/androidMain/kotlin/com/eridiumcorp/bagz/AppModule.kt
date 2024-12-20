package com.eridiumcorp.bagz

import com.eridiumcorp.bagz.app.services.AuthService
import com.eridiumcorp.bagz.app.services.PlaidService
import com.eridiumcorp.bagz.components.home.HomeViewModel
import com.eridiumcorp.bagz.components.landing.LandingViewModel
import com.eridiumcorp.bagz.components.signin.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInViewModel(androidContext()) }
    viewModel { LandingViewModel() }
    viewModel { HomeViewModel() }
    single { AuthService() }
    single { PlaidService() }
}