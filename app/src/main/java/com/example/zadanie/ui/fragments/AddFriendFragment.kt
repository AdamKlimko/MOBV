package com.example.zadanie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentAddFriendBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.FriendsViewModel


class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var viewmodel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel = ViewModelProvider(this,
            Injection.provideViewModelFactory(requireContext())
        )[FriendsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewmodel
        }

        binding.addFriendButton.setOnClickListener {
            if (binding.friendName.text.toString().isNotBlank()) {
                viewmodel.addFriend(binding.friendName.text.toString())
                binding.friendName.text.clear()
            }else {
                viewmodel.show("Please fill in friend's name")
            }
        }
    }
}