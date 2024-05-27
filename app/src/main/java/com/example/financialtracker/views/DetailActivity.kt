package com.example.financialtracker.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.financialtracker.databinding.DetailTransactionBinding
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.repo.TransactionRepository
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = intent.getSerializableExtra(EXTRA_TRANSACTION) as? Transaction
        transaction?.let {
            binding.title.text = it.title
            binding.amount.text = it.amount.toString()
            binding.type.text = it.transactionType
            binding.tag.text = it.tag
            binding.date.text = it.date
            binding.note.text = it.note
            binding.createdAt.text = it.createdAtDateFormat
        }
        // Menambahkan tombol Edit dan Delete beserta logikanya
        setupEditButton(transaction)
        setupDeleteButton(transaction)
    }

    // Fungsi untuk menyiapkan tombol Edit Transaction
    private fun setupEditButton(transaction: Transaction?) {
//        Log.d("PreviousActivity", "Transaction to be sent: $transaction") // Tambahkan log untuk memeriksa nilai transaction sebelum dikirim
        val editButton = binding.btnEditTransaction
        editButton.setOnClickListener {
            transaction?.let {
//                Log.d("PreviousActivity", "Transaction to be sent: $transaction") // Tambahkan log untuk memeriksa nilai transaction sebelum dikirim
                val intent = Intent(this, UpdateTransactionActivity::class.java)
                intent.putExtra(EXTRA_TRANSACTION, it)
                startActivity(intent)
            }
        }
    }

    // Fungsi untuk menyiapkan tombol Delete Transaction
    private fun setupDeleteButton(transaction: Transaction?) {
        val deleteButton = binding.btnDeleteTransaction
        deleteButton.setOnClickListener {
            transaction?.let { transactionToDelete ->
                // Menampilkan dialog konfirmasi sebelum menghapus transaksi
                showDeleteConfirmationDialog(transactionToDelete)
            }
        }
    }

    private fun showDeleteConfirmationDialog(transaction: Transaction) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle("Delete Transaction")
            setMessage("Are you sure you want to delete this transaction?")
            setPositiveButton("Yes") { dialog, _ ->
                // Memanggil fungsi untuk menghapus transaksi dari repository
                deleteTransactionFromRepository(transaction)
                dialog.dismiss()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun deleteTransactionFromRepository(transaction: Transaction) {
        // Menggunakan lifecycleScope untuk menjalankan coroutine di dalam activity
        lifecycleScope.launch {
            TransactionRepository.deleteTransaction(this@DetailActivity, transaction)
            // Menampilkan toast setelah data berhasil dihapus
            Toast.makeText(this@DetailActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            // Kembali ke HomeFragment setelah data dihapus
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        // Buat intent untuk kembali ke MainActivity (atau activity yang memuat HomeFragment)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    companion object {
        const val EXTRA_TRANSACTION = "transaction"
    }
}
