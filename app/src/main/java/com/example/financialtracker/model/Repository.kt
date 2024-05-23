package com.example.financialtracker.model

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    fun getAllSingleTransaction(transactionType: String): Flow<List<Transaction>> {
        return transactionDao.getAllSingleTransaction(transactionType)
    }

    fun getTransactionByID(id: Int): Flow<Transaction> {
        return transactionDao.getTransactionByID(id)
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun deleteTransactionByID(id: Int) {
        transactionDao.deleteTransactionByID(id)
    }
}
