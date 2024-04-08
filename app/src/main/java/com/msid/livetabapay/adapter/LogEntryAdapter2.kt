package com.msid.livetabapay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msid.livetabapay.R
import com.msid.livetabapay.model.EmployeeLog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogEntryAdapter2 (private val employeeLogs: List<EmployeeLog>) : RecyclerView.Adapter<LogEntryAdapter2.ViewHolder>() {

    // ViewHolder class to hold your layout
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmployeeNumber: TextView = view.findViewById(R.id.tvEmployeeNumber)
        val tvTotalBuildingsEntered: TextView = view.findViewById(R.id.tvBuildingEntered)

        val tvTotalRoomsEntered:TextView =view.findViewById(R.id.tvRoomsEntered)
        val tvBuildingName:TextView = view.findViewById(R.id.tvBuilding)
        val tvTimeSpent:TextView = view.findViewById(R.id.tvTimeSpent)
        val tvRoomsEntered:TextView = view.findViewById(R.id.tvNumberofRooms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.log_entry_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employeeLog = employeeLogs[position]
        holder.tvEmployeeNumber.text = "Employee: ${employeeLog.employeeNumber}"
        // A map to store the total time spent in each building by the employee.
        val buildingTimeMap = mutableMapOf<String, Long>()
        // A map to store unique rooms entered per building.
        val buildingRoomsMap = mutableMapOf<String, MutableSet<String>>()

        employeeLog.logs.forEach { log ->
            val time = log.timestamp // Assuming this is in a parseable format

            // Calculate time spent in each building if direction is OUT.
            if (log.direction == "OUT") {
                val inTime = employeeLog.logs.firstOrNull {
                    it.direction == "IN" && it.building == log.building && it.room == log.room
                }?.timestamp
                // Parse the inTime and time, calculate the difference, and add it to the buildingTimeMap.
                // For example, using a SimpleDateFormat to parse the dates and get the milliseconds difference.
                // This is a placeholder, you'll need to implement the actual date parsing and difference calculation.
                val timeSpent = time.toMilliseconds() - (inTime?.toMilliseconds() ?: 0)
                buildingTimeMap[log.building] = buildingTimeMap.getOrDefault(log.building, 0) + timeSpent
            }

            // Populate the set of rooms for each building.
            buildingRoomsMap.getOrPut(log.building) { mutableSetOf() }.add(log.room)
        }

        // Set the total buildings and rooms entered.
        holder.tvTotalBuildingsEntered.text = "${buildingTimeMap.size}"
        holder.tvTotalRoomsEntered.text = "${buildingRoomsMap.values.flatten().distinct().size}"

        // Assuming you have TextViews for each building in your ViewHolder
        // Here we will just populate the first building's data as an example.
        val firstBuilding = buildingTimeMap.keys.firstOrNull()
        firstBuilding?.let {
            holder.tvBuildingName.text = it
            holder.tvTimeSpent.text = formatTime(buildingTimeMap[it])
            holder.tvRoomsEntered.text = buildingRoomsMap[it]?.joinToString(", ") ?: ""
        }
    }

    // Utility function to format the time from milliseconds to HH:mm:ss format.
    private fun formatTime(timeInMillis: Long?): String {
        // Placeholder function, you would need to convert the time in milliseconds to the desired format.
        // For example, use SimpleDateFormat or another method to format it.
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(timeInMillis ?: 0))
    }

    // Utility function to convert date time string to milliseconds.
// You need to implement this based on the actual date time format.
    private fun String.toMilliseconds(): Long {
        // Placeholder function, parse the string to Date and return time in milliseconds.
        val format = SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.getDefault())
        return format.parse(this)?.time ?: 0
    }

    override fun getItemCount() = employeeLogs.size
}