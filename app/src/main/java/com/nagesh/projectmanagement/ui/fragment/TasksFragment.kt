package com.nagesh.projectmanagement.ui.fragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nagesh.projectmanagement.Utils.showMessage
import com.nagesh.projectmanagement.adapters.TaskAdapter
import com.nagesh.projectmanagement.databinding.FragmentTasksBinding
import com.nagesh.projectmanagement.models.Project
import com.nagesh.projectmanagement.repository.DataRepository
import com.nagesh.projectmanagement.repository.DataRepository.getTasks
import com.nagesh.projectmanagement.ui.activity.MainActivity

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private var project: Project? = null
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).binding.tvToolbarTitle.text = "Tasks"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            project = arguments?.getParcelable("project", Project::class.java)
        }else{
            project = arguments?.getParcelable("project") as Project?
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set Header Data
        binding.tvHeaderTitle.text = project?.name
        binding.tvHeaderDesc.text = project?.description

        // Setup Tasks List
        val tasks = getTasks(project?.id)
        adapter = TaskAdapter(tasks)
        binding.rvTasks.adapter = adapter
    }

    // New helper methods for MainActivity
    fun getProjectId(): Long = project?.id ?: -1

    fun filterTasks(query: String) {
        Log.d("ProjectsFragment", "Adapter: $adapter")
        adapter.updateList(DataRepository.searchTasks(query))
    }

    fun refresh() {
        val tasks = getTasks(project?.id)
        (binding.rvTasks.adapter as? TaskAdapter)?.updateList(tasks)
        showMessage(requireView(), "Successfull", Color.BLACK, Color.WHITE)
    }

}