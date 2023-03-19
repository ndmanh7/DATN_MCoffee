package com.example.mcoffee.ui.login

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.R
import com.example.mcoffee.data.remote.AuthRequestState
import com.example.mcoffee.databinding.FragmentLoginBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            viewModel.loginState.collectLatest {
                handleAuthRequestState(it)
            }
        }
    }


    override fun bindView() {
        super.bindView()
        auth = Firebase.auth
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    edtLoginEmail.text.toString(),
                    edtLoginPassword.text.toString(),
                )
            }

            btnToRegisterFragment.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun handleAuthRequestState(authRequestState: AuthRequestState) {
        when (authRequestState) {
            is AuthRequestState.Success -> {
                Toast.makeText(requireContext(),authRequestState.msg, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_main_nav_graph)
            }

            is AuthRequestState.Fail -> {
                Toast.makeText(requireContext(),authRequestState.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}