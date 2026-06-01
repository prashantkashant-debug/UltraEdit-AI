package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.api.Content
import com.example.api.GenerateContentRequest
import com.example.api.Part
import com.example.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AiScriptUiState {
    object Idle : AiScriptUiState()
    object Loading : AiScriptUiState()
    data class Success(val script: String) : AiScriptUiState()
    data class Error(val message: String) : AiScriptUiState()
}

class AiScriptViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<AiScriptUiState>(AiScriptUiState.Idle)
    val uiState: StateFlow<AiScriptUiState> = _uiState.asStateFlow()

    fun generateScript(topic: String, tone: String, length: String) {
        if (topic.isBlank()) return
        
        _uiState.value = AiScriptUiState.Loading

        val prompt = """
            Create a detailed video script for a $length video about "$topic". 
            The tone should be $tone. 
            Format the output with timestamps (e.g., [00:00 - 00:15]) and clearly separate the VISUAL instructions from the AUDIO (Voiceover/Music).
            Keep it professional, engaging, and ready for production.
        """.trimIndent()

        viewModelScope.launch {
            try {
                val request = GenerateContentRequest(
                    contents = listOf(
                        Content(parts = listOf(Part(text = prompt)))
                    )
                )
                val apiKey = BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                    _uiState.value = AiScriptUiState.Error("API Key is missing. Please add it via AI Studio Secrets.")
                    return@launch
                }
                
                val response = RetrofitClient.service.generateContent(apiKey, request)
                val text = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                
                if (text != null) {
                    _uiState.value = AiScriptUiState.Success(text)
                } else {
                    _uiState.value = AiScriptUiState.Error("Failed to parse AI response.")
                }
            } catch (e: Exception) {
                _uiState.value = AiScriptUiState.Error("Network Error: ${e.localizedMessage}")
            }
        }
    }
}
