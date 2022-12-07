package com.example.zadanie.ui.widget.friendsList

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.ui.widget.barlist.BarsAdapter
import com.example.zadanie.ui.widget.barlist.BarsRecyclerView

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
        friendsAdapter = FriendsAdapter()
        adapter = friendsAdapter
    }
}

@BindingAdapter(value = ["friends"])
fun FriendsRecyclerView.applyItems(
    friends: List<FriendItem>?
) {
    (adapter as FriendsAdapter).items = friends ?: emptyList()
}
