package com.example.zadanie.ui.widget.barlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zadanie.R
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.autoNotify
import com.google.android.material.chip.Chip
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates

class BarsAdapter(val events: BarsEvents? = null) :
    RecyclerView.Adapter<BarsAdapter.BarItemViewHolder>() {
    var items: List<BarItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id.compareTo(n.id) == 0 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarItemViewHolder {
        return BarItemViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BarItemViewHolder, position: Int) {
        holder.bind(items[position], events)
    }

    class BarItemViewHolder(
        private val parent: ViewGroup,
        itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.bar_item,
            parent,
            false)
        ) : RecyclerView.ViewHolder(itemView){

        fun bind(item: BarItem, events: BarsEvents?) {
            itemView.findViewById<TextView>(R.id.name).text = item.name
            itemView.findViewById<TextView>(R.id.count).text = item.visitors.toString()
            itemView.findViewById<Chip>(R.id.type).text = getTypeTextString(item.type)
            itemView.findViewById<Chip>(R.id.type).chipIcon = ResourcesCompat.getDrawable(
                itemView.context.resources ,
                R.drawable.ic_baseline_check_24, null
            )

            itemView.setOnClickListener { events?.onBarClick(item) }
        }

        fun getTypeTextString(type: String): String {
            return when(type) {
                "fast_food" -> "Fast food"
                "cafe" -> "Cafe"
                "restaurant" -> "Restaurant"
                "bar" -> "Bar"
                "pub" -> "Pub"
                else -> {return "Bar"}
            }
        }
    }
}