package com.example.financialtracker.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialtracker.databinding.FragmentHomeBinding
//import com.example.financialtracker.viewmodels.TransactionViewModel
import com.example.financialtracker.views.adapter.TransactionAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var transactionAdapter: TransactionAdapter
//    private val transactionViewModel by viewModels<TransactionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
//        observeTransactions()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter()
        binding.transactionRv.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

//    private fun observeTransactions() {
//        transactionViewModel.getAllTransactions().observe(viewLifecycleOwner) { transactions ->
//            transactions?.let { transactionAdapter.differ.submitList(it) }
//        }
//    }
}
