package com.swp12.meogjapatfrontend.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.R
import com.swp12.meogjapatfrontend.api.AREA
import com.swp12.meogjapatfrontend.api.AgeGroup
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
        holder.area.text = AREA.getString(AREA.values()[meetingList[position].area])
        holder.age.text = AgeGroup.getString(AgeGroup.values()[meetingList[position].m_age])

        val amityText = if (meetingList[position].amity) "가능" else "불가"
        holder.amity.text = amityText

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return meetingList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // 메뉴, 친목, 지역, 연령대로 수정
        val menu: TextView = view.findViewById(R.id.tv_meeting_menu)
        val amity: TextView = view.findViewById(R.id.tv_meeting_amity)
        val area: TextView = view.findViewById(R.id.tv_meeting_area)
        val age: TextView = view.findViewById(R.id.tv_meeting_age)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}