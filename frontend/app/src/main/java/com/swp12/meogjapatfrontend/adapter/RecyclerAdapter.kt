package com.swp12.meogjapatfrontend.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.Fragment_navi.Meeting
import com.swp12.meogjapatfrontend.R
/*
class RecyclerAdapter : RecyclerView.Adapter {
    private lateinit var context: Context
    private var items = ArrayList()
    private var item_layout = 0


    constructor(context: Context, items: ArrayList, item_layout: Int) : super() {
        this.context = context
        this.items = items
        this.item_layout = item_layout
    }

    override fun onCreateViewHolder(parent: ViewGroup, item_layout: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.home_meeting_item, null)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val drawable = ContextCompat.getDrawable(context, item.getImage())
        holder.image!!.background = drawable
        holder.title!!.text = item.getTitle()
    }

    class ViewHolder : RecyclerView.ViewHolder {

        var menu: TextView? = null
        var date: TextView? = null
        var time: TextView? = null
        var age: TextView? = null
        var num: TextView? = null

        constructor(itemView: View) : super(itemView) {
            menu = itemView.findViewById(R.id.tv_meeting_menu) as TextView
            date = itemView.findViewById(R.id.tv_meeting_date) as TextView
            time = itemView.findViewById(R.id.tv_meeting_time) as TextView
            age = itemView.findViewById(R.id.tv_meeting_age) as TextView
            num = itemView.findViewById(R.id.tv_meeting_num) as TextView
        }
    }
}

*/