package com.example.mcoffee.ui.fragment

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

        binding.viewLogOut.setOnClickListener {
            Log.d("manh", "bindView at line 42: ")
            LoginConfig.loginState(requireContext(), "logged_out")
            findNavController().popBackStack(R.id.login_nav_graph, false)
        }
    }

}