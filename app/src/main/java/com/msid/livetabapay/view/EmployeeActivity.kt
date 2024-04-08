package com.msid.livetabapay.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.msid.livetabapay.R
import com.msid.livetabapay.adapter.LogEntryAdapter
import java.util.regex.Pattern

class EmployeeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LogEntryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        // Set up Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "List of Employees"

        // Initialize RecyclerView and Adapter
        recyclerView = findViewById(R.id.recyclerViewEmployee)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Receive data from MainActivity
        val data = intent.getStringExtra("lin2")?:""
        val uniqueEmployeeNumbers = getUniqueEmployeeNumbers(data)
        val numberList = mutableListOf<String>()

        data?.let {
            val pattern = Pattern.compile("\\d{4}")
            val matcher = pattern.matcher(data)

            while (matcher.find()) {
                numberList.add(matcher.group())
            }
        }


        adapter = LogEntryAdapter(uniqueEmployeeNumbers,data)
        recyclerView.adapter = adapter
    }

    private fun getUniqueEmployeeNumbers(data: String): List<String> {
        val pattern = Regex("\\d{4}")
        val foundNumbers = mutableSetOf<String>()
        pattern.findAll(data).forEach { matchResult ->
            foundNumbers.add(matchResult.value)
        }
        return foundNumbers.toList() // Convert set to list to maintain uniqueness
    }


}