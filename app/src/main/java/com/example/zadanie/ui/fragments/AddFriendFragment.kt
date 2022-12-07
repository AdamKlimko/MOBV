package com.example.zadanie.ui.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zadanie.databinding.FragmentAddFriendBinding
import com.example.zadanie.helpers.Injection
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
    ): View {
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
                hideKeyboardFrom(requireContext(), binding.addFriendButton)
                viewmodel.addFriend(binding.friendName.text.toString())
                binding.friendName.text.clear()
            }else {
                viewmodel.show("Please fill in friend's name")
            }
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}