package com.example.sos_ci.config

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sos_ci.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class EmergencyAdapter(
    private var emergencies: List<Emergency>,
    private val onPhoneNumberClick: (String) -> Unit
) : RecyclerView.Adapter<EmergencyAdapter.EmergencyViewHolder>() {

    var originalList: List<Emergency> = emergencies.toList()
    private var filteredList: List<Emergency> = emergencies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return EmergencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun updateList(newList: List<Emergency>) {
        filteredList = newList
        notifyDataSetChanged()
    }

    fun resetList() {
        filteredList = originalList
        notifyDataSetChanged()
    }

    inner class EmergencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleNumber: TextView = itemView.findViewById(R.id.titleNumber)
        private val phoneNumber: TextView = itemView.findViewById(R.id.phoneNumber)
        private val phoneIcon: ImageButton = itemView.findViewById(R.id.phoneIcon)

        fun bind(emergency: Emergency) {
            titleNumber.text = emergency.title
            phoneNumber.text = emergency.phoneNumbers.joinToString(", ")

            phoneIcon.setOnClickListener {
                if (emergency.phoneNumbers.size == 1) {
                    onPhoneNumberClick(emergency.phoneNumbers[0])
                } else {
                    showPhoneNumberDialog(emergency.phoneNumbers)
                }
            }
        }

        private fun showPhoneNumberDialog(phoneNumbers: List<String>) {
            val dialog = BottomSheetDialog(itemView.context)
            val view = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_phone_numbers, null)

            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewPhoneNumbers)
            recyclerView.adapter = PhoneNumberAdapter(phoneNumbers) { selectedPhone ->
                onPhoneNumberClick(selectedPhone)
                dialog.dismiss()
            }
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)

            dialog.setContentView(view)
            dialog.show()
        }
    }
}
