package com.example.mobv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.mobv.databinding.FragmentPubFormBinding

class PubFormFragment : Fragment() {
    private var _binding: FragmentPubFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPubFormBinding.inflate(inflater, container, false)

        binding.confirmButton.setOnClickListener { onConfirm() }
        binding.listButton.setOnClickListener { onShowList() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onShowList() {
        val action = PubFormFragmentDirections.actionPubFormFragmentToPubListFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun onConfirm() {
        val action = PubFormFragmentDirections.actionPubFormFragmentToPubInfoFragment(
            ownerName = binding.ownerNameEditText.text.toString(),
            pubName = binding.pubNameEditText.text.toString(),
            latitude = binding.latitudeEditText.text.toString().toFloat(),
            longitude = binding.longitudeEditText.text.toString().toFloat()
        )
        view?.findNavController()?.navigate(action)
    }
}