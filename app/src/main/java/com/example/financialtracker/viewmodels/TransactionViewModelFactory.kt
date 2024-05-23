//package com.example.financialtracker.viewmodels
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.financialtracker.model.TransactionRepository
//
//
//class TransactionViewModelFactory(
//    private val repository: TransactionRepository
//): ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)){
//            @Suppress("UNCHECKED_CAST")
//            return TransactionViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}