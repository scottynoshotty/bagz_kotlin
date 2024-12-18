package com.eridiumcorp.bagz.components.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class AppViewModel : ViewModel() {
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                println("$ERROR_TAG, ${throwable.message.orEmpty()}")
            },
            block = block
        )

    companion object {
        const val ERROR_TAG = "Bagz APP ERROR"
    }
}