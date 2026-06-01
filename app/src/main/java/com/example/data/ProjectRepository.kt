package com.example.data

import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insert(project: ProjectEntity): Long {
        return projectDao.insertProject(project)
    }

    suspend fun deleteById(id: Int) {
        projectDao.deleteProjectById(id)
    }
}
