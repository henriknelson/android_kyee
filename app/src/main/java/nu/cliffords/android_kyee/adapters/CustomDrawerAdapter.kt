package nu.cliffords.android_kyee.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import nu.cliffords.android_kyee.R
import nu.cliffords.android_kyee.classes.DrawerItem


/**
 * Created by Henrik Nelson on 2017-09-10.
 */

class CustomDrawerAdapter(private val drawerMenuList: ArrayList<DrawerItem>) : RecyclerView.Adapter<CustomDrawerAdapter.ViewHolder>() {

    val TYPE_HEADER = 0
    val TYPE_MENU = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        if(viewType == TYPE_HEADER)
            view = LayoutInflater.from(parent.context).inflate(R.layout.nav_drawer_header, parent, false)
        else
            view = LayoutInflater.from(parent.context).inflate(R.layout.nav_drawer_menu_item, parent, false)
        return ViewHolder(view,viewType)
    }

    override fun onBindViewHolder(holder: CustomDrawerAdapter.ViewHolder, position: Int) {
        if(position==0)
        {
            holder.headerTitle?.setText("kYee")
        }else {
            holder.title?.setText(drawerMenuList[position-1].title)
            holder.icon?.setImageResource(drawerMenuList[position-1].icon)
        }
    }

    override fun getItemCount(): Int {
        return drawerMenuList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0) return TYPE_HEADER else return TYPE_MENU
    }

    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var headerTitle: TextView? = null
        var title: TextView? = null
        var icon: ImageView? = null

        init {
            if(viewType == 0)
            {
                headerTitle = itemView.findViewById(R.id.headerText)
            }else {
                title = itemView.findViewById(R.id.title)
                icon = itemView.findViewById<ImageView>(R.id.icon)
            }
        }
    }
}