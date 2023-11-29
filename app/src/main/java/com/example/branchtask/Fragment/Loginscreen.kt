package com.example.branchtask.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.branchtask.R
import com.example.branchtask.common.Resource
import com.example.branchtask.common.TokenManager
import com.example.branchtask.databinding.FragmentLoginscreenBinding
import com.example.branchtask.domain.models.Loginmodel
import com.example.branchtask.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Loginscreen : Fragment(){

    private var _binding: FragmentLoginscreenBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginscreenBinding.inflate(inflater, container, false)
          if(tokenManager.getToken() != null)
          {
              findNavController().navigate(R.id.action_loginscreen_to_homeScreen)
          }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            val validationResult = validateUserInput()
            if(validationResult.first)
            {
                val emailAddress = binding.usernameEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                authViewModel.loginuser(Loginmodel(emailAddress,password))
            }else{
                Toast.makeText(activity, "Invalid", Toast.LENGTH_LONG).show()
            }

        }

        bindObserver()

    }

    private fun bindObserver() {
        authViewModel.userRegisterLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(activity, "Authenticated user",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_loginscreen_to_homeScreen)
                    tokenManager.saveToken(it.data!!.auth_token)
                    Toast.makeText(activity,"token: ${it.data.auth_token}", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {}
            }
        })
    }


    private fun validateUserInput() : Pair<Boolean,String>{
        val emailAddress = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        return authViewModel.validatecredentials(emailAddress,  password)
    }
}