package com.example.financialtracker.views.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialtracker.R
import com.example.financialtracker.databinding.FragmentPengeluaranBinding
import com.example.financialtracker.viewmodels.TransactionViewModel
import com.example.financialtracker.views.DetailActivity
import com.example.financialtracker.views.adapter.TransactionAdapter

class PengeluaranFragment : Fragment() {

    private lateinit var binding: FragmentPengeluaranBinding
    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPengeluaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the status bar color
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue_dark)

        transactionViewModel.getExpenseTransactions(this.requireContext())?.observe(viewLifecycleOwner) { transactions ->
            Log.d("PengeluaranFragment", "Observed transactions: $transactions")
            if (transactions.isNullOrEmpty()) {
                Toast.makeText(this.requireContext(), "Tidak ada data transaksi!", Toast.LENGTH_SHORT).show()
            } else {
                binding.transactionRv.apply {
                    layoutManager = LinearLayoutManager(this@PengeluaranFragment.requireContext())
                    adapter = TransactionAdapter(transactions) { transaction ->
                        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_TRANSACTION, transaction)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
