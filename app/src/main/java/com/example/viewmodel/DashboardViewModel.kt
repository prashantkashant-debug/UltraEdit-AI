package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ProjectEntity
import com.example.data.ProjectRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: ProjectRepository) : ViewModel() {

    val uiState: StateFlow<List<ProjectEntity>> = repository.allProjects
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createProject(title: String, onProjectCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val project = ProjectEntity(title = title, durationString = "00:00")
            val id = repository.insert(project)
            onProjectCreated(id.toInt())
        }
    }

    fun deleteProject(id: Int) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}

class DashboardViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
