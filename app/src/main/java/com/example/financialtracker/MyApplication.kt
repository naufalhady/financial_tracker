package com.example.financialtracker

import android.app.Application
import com.example.financialtracker.data.local.TransactionDatabase
import com.example.financialtracker.model.TransactionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { TransactionDatabase.getDatabase(this) }
    val repository by lazy { TransactionRepository(database.transactionDao()) }
}
