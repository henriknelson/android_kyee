package nu.cliffords.android_kyee.views

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astuetz.PagerSlidingTabStrip
import kotlinx.android.synthetic.main.fragment_main.*
import nu.cliffords.android_kyee.R
import android.widget.TextView
import org.jetbrains.anko.childrenSequence

/**
 * Created by Henrik Nelson on 2017-09-03.
 */

class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pagerAdapter = getPagerAdapter()
        fragmentMainPager.adapter = pagerAdapter
        val tabSlider = getTabSlider()
        tabSlider.setViewPager(fragmentMainPager)
        setupTabSlider(tabSlider)
        tabSlider.shouldExpand = true
        tabSlider.setAllCaps(true)
        tabSlider.indicatorHeight = 10
    }

    fun getPagerAdapter() : MainPagerAdapter {
        val pagerAdapter = MainPagerAdapter(childFragmentManager)
        pagerAdapter.addFragment("Lights",LightsFragment())
        pagerAdapter.addFragment("Flows",FlowsFragment())
        return pagerAdapter
    }

    fun getTabSlider(): PagerSlidingTabStrip {
        return (activity as AppCompatActivity).findViewById<PagerSlidingTabStrip>(R.id.tabSlider)
    }

    fun setupTabSlider(tabSlider: PagerSlidingTabStrip) {
        val accentColor =  getResources().getColor(R.color.colorAccent,null);
        tabSlider.indicatorColor = accentColor

        val childAt = tabSlider.getChildAt(0) as ViewGroup;
        (childAt.getChildAt(0) as TextView).setTextColor(getResources().getColor(R.color.colorAccent));
        tabSlider.setOnPageChangeListener(object:ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                var i = 0
                val size = childAt.getChildCount()
                while (i < size) {
                    (childAt.getChildAt(i) as TextView).setTextColor(if (i == position) resources.getColor(R.color.colorAccent) else resources.getColor(R.color.colorDefault))
                    i++
                }
            }
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }

        });
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