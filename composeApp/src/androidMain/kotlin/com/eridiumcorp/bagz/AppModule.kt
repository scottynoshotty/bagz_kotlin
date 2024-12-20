package com.eridiumcorp.bagz

import com.eridiumcorp.bagz.components.signin.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInViewModel() }
}