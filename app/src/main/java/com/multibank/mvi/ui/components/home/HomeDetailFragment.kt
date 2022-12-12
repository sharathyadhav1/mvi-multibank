package com.multibank.mvi.ui.components.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.multibank.mvi.databinding.FragmentHomeBinding
import com.multibank.mvi.databinding.FragmentHomeDetailBinding
import java.text.SimpleDateFormat
import java.util.*


class HomeDetailFragment : Fragment() {
    private val args: HomeDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHomeDetailBinding.inflate(layoutInflater)
        intialiseView()
        return binding.root

    }

    private fun intialiseView() {
       binding.apply {
           args.task.let {
               orderTitle.text = "order-${it?.id}"
               orderDate.text = "${convertLongToTime(it!!.createdDate)}"
           }

       }
    }

    fun convertLongToTime(time: Long): String {

        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}