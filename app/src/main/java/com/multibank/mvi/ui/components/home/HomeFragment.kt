package com.multibank.mvi.ui.components.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.multibank.mvi.R
import com.multibank.mvi.databinding.FragmentHomeBinding
import com.multibank.mvi.ui.base.mvi.mviViewModel
import com.multibank.mvi.ui.components.home.recyclerview.TasksAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class HomeFragment: Fragment() {

    private val homeViewModel: HomeViewModel by mviViewModel()
    private var onStopDisposables = CompositeDisposable()
    private lateinit var binding: FragmentHomeBinding

    private val tasksAdapter = TasksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      //  return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTasksRecyclerView()

        binding.addTaskButton.setOnClickListener {
            homeViewModel.addTask("Order")

        }


    }

    override fun onStart() {
        super.onStart()
        homeViewModel.init(::render).addTo(onStopDisposables)
        homeViewModel.loadDataIfNeeded()
    }

    override fun onStop() {
        onStopDisposables.clear()
        super.onStop()
    }

    private fun render(viewState: HomeViewState) {
        with(viewState) {
            binding.progressBar.isVisible = inProgress
           // binding.newTaskInput.isEnabled = !inProgress
            binding.addTaskButton.isEnabled = !inProgress


            viewState.tasks?.let {
                tasksAdapter.updateList(it)
            }

           /* newTaskAdded?.consume {
                binding.newTaskInput.setText("")
            }*/

            error?.consume {
                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initTasksRecyclerView() {

        tasksAdapter.onClickListener = {taskId , onStatus ,task->

            when(onStatus){
                TasksAdapter.onStatus.ONCLICK->{
                    var action = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment(task)
                    findNavController().navigate(action)}
                else ->
                    homeViewModel.updateStatus(taskId,onStatus.toString())
            }



        }
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.tasksRecyclerView.overScrollMode = View.OVER_SCROLL_NEVER
        binding.tasksRecyclerView.adapter = tasksAdapter
    }
}