package com.example.sos_ci.config

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhoneNumberAdapter(
    private val phoneNumbers: List<String>,
    private val onPhoneNumberClick: (String) -> Unit
) : RecyclerView.Adapter<PhoneNumberAdapter.PhoneNumberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneNumberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return PhoneNumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhoneNumberViewHolder, position: Int) {
        val phoneNumber = phoneNumbers[position]
        holder.bind(phoneNumber)
    }

    override fun getItemCount(): Int = phoneNumbers.size

    inner class PhoneNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneTextView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(phoneNumber: String) {
            phoneTextView.text = phoneNumber
            itemView.setOnClickListener { onPhoneNumberClick(phoneNumber) }
        }
    }
}
