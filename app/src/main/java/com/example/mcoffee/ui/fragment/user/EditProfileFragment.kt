package com.example.mcoffee.ui.fragment.user

import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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
    private var imagePicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgEditUserImage.setImageURI(uri)
            userInformationViewModel.getImage(uri)
        }
    }
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

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }

        Glide.with(requireContext())
            .load(user!!.image)
            .into(binding.imgEditUserImage)

        binding.btnUpdateProfile.setOnClickListener {
            if (userInformationViewModel.userImageUri.value != null) {
                user!!.image = userInformationViewModel.userImageUri.value.toString()
            }
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

        binding.imgEditUserImage.setOnClickListener {
            imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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