package com.example.mobv

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv.data.PubDatasource
import com.example.mobv.databinding.FragmentPubListBinding
import com.example.mobv.model.Pub

class PubListFragment : Fragment() {
    private lateinit var pubs: ArrayList<Pub>
    private var _binding: FragmentPubListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
//    private var Menu menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPubListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.list
        with(recyclerView) {
            pubs = PubDatasource(context).loadPubs()
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = MyPubRecyclerViewAdapter(pubs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuAtoZ -> {
                pubs.sortBy { it.tags?.name.toString() }
                recyclerView?.adapter?.notifyDataSetChanged()
                Toast.makeText(context, "Sort A to Z", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menuZtoA -> {
                pubs.sortByDescending { it.tags?.name.toString() }
                recyclerView?.adapter?.notifyDataSetChanged()
                Toast.makeText(context, "Sort Z to A", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}