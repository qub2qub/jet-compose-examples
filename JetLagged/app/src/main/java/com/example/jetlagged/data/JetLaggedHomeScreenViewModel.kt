package com.example.jetlagged.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetLaggedHomeScreenViewModel : ViewModel() {

    val uiState: StateFlow<JetLaggedHomeScreenState> = MutableStateFlow(JetLaggedHomeScreenState())
}
