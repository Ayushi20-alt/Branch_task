package com.example.branchtask.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.branchtask.common.Resource
import com.example.branchtask.databinding.FragmentItemFargBinding
import com.example.branchtask.domain.models.HomemodelItem
import com.example.branchtask.domain.models.MessageRequest
import com.example.branchtask.presentation.HomeVewModel
import com.example.branchtask.presentation.SecondAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemFarg : Fragment(){

    private var _binding: FragmentItemFargBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : SecondAdapter

    private val homeviewmodel by viewModels<HomeVewModel>()

    private var threadidlist : List<HomemodelItem> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItemFargBinding.inflate(inflater, container, false)
        adapter = SecondAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeviewmodel.getAllNotes()

        var jsonnote = arguments?.getString("note")

        homeviewmodel.notesLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    if (it != null) {
                        threadidlist = it.data!!.filter { it.thread_id == jsonnote }
                    }
                    adapter.submitList(threadidlist)
                    binding.itemrecycler.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
                    binding.itemrecycler.adapter = adapter

              //      Toast.makeText(activity, "${jsonnote}", Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(activity, "loading", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(activity, "error", Toast.LENGTH_LONG).show()
                }
            }

        })



        binding.sendbutton.setOnClickListener()
        {
            bindObservers()
            if(jsonnote != null)
            {
                homeviewmodel.makeNew(MessageRequest(jsonnote, binding.editText.text.toString()))
            }
            binding.itemrecycler.layoutManager =  LinearLayoutManager(activity, LinearLayoutManager.VERTICAL ,false)
            binding.itemrecycler.adapter = adapter

        }

        binding.resetbutton.setOnClickListener{
      //      Toast.makeText(requireContext(),"CalledSucessfully", Toast.LENGTH_LONG).show()
            bindObservers2()
            homeviewmodel.resetdata()
        }


    }

    private fun bindObservers2() {
        homeviewmodel.resetData.observe(viewLifecycleOwner, Observer{
          when(it){
              is Resource.Success -> {
                homeviewmodel.getAllNotes()
                  Toast.makeText(activity,"reset sucessfully", Toast.LENGTH_LONG).show()
              }
              is Resource.Error -> {
                  Toast.makeText(requireContext(),it.message.toString(), Toast.LENGTH_LONG).show()
              }
              is Resource.Loading -> {
                  Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
              }
          }
        })
    }


    private fun bindObservers() {
        homeviewmodel.postLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    var list : ArrayList<HomemodelItem>? = it.data?.let { it1 ->
                        arrayListOf<HomemodelItem>(
                            it1.copy())
                    }
                    if (list != null) {
                        adapter.submitList(list + threadidlist)
                    }
                 //   Toast.makeText(activity,"doneitem", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),it.message.toString(), Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                }
            }
        })
    }

}