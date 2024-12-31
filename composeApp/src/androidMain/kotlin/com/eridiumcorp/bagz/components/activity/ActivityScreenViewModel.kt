package com.eridiumcorp.bagz.components.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eridiumcorp.bagz.app.repositories.ActivityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityScreenViewModel(val activityRepository: ActivityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityScreenUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            activityRepository.getActivity().collect { activity ->
                _uiState.value = _uiState.value.copy(loading = false, activity = activity)
            }
        }
    }
}