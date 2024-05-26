package com.example.financialtracker.views

//import UpdateTransactionActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.financialtracker.databinding.DetailTransactionBinding
import com.example.financialtracker.model.Transaction

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
        val editButton = binding.btnEditTransaction
        editButton.setOnClickListener {
            transaction?.let {
                val intent = Intent(this, UpdateTransactionActivity::class.java)
                intent.putExtra("TRANSACTION", it)
                startActivity(intent)
            }
        }
    }
    // Fungsi untuk menyiapkan tombol Delete Transaction
    private fun setupDeleteButton(transaction: Transaction?) {
        val deleteButton = binding.btnDeleteTransaction
        deleteButton.setOnClickListener {
            // Tambahkan logika untuk menghapus transaksi
        }
    }
    companion object {
        const val EXTRA_TRANSACTION = "transaction"
    }
}
