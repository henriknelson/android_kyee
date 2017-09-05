package nu.cliffords.android_kyee.views

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import nu.cliffords.android_kyee.R

/**
 * Created by henrik on 2017-09-03.
 */
class MainFragment: Fragment() {

    var actionBar: ActionBar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pagerAdapter = MainPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment("Lights",LightsFragment())
        pagerAdapter.addFragment("Flows",FlowsFragment())
        fragmentMainPager.adapter = pagerAdapter
        tabSlider.indicatorHeight = 2
        tabSlider.setAllCaps(true)
        tabSlider.shouldExpand = true
        tabSlider.setViewPager(fragmentMainPager)
    }

    class MainPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){

        val fragments = LinkedHashMap<String,Fragment>()

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
            return fragments.keys.elementAt(position)
        }
    }

}