package com.msid.livetabapay.view

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msid.livetabapay.R
import com.msid.livetabapay.adapter.BuildingDetailsAdapter
import com.msid.livetabapay.model.BuildingDetails
import com.msid.livetabapay.model.LogEntry
import com.msid.livetabapay.model.LogManager
import java.util.Date
import java.util.Locale

class EmployeeDetailsActivity : AppCompatActivity() {
    private lateinit var tvEmployeeNumber:TextView
    private lateinit var tvBuildingEntered:TextView
    private lateinit var tvRoomsEntered:TextView
    private var logEntries: List<LogEntry> = emptyList()
    private  lateinit var logManager: LogManager
    private lateinit var buildingDetailsAdapter: BuildingDetailsAdapter
    private lateinit var recyclerViewBuildingDetails: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        // Set up Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Employee Details"

        tvEmployeeNumber=findViewById(R.id.tvEmployeeNumber)
        tvBuildingEntered=findViewById(R.id.tvBuildingEntered)
        tvRoomsEntered=findViewById(R.id.tvRoomsEntered)
        recyclerViewBuildingDetails = findViewById(R.id.recyclerViewBuildingDetails)



        logManager = LogManager()

        val employeeNumber = intent.getStringExtra("employeeNumber") ?: return
        val allData = intent.getStringExtra("allData") ?: return
        //Log.d("ALLDATA",allData)
        val records = getEmployeeRecords(allData, employeeNumber)


        logManager.parseLogEntries(allData)
        showEmployeeLogs(employeeNumber)

        tvEmployeeNumber.text="Employee: "+employeeNumber

    }

    fun showEmployeeLogs(employeeId: String) {
        val employeeLogs = logManager.getEmployeeLogEntries(employeeId)
        val buildingSet = mutableSetOf<String>()
        val roomSet = mutableSetOf<String>()

        for (logEntry in employeeLogs) {
            buildingSet.add(logEntry.building)
            roomSet.add(logEntry.room)
        }

        val totalBuildingsEntered = buildingSet.size
        val totalRoomsEntered = roomSet.size
        tvBuildingEntered.text =  totalBuildingsEntered.toString()
        tvRoomsEntered.text = totalRoomsEntered.toString()


        // Calculate time spent and prepare building details list
        val buildingDetailsList = mutableListOf<BuildingDetails>()
        for (buildingName in buildingSet) {
            val timeSpent = calculateTimeSpent(employeeLogs.filter { it.building == buildingName })
            val roomsEntered = employeeLogs.filter { it.building == buildingName }.map { it.room }
            buildingDetailsList.add(BuildingDetails(buildingName, timeSpent, roomsEntered))
        }

        // Initialize and set up RecyclerView and adapter
        buildingDetailsAdapter = BuildingDetailsAdapter(buildingDetailsList)
        recyclerViewBuildingDetails.adapter = buildingDetailsAdapter
        recyclerViewBuildingDetails.layoutManager = LinearLayoutManager(this)
        calculateTimeSpent(employeeLogs)

    }


    private fun getEmployeeRecords(data: String, employeeNumber: String): List<String> {
        return data.split("\n").filter {
            it.contains(",${employeeNumber},")
        }
    }



    private fun calculateTimeSpent(logEntries: List<LogEntry>): String {
        var totalTime = 0L
        var lastInTime: Date? = null

        for (entry in logEntries) {
            if (entry.direction == "IN") {
                lastInTime = parseTimestamp(entry.timestamp)
            } else if (entry.direction == "OUT" && lastInTime != null) {
                val outTime = parseTimestamp(entry.timestamp)
                totalTime += outTime.time - lastInTime.time
                lastInTime = null
            }
        }

        return formatDuration(totalTime)
    }

    private fun parseTimestamp(timestamp: String): Date {
        val format = SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.US)
        return format.parse(timestamp) ?: Date()
    }

    private fun formatDuration(durationMillis: Long): String {
        val hours = durationMillis / (1000 * 60 * 60)
        val minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60)
        val seconds = (durationMillis % (1000 * 60)) / 1000
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }



}