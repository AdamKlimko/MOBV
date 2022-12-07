package com.example.zadanie.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentBarsBinding
import com.example.zadanie.databinding.FragmentFriendsBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.BarsViewModel
import com.example.zadanie.ui.viewmodels.FriendsViewModel


class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    private lateinit var viewmodel: FriendsViewModel

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_locate)
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
//                viewmodel.show("Only approximate location access granted.")
                // Only approximate location access granted.
            }
            else -> {
//                viewmodel.show("Location access denied.")
                // No location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this,
            Injection.provideViewModelFactory(requireContext())
        )[FriendsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)
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
//            model = viewmodel
        }.also { bnd ->
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

            bnd.swiperefresh.setOnRefreshListener {
                viewmodel.refreshData()
            }

            bnd.bottomNavigation.selectedItemId = R.id.page_friends
            bnd.bottomNavigation.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.page_bars -> {
                        this.findNavController().navigate(R.id.action_to_bars)
                        true
                    }
                    R.id.page_locate -> {
                        if (checkPermissions()) {
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

        viewmodel.loading.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = it
        }

        viewmodel.message.observe(viewLifecycleOwner) {
            if (PreferenceData.getInstance().getUserItem(requireContext()) == null) {
                Navigation.findNavController(requireView()).navigate(R.id.action_to_login)
            }
        }
    }

    // TODO to helper func
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}