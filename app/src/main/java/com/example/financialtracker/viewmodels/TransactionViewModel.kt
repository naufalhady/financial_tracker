package com.example.financialtracker.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.repo.TransactionRepository

class TransactionViewModel: ViewModel() {
    private val trcRepo = TransactionRepository


    fun insertTrc(context: Context, transaction: Transaction) {
        return trcRepo.insertTrc(context, transaction)
    }

    fun getAllTrc(context: Context): LiveData<List<Transaction>>? {
        return trcRepo.getAllTrc(context)
    }
    fun deleteTransaction(context: Context, transaction: Transaction) {
        return trcRepo.deleteTransaction(context, transaction)
    }

//    fun updateTrc(context: Context, transaction: Transaction){
//        return trcRepo.updateTrc(context,transaction)
//    }

    fun updateTransaction(context: Context, transaction: Transaction) {
//        return trcRepo.updateTransaction(context, transaction)
        Log.d("TransactionViewModel", "Updating transaction: $transaction")
        TransactionRepository.updateTransaction(context, transaction)
    }

}