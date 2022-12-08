package com.example.zadanie.ui.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentBarsBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.Permissions
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.helpers.SortMethod
import com.example.zadanie.ui.viewmodels.BarsViewModel

class BarsFragment : Fragment() {
    private lateinit var binding: FragmentBarsBinding
    private lateinit var viewmodel: BarsViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this,
            Injection.provideViewModelFactory(requireContext())
        )[BarsViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarsBinding.inflate(inflater, container, false)
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

            bnd.bottomNavigation.selectedItemId = R.id.page_bars
            bnd.bottomNavigation.setOnItemSelectedListener {
                when(it.itemId) {
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

            bnd.sortButton.setOnClickListener { showSortMenu(it, R.menu.sort_menu) }

            bnd.sortDirection.isChecked = false
            bnd.sortDirection.setOnCheckedChangeListener { buttonView, isChecked ->
                viewmodel.setSortingDirection(isChecked)
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

    private fun showSortMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId) {
                R.id.menuName -> {
                    viewmodel.setSortingMethod(SortMethod.NAME)
                    return@setOnMenuItemClickListener true
                }
                R.id.menuDistance -> {
                    viewmodel.setSortingMethod(SortMethod.DISTANCE)
                    return@setOnMenuItemClickListener true
                }
                R.id.menuVisitors -> {
                    viewmodel.setSortingMethod(SortMethod.VISITORS)
                    return@setOnMenuItemClickListener true
                }
                else -> { return@setOnMenuItemClickListener false }
            }
        }
        popup.show()
    }
}