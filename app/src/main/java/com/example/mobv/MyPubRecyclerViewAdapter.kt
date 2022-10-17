package com.example.mobv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.databinding.FragmentPubEntryBinding
import com.example.mobv.model.Pub


class MyPubRecyclerViewAdapter(
    private val pubs: ArrayList<Pub>
) : RecyclerView.Adapter<MyPubRecyclerViewAdapter.PubViewHolder>() {

    class PubViewHolder(binding: FragmentPubEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val pubNameTextView: TextView = binding.pubName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubViewHolder {

        return PubViewHolder(
            FragmentPubEntryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PubViewHolder, position: Int) {
        val item = pubs[position]
        holder.pubNameTextView.text = item.tags?.name
        holder.pubNameTextView.setOnClickListener{
            val action = PubListFragmentDirections.actionPubListFragmentToPubDetailFragment(
                pub = item,
                position = position
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = pubs.size
}