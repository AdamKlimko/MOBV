package com.example.zadanie.ui.widget.friendsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.R
import com.example.zadanie.data.db.model.FriendItem
import com.example.zadanie.helpers.autoNotify
import com.example.zadanie.ui.widget.barlist.BarsEvents
import kotlin.properties.Delegates

class FriendsAdapter(val events: BarsEvents? = null) :
    RecyclerView.Adapter<FriendsAdapter.FriendItemViewHolder>() {
    var items: List<FriendItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id.compareTo(n.id) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.FriendItemViewHolder {
        return FriendsAdapter.FriendItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FriendsAdapter.FriendItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class FriendItemViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.friend_item,
            parent,
            false)
    ) : RecyclerView.ViewHolder(itemView){

        fun bind(item: FriendItem) {
            itemView.findViewById<TextView>(R.id.name).text = item.name
        }
    }
}