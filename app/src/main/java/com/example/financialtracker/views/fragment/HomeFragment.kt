package com.example.financialtracker.views.fragment

//import com.example.financialtracker.viewmodels.TransactionViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialtracker.databinding.FragmentHomeBinding
import com.example.financialtracker.viewmodels.TransactionViewModel
import com.example.financialtracker.views.DetailActivity
import com.example.financialtracker.views.adapter.TransactionAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val transactionViewModel by viewModels<TransactionViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewModel.getAllTrc(this.requireContext())?.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                Toast.makeText(this.requireContext(), "Data Not Found", Toast.LENGTH_SHORT).show()
            } else {
                binding.transactionRv.apply {
                    layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
                    adapter = TransactionAdapter(it) { transaction ->
                        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                            putExtra(DetailActivity.EXTRA_TRANSACTION, transaction)
                        }
                        startActivity(intent)
                    }
                }
            }
        }


// with danu
//        transactionViewModel.getAllTrc(this.requireContext())?.observe(viewLifecycleOwner){
//if (it.isNullOrEmpty()){
//    Toast.makeText(this.requireContext(),"Data Not Found",Toast.LENGTH_SHORT).show()
//    } else {
//        binding.transactionRv.apply {
//            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
//            adapter = TransactionAdapter(it)
//        }
//    }
//        }


    }


    //Gatau kapan
//    private fun setupRecyclerView() {
//        transactionAdapter = TransactionAdapter()
//        binding.transactionRv.apply {
//            adapter = transactionAdapter
//            layoutManager = LinearLayoutManager(requireContext())
//        }
//    }
//    private fun observeTransactions() {
//        transactionViewModel.getAllTransactions().observe(viewLifecycleOwner, Observer { transactions ->
//            transactions?.let { transactionAdapter.submitList(it) }
//        })
//    }
}


