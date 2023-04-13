package com.example.mcoffee.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
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
//        val navController = binding.mainFragmentContainer.getFragment<NavHostFragment>().navController

        //bottom navigation item click
        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { item ->
                val reSelectedDestinationId = item.itemId
                navController.popBackStack(reSelectedDestinationId, inclusive = false)
            }
        }
    }


}