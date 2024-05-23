//package com.example.financialtracker.viewmodels
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.asLiveData
//import androidx.lifecycle.viewModelScope
//import com.example.financialtracker.MyApplication
//import com.example.financialtracker.data.local.TransactionDatabase
//import com.example.financialtracker.model.Transaction
//import com.example.financialtracker.model.TransactionRepository
//import kotlinx.coroutines.launch
//
//class TransactionViewModel(private var repository: TransactionRepository) : ViewModel() {
//
//    private var allTransactions: LiveData<List<Transaction>>
//
//    init {
//        val transactionDao = TransactionDatabase.getDatabase(MyApplication()).transactionDao()
//        repository = TransactionRepository(transactionDao)
//        allTransactions = repository.allTransactions.asLiveData()
//    }
//
//    fun insertTransaction(transaction: Transaction) = viewModelScope.launch {
//        repository.insertTransaction(transaction)
//    }
//
//    fun updateTransaction(transaction: Transaction) = viewModelScope.launch {
//        repository.updateTransaction(transaction)
//    }
//
//    fun deleteTransaction(transaction: Transaction) = viewModelScope.launch {
//        repository.deleteTransaction(transaction)
//    }
//
//    fun deleteTransactionByID(id: Int) = viewModelScope.launch {
//        repository.deleteTransactionByID(id)
//    }
//
//    fun getTransactionByID(id: Int): LiveData<Transaction> {
//        return repository.getTransactionByID(id).asLiveData()
//    }
//
//    fun getAllTransactions(): LiveData<List<Transaction>> {
//        return allTransactions
//    }
//
//    fun getAllSingleTransaction(transactionType: String): LiveData<List<Transaction>> {
//        return repository.getAllSingleTransaction(transactionType).asLiveData()
//    }
//}
