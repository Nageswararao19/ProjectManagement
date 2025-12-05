package com.nagesh.projectmanagement.repository

import com.nagesh.projectmanagement.models.Project
import com.nagesh.projectmanagement.models.Task

object DataRepository {

    // Mock Data
    private val projects = mutableListOf(
        Project(
            1,
            "HR Management Portal",
            "Developing a secure portal for employee payroll.",
            "2023-11-20",
            "2024-01-30",
            "In Progress"
        ),
        Project(2, "E-Commerce Revamp", "Complete overhaul of the legacy frontend.", "2023-10-01", "2024-02-15", "Planned"),
        Project(3, "Marketing Campaign", "Coordination of social media assets.", "2023-08-15", "2023-10-30", "Completed")
    )

    private val tasks = mutableListOf(
        Task(101, 1, "Database Schema", "Create ER diagrams.", "Alex M.", "2023-12-05", "Pending"),
        Task(102, 1, "Auth API", "Implement JWT.", "Sarah J.", "2023-12-10", "In Progress"),
        Task(201, 2, "Payment Gateway", "Stripe integration fixes.", "Mike T.", "2023-11-28", "Done")
    )

    fun getProjects(): List<Project> = projects
    fun getTasks(projectId: Long?): List<Task> = tasks.filter { it.projectId == projectId }

    fun addProject(project: Project) { projects.add(0, project) }
    fun addTask(task: Task) { tasks.add(0, task) }

    fun searchProjects(query: String): List<Project> {
        return projects.filter { it.name.contains(query, true) || it.description.contains(query, true) }
    }

    fun searchTasks(query: String): List<Task> {
        return tasks.filter { it.title.contains(query, true) || it.description.contains(query, true) }
    }
}