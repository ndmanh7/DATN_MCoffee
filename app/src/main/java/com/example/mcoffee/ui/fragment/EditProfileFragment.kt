package com.example.mcoffee.ui.fragment

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mcoffee.data.model.user.Users
import com.example.mcoffee.data.remote.FireBaseState
import com.example.mcoffee.databinding.FragmentEditProfileBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.UserInformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val userInformationViewModel: UserInformationViewModel by activityViewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        userInformationViewModel.userInfo.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        lifecycleScope.launch {
            userInformationViewModel.updateProfileState.collect {
                handleState(it)
            }
        }
    }

    override fun bindView() {
        super.bindView()
        val user = userInformationViewModel.userInfo.value

        binding.btnUpdateProfile.setOnClickListener {
            var newUserInformation = hashMapOf<String, Any?>()
            user?.let {
                newUserInformation = hashMapOf<String, Any?>(
                    "/firstName" to binding.edtFirstName.text.toString(),
                    "/lastName" to binding.edtLastName.text.toString(),
                    "/phoneNumber" to binding.edtPhoneNumber.text.toString()
                )
            }
            userInformationViewModel.updateUserProfile(user, newUserInformation)
        }
    }

    private fun updateUI(user: Users?) {
        binding.apply {
            user?.let {
                tvEditUserEmail.text = it.email
                tvEditUsername.text = it.email
                edtFirstName.setText(it.firstName)
                edtLastName.setText(it.lastName)
                edtPhoneNumber.setText(it.phoneNumber)
            }
        }
    }

    private fun handleState(fireBaseState: FireBaseState<String>) {
        when(fireBaseState) {
            is FireBaseState.Success -> Toast.makeText(requireContext(), "Update success", Toast.LENGTH_SHORT).show()
            is FireBaseState.Fail -> Toast.makeText(requireContext(), "Update fail", Toast.LENGTH_SHORT).show()
        }
    }

}