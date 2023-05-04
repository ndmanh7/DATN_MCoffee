package com.example.mcoffee.ui.fragment.user

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.R
import com.example.mcoffee.data.remote.user.AuthRequestState
import com.example.mcoffee.databinding.FragmentLoginBinding
import com.example.mcoffee.safeNavigate
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.LoginViewModel
import com.example.mcoffee.ui.viewmodel.UserInformationViewModel
import com.example.mcoffee.utils.LoginConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by activityViewModels()
    private val userInformationViewModel: UserInformationViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            viewModel.loginState.collect {
                handleAuthRequestState(it)
            }
        }
    }


    override fun bindView() {
        super.bindView()
        Log.d("manh", "bindView at line 38: ${LoginConfig.isLogin(requireContext(), "logged_in")}")

//        if (LoginConfig.isLogin(requireContext(), "logged_in")) {
//            userInformationViewModel.userInfo.observe(viewLifecycleOwner) {
//                if (it.isAdmin) {
//                    findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToAdminActivity())
//                } else {
//                    findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
//                }
//            }
//
//        }

        if (LoginConfig.isLogin(requireContext(), "logged_in_as_admin")) {
            findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToAdminActivity())
        } else if (LoginConfig.isLogin(requireContext(), "logged_in_as_user")) {
            findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        }

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
                userInformationViewModel.getUserInfo()
                Toast.makeText(requireContext(),authRequestState.msg, Toast.LENGTH_SHORT).show()
                userInformationViewModel.userInfo.observe(viewLifecycleOwner) {
                    if (it.isAdmin) {
                        LoginConfig.loginState(requireContext(), "logged_in_as_admin")
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAdminActivity())
                    } else {
                        LoginConfig.loginState(requireContext(), "logged_in_as_user")
                        findNavController().safeNavigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                    }
                }
                viewModel.clearLoginState()
            }

            is AuthRequestState.Fail -> {
                if (authRequestState.msg.isNotEmpty()) {
                    Toast.makeText(requireContext(),authRequestState.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}