package com.example.mcoffee.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        //bottom navigation item click
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.homeFragment -> {
//                    navigateToFragment(R.id.homeFragment)
//                    true
//                }
//
//                R.id.cartFragment -> {
//                    navigateToFragment(R.id.cartFragment)
//                    true
//                }
//
//                R.id.profileFragment -> {
//                    navigateToFragment(R.id.profileFragment)
//                    true
//                }
//                else -> {
//                    navigateToFragment(R.id.homeFragment)
//                    true
//                }
//            }
            menuItem.onNavDestinationSelected(navController)
        }
    }

}