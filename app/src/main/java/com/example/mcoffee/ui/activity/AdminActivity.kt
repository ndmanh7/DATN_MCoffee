package com.example.mcoffee.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mcoffee.R
import com.example.mcoffee.databinding.ActivityAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.admin_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.adminHomeFragment -> showBottomNav()
                R.id.adminCategoryManageFragment -> showBottomNav()
                R.id.adminOrderManageFragment -> showBottomNav()
                else -> hideBottomNav()
            }

        }

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnItemReselectedListener { item ->
                val reSelectedDestinationId = item.itemId
                navController.popBackStack(reSelectedDestinationId, inclusive = false)
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }
}