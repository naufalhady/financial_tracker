package com.example.financialtracker.views.fragment

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.financialtracker.R
import com.example.financialtracker.databinding.FragmentAddTransactionBinding
import com.example.financialtracker.model.Transaction
import com.example.financialtracker.viewmodels.TransactionViewModel

class AddTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private  val transactionModel by viewModels<TransactionViewModel>()
    var trcTag: String? = null
    var trcType: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
        private fun initViews() {
            val arrTag = resources.getStringArray(R.array.tag)
            val adapter = ArrayAdapter(
                this.requireContext(), android.R.layout.simple_spinner_item, arrTag
            )

            binding.addTransactionLayout.tag.adapter = adapter

            val arrTrcType = resources.getStringArray(R.array.TrcType)
            val typeAdapter = ArrayAdapter(
                this.requireContext(), android.R.layout.simple_spinner_item, arrTrcType
            )
            binding.addTransactionLayout.transactionType.adapter = typeAdapter

//            binding.addTransactionLayout.tag.onItemSelectedListener =
//                object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>,
//                        view: View,
//                        position: Int,
//                        id: Long
//                    ) {
//                        trcTag = arrTag[position]
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                        // Do nothing
//                    }
//                }

            binding.addTransactionLayout.tag.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
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
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        trcType = arrTrcType[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            // Transform EditText to DatePicker
                binding.addTransactionLayout.etWhen.setOnClickListener {
                    showDatePickerDialog()

                binding.btnSave.setOnClickListener {
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
                        transactionModel.insertTrc(this.requireContext(), data)
                        Toast.makeText(
                            this.requireContext(),
                            "Data Berhasil Disimpan",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.addTransactionLayout.etTitle.text = null
                        binding.addTransactionLayout.etAmount.text = null
                        binding.addTransactionLayout.etWhen.text = null
                        binding.addTransactionLayout.etNote.text = null
                    } catch (ex: Exception) {
                        Toast.makeText(this.requireContext(), ex.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(requireContext(), "Tag must not be empty", Toast.LENGTH_SHORT).show()
                    false
                }
                trcType.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "Transaction type must not be empty", Toast.LENGTH_SHORT).show()
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

    private fun showDatePickerDialog() {val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
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
