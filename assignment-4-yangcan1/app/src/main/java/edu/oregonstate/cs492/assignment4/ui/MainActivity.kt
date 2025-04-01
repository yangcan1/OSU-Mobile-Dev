package edu.oregonstate.cs492.assignment4.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import edu.oregonstate.cs492.assignment4.R
import edu.oregonstate.cs492.assignment4.data.DataEntities

/*
 * Often, we'll have sensitive values associated with our code, like API keys, that we'll want to
 * keep out of our git repo, so random GitHub users with permission to view our repo can't see them.
 * The OpenWeather API key is like this.  We can keep our API key out of source control using the
 * technique described below.  Note that values configured in this way can still be seen in the
 * app bundle installed on the user's device, so this isn't a safe way to store values that need
 * to be kept secret at all costs.  This will only keep them off of GitHub.
 *
 * The Gradle scripts for this app are set up to read your API key from a special Gradle file
 * that lives *outside* your project directory.  This file called `gradle.properties`, and it
 * should live in your GRADLE_USER_HOME directory (this will usually be `$HOME/.gradle/` in
 * MacOS/Linux and `$USER_HOME/.gradle/` in Windows).  To store your API key in `gradle.properties`,
 * make sure that file exists in the correct location, and then add the following line:
 *
 *   OPENWEATHER_API_KEY="<put_your_own_OpenWeather_API_key_here>"
 *
 * If your API key is stored in that way, the Gradle build for this app will grab it and write it
 * into the string resources for the app with the resource name "openweather_api_key".  You'll be
 * able to access your key in the app's Kotlin code the same way you'd access any other string
 * resource, e.g. `getString(R.string.openweather_api_key)`.  This is what's done in the code below
 * when the OpenWeather API key is needed.
 *
 * If you don't mind putting your OpenWeather API key on GitHub, then feel free to just hard-code
 * it in the app. 🤷‍
 */

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfig: AppBarConfiguration
    private val ViewModelSavedCity: SavedCityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_nav_drawer)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)

        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)

        ViewModelSavedCity.savedCities.observe(this) {
            addEntriesToNavDrawer(it)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    private fun addEntriesToNavDrawer(dataEntities: List<DataEntities>) {
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val subMenu = navView.menu.findItem(R.id.programmatically_inserted).subMenu
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        subMenu?.clear()
        for (entry in dataEntities) {
            subMenu?.add(entry.cityName)?.setOnMenuItemClickListener {
                val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                prefs.edit().putString("city", entry.cityName).apply()

                findNavController(R.id.nav_host_fragment).navigate(R.id.current_weather)
                drawerLayout.closeDrawers()
                true

            }
        }
    }
}