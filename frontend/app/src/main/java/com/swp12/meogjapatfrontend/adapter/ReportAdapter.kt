package com.swp12.meogjapatfrontend.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swp12.meogjapatfrontend.R

class ReportAdapter(private val reportList: ArrayList<Report>) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.payment_report_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nickname.text = reportList[position].nickname
        holder.rude.isChecked = reportList[position].rude
        holder.run.isChecked = reportList[position].run

        holder.rude.setOnClickListener { reportList[position].rude = holder.rude.isChecked }
        holder.run.setOnClickListener { reportList[position].run = holder.run.isChecked }
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nickname: TextView = view.findViewById(R.id.report_tv)
        val run: CheckBox = view.findViewById(R.id.report_run)
        val rude: CheckBox = view.findViewById(R.id.report_rude)
    }
}

data class Report(
    var nickname: String = "",
    var rude: Boolean = false,
    var run: Boolean = false
)