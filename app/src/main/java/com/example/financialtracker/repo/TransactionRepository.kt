package com.example.financialtracker.repo


import android.content.Context
import androidx.lifecycle.LiveData
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.room.AppDatabase
import com.example.financialtracker.room.TransactionDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransactionRepository {
    companion object {
        private var appDatabase: AppDatabase? = null


        fun initDb(context: Context): AppDatabase {
            return AppDatabase.getDatabase(context)
        }


        fun getAllTrc(context: Context): LiveData<List<Transaction>>? {
            appDatabase = initDb(context)
            return appDatabase?.transactionDao()?.getAllTransactions()
        }

        fun insertTrc(context: Context, transaction: Transaction) {
            appDatabase = initDb(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDatabase!!.transactionDao().insertTransaction(transaction)
            }
        }
        fun updateTransaction(context: Context, transaction: Transaction){
            appDatabase = initDb(context)
            CoroutineScope(Dispatchers.IO).launch {
                appDatabase!!.transactionDao().updateTransaction(transaction)
            }
        }
    }
}