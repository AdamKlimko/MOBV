package com.example.mobv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobv.databinding.FragmentPubDetailBinding
import com.example.mobv.model.Pub


class PubDetailFragment : Fragment() {

    private var pubPosition: Int = 0
    private var pub: Pub? = null
    private var _binding: FragmentPubDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pub = it.getParcelable<Pub>("pub")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPubDetailBinding.inflate(inflater, container, false)
        initDetail()
        binding.deleteBtn.setOnClickListener{ onDelete() }

        return binding.root
    }

    fun initDetail() {
        binding.pubNameTextView.text = pub?.tags?.name.toString()
//        binding.ownerNameTextView.text = pub?.tags?.
    }

    fun onDelete() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}