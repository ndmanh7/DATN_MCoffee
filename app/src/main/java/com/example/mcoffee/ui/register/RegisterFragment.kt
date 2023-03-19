package com.example.mcoffee.ui.register

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mcoffee.data.model.Users
import com.example.mcoffee.data.remote.AuthRequestState
import com.example.mcoffee.databinding.FragmentRegisterBinding
import com.example.mcoffee.ui.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            viewModel.registerState.collectLatest {
                handleRegisterState(it)
            }
        }
    }

    override fun bindView() {
        super.bindView()
        binding.apply {
            btnRegister.setOnClickListener {
                viewModel.register(
                    edtLoginEmail.text.toString(),
                    edtLoginPassword.text.toString()
                )
            }
        }
    }

    private fun handleRegisterState(registerState: AuthRequestState) {
        when(registerState) {
            is AuthRequestState.Success -> {
                Toast.makeText(requireContext(),registerState.msg, Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            is AuthRequestState.Fail -> {
                Toast.makeText(requireContext(),registerState.msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

}