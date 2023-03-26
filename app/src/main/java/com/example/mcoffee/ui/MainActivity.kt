package com.example.mcoffee.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mcoffee.R
import com.example.mcoffee.databinding.ActivityLoginBinding
import com.example.mcoffee.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottom navigation item click
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navigateToFragment(R.id.homeFragment)
                    true
                }

                R.id.cartFragment -> {
                    navigateToFragment(R.id.cartFragment)
                    true
                }

                R.id.profileFragment -> {
                    navigateToFragment(R.id.profileFragment)
                    true
                }
                else -> {
                    navigateToFragment(R.id.homeFragment)
                    true
                }
            }
        }
    }

    private fun navigateToFragment(fragmentId: Int) {
        Navigation.findNavController(this, R.id.main_fragment_container).navigate(fragmentId)
    }

}