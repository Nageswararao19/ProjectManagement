package com.nagesh.projectmanagement.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nagesh.projectmanagement.R
import com.nagesh.projectmanagement.Utils.showMessage
import com.nagesh.projectmanagement.adapters.ProjectAdapter
import com.nagesh.projectmanagement.databinding.FragmentProjectsBinding
import com.nagesh.projectmanagement.repository.DataRepository
import com.nagesh.projectmanagement.repository.DataRepository.getProjects

class ProjectsFragment : Fragment() {

    lateinit var binding: FragmentProjectsBinding
    lateinit var adapter: ProjectAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ProjectsFragment", "onAttach:Called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ProjectsFragment", "onCreate:Called")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ProjectsFragment", "onCreateView:Called")
        // Inflate the layout for this fragment
        binding = FragmentProjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ProjectsFragment", "onViewCreated:Called")
        adapter = ProjectAdapter(getProjects()) { project ->
            val action = ProjectsFragmentDirections.actionProjectsFragmentToTasksFragment(project)
            findNavController().navigate(action)
        }
        Log.d("ProjectsFragment", "Adapter: $adapter")
        binding.projectRecyclerview.adapter = adapter
    }

    fun filterProjects(query: String) {
        Log.d("ProjectsFragment", "Adapter: $adapter")
        adapter.updateList(DataRepository.searchProjects(query))
    }

    fun refresh() {
        adapter.updateList(getProjects())
        showMessage(requireView(), "Successfull", Color.BLACK, Color.WHITE)
    }

    override fun onResume() {
        super.onResume()
        Log.d("ProjectsFragment", "onResume:Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ProjectsFragment", "onPause:Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ProjectsFragment", "onStop:Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ProjectsFragment", "onDestroy:Called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ProjectsFragment", "onDestroyView:Called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("ProjectsFragment", "onDetach:Called")
    }
    override fun onStart() {
        super.onStart()
        Log.d("ProjectsFragment", "onStart:Called")
    }

}