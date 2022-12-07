package com.example.zadanie.ui.widget.friendsList

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.ui.fragments.BarsFragmentDirections
import com.example.zadanie.ui.fragments.FriendsFragmentDirections

interface FriendsEvents {
    fun onFriendClick(friend: FriendItem)
}

class FriendsRecyclerView : RecyclerView {
    private lateinit var friendsAdapter: FriendsAdapter

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, VERTICAL, false)
        friendsAdapter = FriendsAdapter(object : FriendsEvents {
            override fun onFriendClick(friend: FriendItem) {
                friend.bar_id?.let {
                    this@FriendsRecyclerView.findNavController().navigate(
                        FriendsFragmentDirections.actionToDetail(it)
                    )
                }
            }
        })
        adapter = friendsAdapter
    }
}

@BindingAdapter(value = ["friends"])
fun FriendsRecyclerView.applyItems(
    friends: List<FriendItem>?
) {
    (adapter as FriendsAdapter).items = friends ?: emptyList()
}
