package uz.kattabozor.test.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import uz.kattabozor.test.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderLoading()
        renderError()
        renderOffers()
    }

    private fun renderLoading() = lifecycleScope.launch {
        viewModel.state.map { it.loading }.collectLatest {
            binding.loading.root.isVisible = it
        }
    }

    private fun renderError() {
        binding.error.retry.setOnClickListener {
            viewModel.processInput(MainViewModel.Input.GetOffers)
        }

        lifecycleScope.launch {
            viewModel.state.map { it.error }.collectLatest {
                binding.error.root.isVisible = it
            }
        }
    }

    private fun renderOffers() {
        val adapter = OfferAdapter()
        binding.offers.adapter = adapter

        lifecycleScope.launch {
            viewModel.state.map { it.offers }.collectLatest {
                binding.offers.isVisible = it != null
                adapter.submitList(it)
            }
        }
    }
}