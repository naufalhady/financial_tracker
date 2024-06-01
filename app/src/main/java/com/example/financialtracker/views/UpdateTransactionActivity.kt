package com.example.financialtracker.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.financialtracker.R
import com.example.financialtracker.databinding.UpdateTransactionBinding
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.utility.indonesiaRupiah
import com.example.financialtracker.viewmodels.TransactionViewModel
import java.util.Calendar

class UpdateTransactionActivity : AppCompatActivity() {
    private lateinit var binding: UpdateTransactionBinding
    private val transactionModel by viewModels<TransactionViewModel>()
    private var trcTag: String? = null
    private var trcType: String? = null
    private var transaction: Transaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_dark)

        transaction = intent.getSerializableExtra(DetailActivity.EXTRA_TRANSACTION) as? Transaction
        transaction?.let {
            binding.addTransactionLayout.etTitle.setText(it.title)
            binding.addTransactionLayout.etAmount.setText(it.amount.toString())
            binding.addTransactionLayout.etWhen.setText(it.date)
            binding.addTransactionLayout.etNote.setText(it.note)
            setSpinnerSelection(binding.addTransactionLayout.transactionType, it.transactionType ?: "")
            setSpinnerSelection(binding.addTransactionLayout.tag, it.tag ?: "")
        }

        initViews()
    }

    private fun initViews() {
        val arrTag = resources.getStringArray(R.array.tag)
        val adapterTag = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, arrTag
        )
        binding.addTransactionLayout.tag.adapter = adapterTag

        val arrTrcType = resources.getStringArray(R.array.TrcType)
        val adapterTrcType = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, arrTrcType
        )
        binding.addTransactionLayout.transactionType.adapter = adapterTrcType

        binding.addTransactionLayout.tag.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    trcTag = arrTag[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }

        binding.addTransactionLayout.transactionType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    trcType = arrTrcType[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }

        binding.addTransactionLayout.etWhen.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnUpdateTransaction.setOnClickListener {
            if (validateInputs()) {
                try {
                    val currentTransaction = transaction
                    if (currentTransaction != null) {
                        val updatedTransaction = Transaction(
                            id = currentTransaction.id,
                            title = binding.addTransactionLayout.etTitle.text.toString(),
                            amount = binding.addTransactionLayout.etAmount.text.toString().toDouble(),
                            date = binding.addTransactionLayout.etWhen.text.toString(),
                            note = binding.addTransactionLayout.etNote.text.toString(),
                            tag = trcTag.toString(),
                            transactionType = trcType.toString()
                        )
                        transactionModel.updateTransaction(this, updatedTransaction)
                        // Memformat jumlah transaksi dan menampilkannya
                        val formattedAmount = indonesiaRupiah(updatedTransaction.amount)
                        binding.addTransactionLayout.etAmount.setText(formattedAmount)
                        Toast.makeText(this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Transaction is null", Toast.LENGTH_SHORT).show()
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        with(binding.addTransactionLayout) {
            return when {
                etTitle.text.isNullOrEmpty() -> {
                    etTitle.error = "Title must not be empty"
                    false
                }
                etAmount.text.isNullOrEmpty() -> {
                    etAmount.error = "Amount must not be empty"
                    false
                }
                etWhen.text.isNullOrEmpty() -> {
                    etWhen.error = "Date must not be empty"
                    false
                }
                etNote.text.isNullOrEmpty() -> {
                    etNote.error = "Note must not be empty"
                    false
                }
                trcTag.isNullOrEmpty() -> {
                    Toast.makeText(this@UpdateTransactionActivity, "Tag must not be empty", Toast.LENGTH_SHORT).show()
                    false
                }
                trcType.isNullOrEmpty() -> {
                    Toast.makeText(this@UpdateTransactionActivity, "Transaction type must not be empty", Toast.LENGTH_SHORT).show()
                    false
                }
                transaction == null -> {
                    Toast.makeText(this@UpdateTransactionActivity, "Transaction is null", Toast.LENGTH_SHORT).show()
                    false
                }
                else -> {
                    // Validate amount
                    try {
                        val amount = etAmount.text.toString().toDouble()
                        if (amount <= 0) {
                            etAmount.error = "Amount must be greater than 0"
                            return false
                        }
                    } catch (e: NumberFormatException) {
                        etAmount.error = "Invalid amount format"
                        return false
                    }
                    true
                }
            }
        }
    }


    private fun setSpinnerSelection(spinner: Spinner, value: String) {
        val adapter = spinner.adapter as? ArrayAdapter<String>
        adapter?.let {
            val position = it.getPosition(value)
            spinner.setSelection(position) }
            }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.addTransactionLayout.etWhen.setText(
                    String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                )
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}
