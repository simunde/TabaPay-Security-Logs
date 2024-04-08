package com.msid.livetabapay.model

class LogManager {

    // This list will hold all the log entries after parsing.
    private var logEntries: List<LogEntry> = emptyList()

    // Parses the API response and stores it in the logEntries list.
    fun parseLogEntries(response: String) {
        logEntries = response.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(",")
                if (parts.size == 5) {
                    LogEntry(
                        direction = parts[0].trim(),
                        timestamp = parts[1].trim(),
                        employeeId = parts[2].trim(),
                        building = parts[3].trim(),
                        room = parts[4].trim()
                    )
                } else {
                    null // For lines that do not conform to the expected format
                }
            }
            .filterNotNull() // Remove nulls that were created from malformed lines
            .toList()
    }

    // Gets a specific employee's log entries.
    fun getEmployeeLogEntries(employeeId: String): List<LogEntry> {
        return logEntries.filter { it.employeeId == employeeId }
    }
}