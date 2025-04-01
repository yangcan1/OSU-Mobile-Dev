package com.example.foodrecipe.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodrecipe.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as NavHostFragment

        val navController = navHostFragment.navController
        appBarConfig = AppBarConfiguration(navController.graph)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)



    }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "Running onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "Running onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "Running onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "Running onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "Running onDestroy()")
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}
