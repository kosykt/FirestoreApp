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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

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
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPulseAndPressureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentRecycler.adapter = adapter
        viewModel.getData()
        initFAB()
        observeData()
        initSaveButton()
    }

    private fun initSaveButton() {
        binding.bottomSheetDatePickerInclude.saveData.setOnClickListener {
            try {
                val day = binding.bottomSheetDatePickerInclude.day.text.toString().toInt()
                val month = binding.bottomSheetDatePickerInclude.month.text.toString().toInt()
                val year = binding.bottomSheetDatePickerInclude.year.text.toString().toInt()
                val hour = binding.bottomSheetDatePickerInclude.hour.text.toString().toInt()
                val minute = binding.bottomSheetDatePickerInclude.minute.text.toString().toInt()
                val pressureS = binding.bottomSheetDatePickerInclude.pressureS.text.toString()
                val pressureD = binding.bottomSheetDatePickerInclude.pressureD.text.toString()
                val pulse = binding.bottomSheetDatePickerInclude.pulse.text.toString().toInt()
                if (checkDate(day, month, year, hour, minute) && checkPressure(pressureS, pressureD)) {
                    viewModel.saveData(
                        DomainData(
                            date = GregorianCalendar(year, month, day, hour, minute),
                            pressure = "$pressureS/$pressureD",
                            pulse = pulse
                        )
                    )
                }
            }catch (e: Exception){
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkDate(day: Int, month: Int, year: Int, hour: Int, minute: Int): Boolean {
        return try {
            GregorianCalendar(year, month, day, hour, minute)
            true
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun checkPressure(pressureS: String, pressureD: String): Boolean {
        return if (pressureS >= pressureD) {
            true
        } else {
            Toast.makeText(context,
                "systolic pressure cannot be less than diastolic",
                Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun observeData() {
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
            val bottomSheetDate =
                BottomSheetBehavior.from(binding.bottomSheetDatePickerInclude.bottomSheetContainerDate)
            bottomSheetDate.state = BottomSheetBehavior.STATE_EXPANDED
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