package com.example.productcrud.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productcrud.R
import com.example.productcrud.databinding.ActivityMainBinding
import com.example.productcrud.entities.ProductEntity
import com.example.productcrud.utils.constants.Constants
import com.example.productcrud.view.adapters.EmptyStateObserver
import com.example.productcrud.view.adapters.ProductAdapter
import com.example.productcrud.view.listeners.ProductItemClickListener
import com.example.productcrud.viewmodels.list.ProductListViewModel
import com.example.productcrud.viewmodels.ProductViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ProductItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar!!.title = getString(R.string.products)

        viewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(this)
        )[ProductListViewModel::class.java]
        productAdapter = ProductAdapter(this, this)
        productAdapter.registerAdapterDataObserver(
            EmptyStateObserver(
                binding.emptyState,
                binding.productsRecyclerView
            )
        )
        binding.productsRecyclerView.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(applicationContext)
        }

        viewModel.getAll()
        observer()

        binding.floatAddButton.setOnClickListener {
            goToProductFormActivity()
        }
    }

    private fun observer() {
        viewModel.products.observe(this) {
            productAdapter.submitList(it)
        }
    }

    private fun goToProductFormActivity(productEntity: ProductEntity? = null) {
        val intent = Intent(this, ProductFormActivity::class.java)
        productEntity?.let {
            intent.putExtra(Constants.INTENT.PRODUCT, it)
        }
        startForResult.launch(intent)
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data!!
                val message = intent.getStringExtra(Constants.INTENT.MESSAGE)!!
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                viewModel.getAll()
            }
        }

    override fun onEdit(productEntity: ProductEntity) {
        goToProductFormActivity(productEntity)
    }

    override fun onDelete(productEntity: ProductEntity) {
        AlertDialog.Builder(this).setTitle(R.string.delete)
            .setMessage(R.string.are_you_sure_you_want_to_remove_this_item)
            .setNegativeButton(R.string.no, null)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.delete(productEntity)
            }.show()
    }
}