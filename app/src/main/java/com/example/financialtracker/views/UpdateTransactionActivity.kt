package com.example.financialtracker.views

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.financialtracker.R
import com.example.financialtracker.databinding.UpdateTransactionBinding
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.viewmodels.TransactionViewModel
import com.example.financialtracker.views.DetailActivity
import java.util.Calendar

class UpdateTransactionActivity : AppCompatActivity() {
    private lateinit var binding: UpdateTransactionBinding
    private val transactionModel by viewModels<TransactionViewModel>()
    var trcTag: String? = null
    var trcType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()


//    override onCreate(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?
//    ): View? {
//        binding = UpdateTransactionBinding.inflate(inflater,container, false)
//    return binding.root
//        initViews()
//    }
//
//        overrideonViewCreated(view: View, savedInstanceState: Bundle?) {
//            super.onViewCreated(view, savedInstanceState)
//            initViews()
//        }
    }
        private fun initViews() {
            val arrTag = resources.getStringArray(R.array.tag)
            val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, arrTag
            )
            binding.addTransactionLayout.tag.adapter = adapter

            val arrTrcType = resources.getStringArray(R.array.TrcType)
            val typeAdapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, arrTrcType
            )
            binding.addTransactionLayout.transactionType.adapter = typeAdapter

            binding.addTransactionLayout.tag.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        trcTag = arrTag[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
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
                        TODO("Not yet implemented")
                    }

                }
            binding.addTransactionLayout.etWhen.setOnClickListener {
                showDatePickerDialog()

                binding.btnUpdateTransaction.setOnClickListener {
                    if (validateInputs())
                        try {
                            val data = Transaction(
                                title = binding.addTransactionLayout.etTitle.text.toString(),
                                amount = binding.addTransactionLayout.etAmount.text.toString()
                                    .toDouble(),
                                date = binding.addTransactionLayout.etWhen.text.toString(),
                                note = binding.addTransactionLayout.etNote.text.toString(),
                                tag = trcTag.toString(),
                                transactionType = trcType.toString()
                            )
                            transactionModel.updateTransaction(this, data)
                            Toast.makeText(
                                this,
                                "Data Disimpan",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.addTransactionLayout.etTitle.text = null
                            binding.addTransactionLayout.etAmount.text = null
                            binding.addTransactionLayout.etWhen.text = null
                            binding.addTransactionLayout.etNote.text = null
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
                        Toast.makeText(
                            this@UpdateTransactionActivity,
                            "Tag must not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        false
                    }

                    trcType.isNullOrEmpty() -> {
                        Toast.makeText(
                            this@UpdateTransactionActivity,
                            "Transaction type must not be empty",
                            Toast.LENGTH_SHORT
                        ).show()
                        false
                    }

                    else -> true
                }
            }
        }

        private fun clearFields() {
            with(binding.addTransactionLayout) {
                etTitle.text = null
                etAmount.text = null
                etWhen.text = null
                etNote.text = null
            }
            trcTag = null
            trcType = null
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
                        String.format(
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }



//
//    private var transaction: Transaction? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = UpdateTransactionBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Mendapatkan data transaksi yang dikirim dari DetailActivity
//        transaction = intent.getSerializableExtra(DetailActivity.EXTRA_TRANSACTION) as? Transaction
//        transaction?.let {
//            binding.addTransactionLayout.etTitle.setText(it.title)
//            binding.addTransactionLayout.etAmount.setText(it.amount.toString())
//            binding.addTransactionLayout.etWhen.setText(it.date)
//            binding.addTransactionLayout.etNote.setText(it.note)
//            setSpinnerSelection(binding.addTransactionLayout.transactionType, it.transactionType)
//            setSpinnerSelection(binding.addTransactionLayout.tag, it.transactionType)
//            setTagSpinner()
//        }
//
//        binding.btnUpdateTransaction.setOnClickListener {
//            if (validateInputs()) {
//                try {
//                    val updatedTransaction = Transaction(
//                        id = transaction?.id ?: 0, // Pastikan ID transaksi tetap sama
//                        title = binding.addTransactionLayout.etTitle.text.toString(),
//                        amount = binding.addTransactionLayout.etAmount.text.toString().toDouble(),
//                        date = binding.addTransactionLayout.etWhen.text.toString(),
//                        note = binding.addTransactionLayout.etNote.text.toString(),
//                        tag = binding.addTransactionLayout.tag.selectedItem.toString(),
//                        transactionType = binding.addTransactionLayout.transactionType.selectedItem.toString()
//                    )
//
//                    viewModel.updateTransaction(this, updatedTransaction)
//
//                    Toast.makeText(this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show()
//                    finish()
//                } catch (ex: Exception) {
//                    Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.addTransactionLayout.etWhen.setOnClickListener {
//            showDatePickerDialog()
//        }
//    }
//
//    private fun validateInputs(): Boolean {
//        return binding.addTransactionLayout.etTitle.text!!.isNotEmpty() &&
//                binding.addTransactionLayout.etAmount.text!!.isNotEmpty() &&
//                binding.addTransactionLayout.transactionType.selectedItem != null &&
//                binding.addTransactionLayout.tag.selectedItem != null &&
//                binding.addTransactionLayout.etWhen.text!!.isNotEmpty() &&
//                binding.addTransactionLayout.etNote.text!!.isNotEmpty()
//    }
//
//    private fun setSpinnerSelection(spinner: Spinner, value: String) {
//        val adapter = spinner.adapter as ArrayAdapter<String>
//        val position = adapter.getPosition(value)
//        spinner.setSelection(position)
//    }
//
//    private fun setTagSpinner() {
//        val tagOptions = resources.getStringArray(R.array.tag)
//        val tagAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tagOptions)
//        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.addTransactionLayout.tag.adapter = tagAdapter
//    }
//
//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(
//            this,
//            { _, selectedYear, selectedMonth, selectedDay ->
//                binding.addTransactionLayout.etWhen.setText(
//                    String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
//                )
//            },
//            year,
//            month,
//            day
//        )
//        datePickerDialog.show()
//    }
//}
