package com.example.branchtask.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.branchtask.R
import com.example.branchtask.common.Resource
import com.example.branchtask.databinding.FragmentHomeScreenBinding
import com.example.branchtask.domain.models.HomemodelItem
import com.example.branchtask.presentation.HomeAdapter
import com.example.branchtask.presentation.HomeVewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : HomeAdapter

    private val homeviewmodel by viewModels<HomeVewModel>()

    private val list : ArrayList<HomemodelItem> =  arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        adapter = HomeAdapter(::onItemClicked)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
        homeviewmodel.getAllNotes()
        binding.homerecycler.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
        binding.homerecycler.adapter = adapter
    }

    private fun bindObservers() {
        homeviewmodel.notesLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when(it){
                is Resource.Success -> {
                    adapter.submitList(it.data)
                    it.data?.let { it1 -> list.addAll(it1) }
             //       Toast.makeText(activity,"done", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),it.message.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun onItemClicked(homeResponse: HomemodelItem) {
        val bundle = Bundle()
        bundle.putString("note", homeResponse.thread_id)
      //  bundle.putParcelable("data", homeResponse)
        findNavController().navigate(R.id.action_homeScreen_to_itemFarg, bundle)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}