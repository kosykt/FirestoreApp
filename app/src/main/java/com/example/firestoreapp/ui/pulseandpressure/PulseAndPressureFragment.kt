package com.example.firestoreapp.ui.pulseandpressure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.firestoreapp.databinding.FragmentPulseAndPressureBinding
import com.example.firestoreapp.domain.model.DomainData
import com.example.firestoreapp.ui.AppState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

class PulseAndPressureFragment : Fragment() {

    private var _binding: FragmentPulseAndPressureBinding? = null
    private val binding: FragmentPulseAndPressureBinding
        get() = _binding ?: throw RuntimeException("FragmentPulseAndPressureBinding? = null")

    private val viewModel by viewModels<PulseAndPressureViewModel>()
    private val adapter by lazy {
        PulseAndPressureAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPulseAndPressureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentRecycler.adapter = adapter
        initFAB()
        viewModel.getData()
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    private fun initFAB() {
        binding.fragmentFab.setOnClickListener {

        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                refreshAdapter(appState.data)
            }
            is AppState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshAdapter(list: List<DomainData>) = adapter.submitList(list)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}