package com.swp12.meogjapatfrontend.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.Meeting
import java.time.format.DateTimeFormatter

class MeetingAdapter(private val meetingList: ArrayList<Meeting>) : RecyclerView.Adapter<MeetingAdapter.ViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_meeting_item, parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.menu.text = meetingList[position].menu
        val date = DateTimeFormatter.ofPattern("MM월 dd일")
        holder.date.text = meetingList[position].time.toLocalDate().format(date)
        val time = DateTimeFormatter.ofPattern("HH시 mm분")
        holder.time.text = meetingList[position].time.toLocalTime().format(time)
        holder.age.text = meetingList[position].age.toString()
        holder.number.text = meetingList[position].number.toString()

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return meetingList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menu: TextView = view.findViewById(R.id.tv_meeting_menu)
        val date: TextView = view.findViewById(R.id.tv_meeting_date)
        val time: TextView = view.findViewById(R.id.tv_meeting_time)
        val age: TextView = view.findViewById(R.id.tv_meeting_age)
        val number: TextView = view.findViewById(R.id.tv_meeting_num)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}