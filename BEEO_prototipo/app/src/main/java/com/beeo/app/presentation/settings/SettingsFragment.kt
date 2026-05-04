package com.beeo.app.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeo.app.databinding.FragmentSettingsBinding
import com.beeo.app.service.TrackingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load current values
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profile.collect { profile ->
                binding.sliderBaselineSteps.value = profile.baselineSteps.toFloat().coerceIn(1000f, 20000f)
                binding.tvBaselineStepsValue.text = "${profile.baselineSteps.formatSteps()} pasos/día"
                binding.switchTracking.isChecked = true
                binding.switchNotifications.isChecked = true
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trackingEnabled.collect { enabled ->
                binding.switchTracking.isChecked = enabled
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.notificationsEnabled.collect { enabled ->
                binding.switchNotifications.isChecked = enabled
            }
        }

        // Tracking toggle
        binding.switchTracking.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTrackingEnabled(isChecked)
            if (isChecked) TrackingService.start(requireContext())
            else TrackingService.stop(requireContext())
        }

        // Notifications toggle
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }

        // Baseline steps slider
        binding.sliderBaselineSteps.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val steps = value.toInt()
                binding.tvBaselineStepsValue.text = "${steps.formatSteps()} pasos/día"
                viewModel.setBaselineSteps(steps)
            }
        }

        // Set home location button
        binding.btnSetHomeLocation.setOnClickListener {
            viewModel.setCurrentLocationAsHome()
            binding.btnSetHomeLocation.text = "✓ Casa guardada"
            binding.btnSetHomeLocation.isEnabled = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Int.formatSteps() = String.format("%,d", this)
}
