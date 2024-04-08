package com.msid.livetabapay.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msid.livetabapay.R
import com.msid.livetabapay.view.EmployeeDetailsActivity

class LogEntryAdapter(private val employeeNumbers: List<String>, private val data: String) :
    RecyclerView.Adapter<LogEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmployeeNumber: TextView = view.findViewById(R.id.tvEmployeeNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employeeNumber = employeeNumbers[position]
        holder.tvEmployeeNumber.text = "Employee: $employeeNumber"

        holder.itemView.setOnClickListener {
            // Start a new Activity and pass the employee number
            val context = holder.itemView.context
            val intent = Intent(context, EmployeeDetailsActivity::class.java)
            intent.putExtra("employeeNumber", employeeNumber)
            intent.putExtra("allData", data) // Pass all data to filter in the next activity
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = employeeNumbers.size
}
