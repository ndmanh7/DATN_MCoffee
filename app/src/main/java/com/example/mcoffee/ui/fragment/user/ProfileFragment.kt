package com.example.mcoffee.ui.fragment.user

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.R
import com.example.mcoffee.data.model.user.Users
import com.example.mcoffee.databinding.FragmentProfileBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.LoginViewModel
import com.example.mcoffee.ui.viewmodel.UserInformationViewModel
import com.example.mcoffee.utils.LoginConfig
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val userInformationViewModel: UserInformationViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        userInformationViewModel.userInfo.observe(viewLifecycleOwner) {
            Log.d("manh", "observeViewModel at line 21: $it")
            updateUI(it)
        }
    }

    private fun updateUI(user: Users) {
        binding.apply {
            tvUsername.text = user.email
            tvEmail.text = user.email
        }
    }

    override fun bindView() {
        super.bindView()
        binding.viewMyAccount.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.viewMyOder.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_orderHistoryFragment)
        }

        binding.viewLogOut.setOnClickListener {
            LoginConfig.loginState(requireContext(), "logged_out")
            loginViewModel.logout()
            requireActivity().finish()
        }
    }

}