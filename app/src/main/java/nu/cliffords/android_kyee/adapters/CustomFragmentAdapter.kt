package nu.cliffords.android_kyee.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Henrik Nelson on 2017-09-08.
 */

class CustomFragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

    val fragments = LinkedHashMap<String, Fragment>()

    fun addFragment(title: String,fragment: Fragment) {
        fragments.put(title,fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments.values.elementAt(position)
    }

    override fun getCount(): Int {
        return fragments.values.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments.keys.elementAt(position).toUpperCase()
    }
}