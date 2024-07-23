package com.example.productcrud.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.productcrud.R
import com.example.productcrud.databinding.ActivityProductFormBinding
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.utils.CurrencyTextWatcher
import com.example.productcrud.utils.constants.Constants
import com.example.productcrud.utils.extensions.toCurrency
import com.example.productcrud.utils.extensions.toDayMonthYear
import com.example.productcrud.utils.extensions.validate
import com.example.productcrud.viewmodels.ProductViewModelFactory
import com.example.productcrud.viewmodels.form.ProductFormViewModel
import java.util.Calendar

class ProductFormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityProductFormBinding
    private lateinit var viewModel: ProductFormViewModel
    private val calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar!!.title = getString(R.string.create_product)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(this)
        )[ProductFormViewModel::class.java]
        observer()

        checkIfIsEdition()

        binding.priceEditText.addTextChangedListener(CurrencyTextWatcher(binding.priceEditText))

        binding.dateEditText.setOnClickListener {
            hideKeyboard()
            showDatePickerDialog()
        }

        binding.saveButton.setOnClickListener {
            save()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        val formattedDate = calendar.time.toDayMonthYear
        binding.dateEditText.setText(formattedDate)
    }

    private fun observer() {
        viewModel.state.observe(this) {
            if (it.saved) {
                val intent = Intent()
                if (it.editProduct != null) {
                    intent.putExtra(Constants.INTENT.MESSAGE, getString(R.string.updated_product))
                } else {
                    intent.putExtra(Constants.INTENT.MESSAGE, getString(R.string.created_product))
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            if (it.editProduct != null && !it.saved) {
                val editProduct = it.editProduct!!
                binding.apply {
                    nameEditText.setText(editProduct.name)
                    descriptionEditText.setText(editProduct.description)
                    priceEditText.setText(editProduct.price.toCurrency)
                    dateEditText.setText(editProduct.dueDate.toDayMonthYear)
                }
            }
        }
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(this, this, year, month, day)
        dialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        dialog.show()
    }

    private fun checkIfIsEdition() {
        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.INTENT.PRODUCT, ProductEntity::class.java)
        } else {
            intent.getParcelableExtra(Constants.INTENT.PRODUCT) as ProductEntity?
        }
        product?.let {
            supportActionBar!!.title = getString(R.string.edit_product)
            viewModel.setEditProduct(product)
        }
    }

    private fun save() {
        if (hasError()) {
            return
        }
        viewModel.save(
            binding.nameEditText.text.toString(),
            binding.descriptionEditText.text.toString(),
            binding.priceEditText.text.toString(),
            binding.dateEditText.text.toString(),
        )
    }

    private fun hasError(): Boolean {
        val nameResult = binding.nameTextInputLayout.validate(getString(R.string.required_name_field))
        val descriptionResult = binding.descriptionTextInputLayout.validate(getString(R.string.required_description_field))
        val priceResult = binding.priceTextInputLayout.validate(getString(R.string.required_price_field))
        val dateResult = binding.dateTextInputLayout.validate(getString(R.string.required_date_field))
        return listOf(nameResult, descriptionResult, priceResult, dateResult).any { !it }
    }
}
