package com.example.financialtracker.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.repo.TransactionRepository
import com.example.financialtracker.utility.indonesiaRupiah

class TransactionViewModel : ViewModel() {
    private val trcRepo = TransactionRepository

    fun insertTrc(context: Context, transaction: Transaction) {
        trcRepo.insertTrc(context, transaction)
    }

    fun getAllTrc(context: Context): LiveData<List<Transaction>>? {
        return trcRepo.getAllTrc(context)
    }

    fun deleteTransaction(context: Context, transaction: Transaction) {
        trcRepo.deleteTransaction(context, transaction)
    }

    fun updateTransaction(context: Context, transaction: Transaction) {
        trcRepo.updateTransaction(context, transaction)
    }

    fun getTotalIncome(transactions: List<Transaction>): String {
        val totalIncome = calculateTotalIncome(transactions)
        return indonesiaRupiah(totalIncome)
    }

    fun getTotalExpense(transactions: List<Transaction>): String {
        val totalExpense = calculateTotalExpense(transactions)
        return indonesiaRupiah(totalExpense)
    }

    fun getTotalBalance(transactions: List<Transaction>): String {
        val totalIncome = calculateTotalIncome(transactions)
        val totalExpense = calculateTotalExpense(transactions)
        val totalBalance = totalIncome - totalExpense
        return indonesiaRupiah(totalBalance)
    }

    fun getIncomeTransactions(context: Context): LiveData<List<Transaction>>? {
        return trcRepo.getIncomeTransactions(context)
    }

    fun getExpenseTransactions(context: Context): LiveData<List<Transaction>>? {
        return trcRepo.getExpenseTransactions(context)
    }

    private fun calculateTotalIncome(transactions: List<Transaction>): Double {
        return transactions.filter { it.transactionType == "Income" }
            .sumOf { it.amount }
    }

    private fun calculateTotalExpense(transactions: List<Transaction>): Double {
        return transactions.filter { it.transactionType == "Expense" }
            .sumOf { it.amount }
    }
}
