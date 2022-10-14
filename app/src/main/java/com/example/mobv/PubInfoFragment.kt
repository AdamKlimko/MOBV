package com.example.mobv

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobv.databinding.FragmentPubInfoBinding

class PubInfoFragment : Fragment() {
    private var _binding: FragmentPubInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var ownerName: String
    private lateinit var pubName: String
    private var latitude: Float = 0f
    private var longitude: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            ownerName = it.getString("ownerName").toString()
            pubName = it.getString("pubName").toString()
            latitude = it.getFloat("latitude")
            longitude = it.getFloat("longitude")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPubInfoBinding.inflate(inflater, container, false)

        binding.ownerNameTextView.text = ownerName
        binding.pubNameTextView.text = pubName

        binding.showOnMapBtn.setOnClickListener{ onShowMapBtnClick() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onShowMapBtnClick() {
        val gmmIntentUri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}