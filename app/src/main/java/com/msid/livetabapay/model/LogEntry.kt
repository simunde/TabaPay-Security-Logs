package com.msid.livetabapay.model

//data class LogEntry(
//    val direction: String,
//    val dateTime: String,
//    val employeeNumber: String,
//    val buildingName: String,
//    val roomNumber: String
//)

data class LogEntry(
    val direction: String,
    val timestamp: String,
    val employeeId: String,
    val building: String,
    val room: String
)

data class EmployeeLog(
    val employeeNumber: String,
    val logs: List<LogEntry>
)
