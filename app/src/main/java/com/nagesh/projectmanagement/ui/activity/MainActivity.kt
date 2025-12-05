package com.nagesh.projectmanagement.ui.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.nagesh.projectmanagement.R
import com.nagesh.projectmanagement.Utils.dateToMillis
import com.nagesh.projectmanagement.databinding.ActivityMainBinding
import com.nagesh.projectmanagement.models.Project
import com.nagesh.projectmanagement.models.Task
import com.nagesh.projectmanagement.repository.DataRepository.addProject
import com.nagesh.projectmanagement.repository.DataRepository.addTask
import com.nagesh.projectmanagement.ui.fragment.ProjectsFragment
import com.nagesh.projectmanagement.ui.fragment.TasksFragment
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment = ProjectsFragment()
    var btnSearchVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvToolbarTitle.text = "Dashboard"

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment

        val navController = navHostFragment.navController
        navController.currentDestination?.id

        // Drawer Nav Logic
        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> {
                    navController.navigate(R.id.projectsFragment)
                    binding.tvToolbarTitle.text = "Dashboard"
                }

                R.id.add_project -> {
                    showCreateProjectDialog()
                }
//              R.id.menu_settings -> toggleTheme()
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        // Search Logic
        binding.btnSearch.setOnClickListener {
            btnSearchVisible = !btnSearchVisible
            binding.etSearch.visibility = if (btnSearchVisible) View.GONE else View.VISIBLE
        }

        binding.etSearch.addTextChangedListener { text ->
            val fragmentCurrent = navHostFragment.childFragmentManager.fragments
                .firstOrNull()
            val query = text.toString()
            when (fragmentCurrent) {
                is ProjectsFragment -> {
                    currentFragment = ProjectsFragment()
                    fragmentCurrent.filterProjects(query)
                }

                is TasksFragment -> {
                    currentFragment = TasksFragment()
                    fragmentCurrent.filterTasks(query)
                }
            }
        }

        // FAB Logic
        binding.fab.setOnClickListener {
            val fragmentCurrent = navHostFragment.childFragmentManager.fragments.firstOrNull()
            when (fragmentCurrent) {
                is ProjectsFragment -> {
                    currentFragment = fragmentCurrent
                    showCreateProjectDialog()
                }

                is TasksFragment -> {
                    currentFragment = fragmentCurrent
                    val projectId = (currentFragment as TasksFragment).getProjectId()
                    showCreateTaskDialog(projectId)
                }
            }
        }
    }

    // --- DIALOG IMPLEMENTATIONS ---
    private fun showCreateProjectDialog() {
        var isAllEntered = false
        var selectedStatusItem = -1
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_project, null)
        val etName = dialogView.findViewById<EditText>(R.id.etProjectName)
        val etDesc = dialogView.findViewById<EditText>(R.id.etProjectDesc)
        val etStart = dialogView.findViewById<EditText>(R.id.etStartDate)
        val etEnd = dialogView.findViewById<EditText>(R.id.etEndDate)
        val cancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val create = dialogView.findViewById<Button>(R.id.btnCreate)
        val status = dialogView.findViewById<AutoCompleteTextView>(R.id.actProjectStatus)
        val statusList = listOf("Planned", "Completed", "In Progress")

        //startDate
        etStart.setOnClickListener {
            getDate(etStart.text.toString()) { date ->
                etStart.setText(date)
            }
        }

        //endDate
        etEnd.setOnClickListener {
            getDate(etEnd.text.toString()) { date ->
                etEnd.setText(date)
            }
        }

        status.setAdapter(
            ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                statusList
            )
        )
        status.setOnClickListener {
            status.showDropDown()
        }
        status.setOnItemClickListener { _, _, position, _ ->
            status.setSelection(position)
            selectedStatusItem = position
        }

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        create.setOnClickListener {
            val name = etName.text.toString()
            val desc = etDesc.text.toString()
            val startDate = etStart.text.toString()
            val endDate = etEnd.text.toString()
            if (name.isEmpty() && desc.isEmpty() && startDate.isEmpty() && endDate.isEmpty() && selectedStatusItem == -1) {
                etName.error = "Name is required"
                etDesc.error = "Description is required"
                etStart.error = "Start Date is required"
                etEnd.error = "End Date is required"
                status.error = "Select a status"
            }
            if (name.isEmpty()) {
                etName.error = "Name is required"
            } else {
                etName.error = null
                isAllEntered = true
            }
            if (desc.isEmpty()) {
                etDesc.error = "Description is required"
            } else {
                etDesc.error = null
                isAllEntered = true
            }
            if (startDate.isEmpty()) {
                etStart.error = "Start Date is required"
            } else {
                etStart.error = null
                isAllEntered = true
            }
            if (endDate.isEmpty()) {
                etEnd.error = "End Date is required"
            } else {
                etEnd.error = null
                isAllEntered = true
            }
            if (selectedStatusItem == -1) {
                status.error = "Select a status"
            } else {
                status.error = null
                isAllEntered = true
            }

            if (isAllEntered) {
                val project = Project(
                    System.currentTimeMillis(),
                    name,
                    desc,
                    etStart.text.toString(),
                    etEnd.text.toString(),
                    statusList[selectedStatusItem]
                )
                addProject(project)
                // Refresh Dashboard
                (currentFragment as? ProjectsFragment)?.refresh()
                alertDialog.dismiss()
            }
        }
        cancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun showCreateTaskDialog(projectId: Long) {
        val calendar = Calendar.getInstance()
        var isAllEntered = false
        var selectedStatusItem = -1
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etDesc = dialogView.findViewById<EditText>(R.id.etTaskDesc)
        val etAssigned = dialogView.findViewById<EditText>(R.id.etAssignedTo)
        val etDue = dialogView.findViewById<EditText>(R.id.etDueDate)
        val cancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val createTask = dialogView.findViewById<Button>(R.id.btnCreateTask)
        val status = dialogView.findViewById<AutoCompleteTextView>(R.id.actTaskStatus)

        etDue.setOnClickListener {
            getDate(etDue.text.toString()){ date ->
                etDue.setText(date)
            }
        }

        val statusList = listOf("Pending", "In Progress", "Done")
        status.setAdapter(
            ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_list_item_1,
                statusList
            )
        )
        status.setOnClickListener {
            status.showDropDown()
        }
        status.setOnItemClickListener { _, _, position, _ ->
            status.setSelection(position)
            selectedStatusItem = position
        }

        val dialogue = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
        createTask.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDesc.text.toString()
            val assigned = etAssigned.text.toString()
            val due = etDue.text.toString()

            if (title.isEmpty() && desc.isEmpty() && assigned.isEmpty() && due.isEmpty() && selectedStatusItem == -1) {
                etTitle.error = "Name is required"
                etDesc.error = "Description is required"
                etAssigned.error = "Please enter assigned to"
                etDue.error = "Due Date is required"
                status.error = "Select a status"
            }
            if (title.isEmpty()) {
                etTitle.error = "Name is required"
            } else {
                etTitle.error = null
                isAllEntered = true
            }
            if (desc.isEmpty()) {
                etDesc.error = "Description is required"
            } else {
                etDesc.error = null
                isAllEntered = true
            }
            if (selectedStatusItem == -1) {
                status.error = "Select a status"
            } else {
                status.error = null
                isAllEntered = true
            }
            if (due.isEmpty()) {
                etDue.error = "Start Date is required"
            } else {
                etDue.error = null
                isAllEntered = true
            }

            if (assigned.isEmpty()) {
                etAssigned.error = "Select a status"
            } else {
                etAssigned.error = null
                isAllEntered = true
            }

            if (isAllEntered) {
                val task = Task(
                    System.currentTimeMillis(),
                    projectId,
                    title,
                    etDesc.text.toString(),
                    etAssigned.text.toString(),
                    etDue.text.toString(),
                    statusList[selectedStatusItem]
                )
                addTask(task)
                // Refresh Detail Fragment
                (currentFragment as? TasksFragment)?.refresh()
                dialogue.dismiss()
            }
        }
        cancel.setOnClickListener {
            dialogue.dismiss()
        }
    }

    fun getDate(selectedDate:String?, dateListener: (String) -> Unit) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(if(!selectedDate.isNullOrEmpty()) dateToMillis(selectedDate) else{MaterialDatePicker.todayInUtcMilliseconds()})
                .build()

        datePicker.show(supportFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->
            val date = Instant.ofEpochMilli(selection)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()

            val formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            dateListener(formatted)
        }
    }

}