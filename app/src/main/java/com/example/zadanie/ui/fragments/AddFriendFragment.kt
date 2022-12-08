package com.example.zadanie.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentAddFriendBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.Permissions
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.FriendsViewModel


class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var viewmodel: FriendsViewModel

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
        )[FriendsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener { it.findNavController().popBackStack() }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.log_out -> {
                    PreferenceData.getInstance().clearData(requireContext())
                    Navigation.findNavController(view).navigate(R.id.action_to_login)
                    true
                }
                else -> false
            }
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewmodel
        }

        binding.addFriendButton.setOnClickListener {
            if (binding.friendName.text.toString().isNotBlank()) {
                hideKeyboardFrom(requireContext(), binding.addFriendButton)
                viewmodel.addFriend(binding.friendName.text.toString())
                binding.friendName.text.clear()
            }else {
                viewmodel.show("Please fill in friend's name")
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.page_friends
        binding.bottomNavigation.setOnItemSelectedListener {
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

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}