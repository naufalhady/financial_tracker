package com.example.financialtracker.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.repo.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel: ViewModel() {
    private val trcRepo = TransactionRepository


    fun insertTrc(context: Context, transaction: Transaction) {
        return trcRepo.insertTrc(context, transaction)
    }

    fun getAllTrc(context: Context): LiveData<List<Transaction>>? {
        return trcRepo.getAllTrc(context)
    }

//    fun updateTrc(context: Context, transaction: Transaction){
//        return trcRepo.updateTrc(context,transaction)
//    }

    fun updateTransaction(context: Context, transaction: Transaction) {
        return trcRepo.updateTransaction(context, transaction)
    }
}