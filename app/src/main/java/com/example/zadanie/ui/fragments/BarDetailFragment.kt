package com.example.zadanie.ui.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentDetailBarBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.Permissions
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.DetailViewModel

class BarDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBarBinding
    private lateinit var viewmodel: DetailViewModel

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_locate)
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                viewmodel.show("Only approximate location access granted.")
            }
            else -> {
                viewmodel.show("Location access denied.")
            }
        }
    }

    private val navigationArgs: BarDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_login)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewmodel
        }.also { bnd ->
            bnd.back.setOnClickListener { it.findNavController().popBackStack() }

            bnd.topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.log_out -> {
                        PreferenceData.getInstance().clearData(requireContext())
                        Navigation.findNavController(view).navigate(R.id.action_to_login)
                        true
                    }
                    else -> false
                }
            }

            bnd.mapButton.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "geo:0,0,?q=" +
                                    "${viewmodel.bar.value?.lat ?: 0}," +
                                    "${viewmodel.bar.value?.lon ?: 0}" +
                                    "(${viewmodel.bar.value?.name ?: ""}"
                        )
                    )
                )
            }

            bnd.bottomNavigation.selectedItemId = R.id.page_bars
            bnd.bottomNavigation.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.page_bars -> {
                        this.findNavController().navigate(R.id.action_to_bars)
                        true
                    }
                    R.id.page_friends -> {
                        this.findNavController().navigate(R.id.action_to_friends)
                        true
                    }
                    R.id.page_locate -> {
                        if (Permissions.checkPermissions(requireContext())) {
                            this.findNavController().navigate(R.id.action_to_locate)
                        } else {
                            locationPermissionRequest.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                        true
                    }
                    else -> false
                }
            }
        }

        viewmodel.loadBar(navigationArgs.id)
    }
}