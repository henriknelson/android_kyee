package nu.cliffords.android_kyee

import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import nu.cliffords.android_kyee.views.AboutFragment
import nu.cliffords.android_kyee.views.FlowsFragment
import nu.cliffords.android_kyee.views.LightsFragment
import nu.cliffords.android_kyee.views.MainFragment




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        setupDrawer()
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, MainFragment()).commit()
    }

    fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val myTypeface = Typeface.createFromAsset(assets, "fonts/raleway_light.ttf")
        toolbar_title.setTypeface(myTypeface)
    }

    fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Make sure the correct nav menu item is selected at all times
        nav_view.menu.getItem(0).isChecked = true
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) {
                nav_view.menu.getItem(0).isChecked = true
            }
        }
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
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, LightsFragment()).commit()
            }
            R.id.nav_flows -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container, FlowsFragment()).addToBackStack("flows_fragment").commit()
            }
            R.id.nav_about -> {
                supportFragmentManager.beginTransaction().replace(R.id.frame_container,AboutFragment()).addToBackStack("about_fragment").commit()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
