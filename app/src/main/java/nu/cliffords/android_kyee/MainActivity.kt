package nu.cliffords.android_kyee

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import nu.cliffords.android_kyee.Fragments.AboutFragment
import nu.cliffords.android_kyee.Fragments.LightsFragment
import nu.cliffords.kyee.classes.LightManager

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.frame_container,LightsFragment()).commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val count = supportFragmentManager.backStackEntryCount

            if (count > 0) {
                supportFragmentManager.popBackStackImmediate()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container,LightsFragment()).commit()
            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container,AboutFragment()).addToBackStack("about_fragment").commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
