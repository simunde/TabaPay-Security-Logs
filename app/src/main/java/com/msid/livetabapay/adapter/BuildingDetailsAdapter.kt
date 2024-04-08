package com.msid.livetabapay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msid.livetabapay.R
import com.msid.livetabapay.model.BuildingDetails

class BuildingDetailsAdapter (private val buildingDetailsList: List<BuildingDetails>) :
    RecyclerView.Adapter<BuildingDetailsAdapter.BuildingDetailsViewHolder>() {

    inner class BuildingDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBuildingName: TextView = itemView.findViewById(R.id.tvBuildingName)
        val tvTimeSpent: TextView = itemView.findViewById(R.id.tvTimeSpent)
        val tvTimeValue: TextView = itemView.findViewById(R.id.tvTimeValue)
        val tvRooms: TextView = itemView.findViewById(R.id.tvRooms)
        val layoutRoomsEntered: ViewGroup = itemView.findViewById(R.id.layoutRoomsEntered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildingDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_building_details, parent, false)
        return BuildingDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BuildingDetailsViewHolder, position: Int) {
        val currentItem = buildingDetailsList[position]

        holder.tvBuildingName.text = currentItem.buildingName
        holder.tvTimeValue.text = currentItem.timeSpent
        holder.layoutRoomsEntered.removeAllViews() // Clear any previous views



// Maintain a set to store unique room numbers for each building
        val uniqueRoomNumbers = mutableSetOf<String>()

// Iterate over room entries for the specific building
        for (room in currentItem.roomsEntered) {
            // Add room number to the set if it's not already present
            if (room !in uniqueRoomNumbers) {
                uniqueRoomNumbers.add(room)

                // Create a TextView to display the unique room number
                val roomTextView = TextView(holder.itemView.context)
                roomTextView.text = "    Room no${room}" // Display exact room number
                holder.layoutRoomsEntered.addView(roomTextView)
            }
        }
    }

    override fun getItemCount(): Int {
        return buildingDetailsList.size
    }
}
