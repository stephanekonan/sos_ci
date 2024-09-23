package com.example.sos_ci.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sos_ci.R
import com.example.sos_ci.config.Emergency
import com.example.sos_ci.config.EmergencyAdapter
import com.example.sos_ci.databinding.FragmentNumeroUrgenceBinding
import com.google.firebase.firestore.FirebaseFirestore

class NumeroVertFragment : Fragment() {

    private lateinit var adapter: EmergencyAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_numero_urgence, container, false)
        val searchInput: EditText = view.findViewById(R.id.searchInput)
        val recyclerView: RecyclerView = view.findViewById(R.id.repairsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchEmergencies { emergencies ->
            adapter = EmergencyAdapter(emergencies) { phoneNumber ->
                makePhoneCall(phoneNumber)
            }
            recyclerView.adapter = adapter
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun fetchEmergencies(onDataFetched: (List<Emergency>) -> Unit) {
        db.collection("emergencies")
            .whereEqualTo("category", "vert")
            .get()
            .addOnSuccessListener { result ->
                val emergencyList = result.map { document ->
                    document.toObject(Emergency::class.java)
                }
                onDataFetched(emergencyList)
            }
            .addOnFailureListener { exception ->
                Log.e("NumeroUrgenceFragment", "Erreur lors de la récupération des données", exception)
            }
    }

    private fun makePhoneCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filter(text: String) {
        val filteredList = if (text.isEmpty()) {
            adapter.resetList()
            return
        } else {
            adapter.originalList.filter { emergency ->
                emergency.title.contains(text, true) ||
                        emergency.phoneNumbers.any { phone -> phone.contains(text) }
            }
        }

        adapter.updateList(filteredList)

        view?.findViewById<LinearLayout>(R.id.noFoundLayoud)?.visibility =
            if (filteredList.isEmpty()) View.VISIBLE else View.GONE
    }

}