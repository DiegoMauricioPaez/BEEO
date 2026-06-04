package com.beeo.app.presentation.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.beeo.app.databinding.FragmentAlertsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlertsFragment : Fragment() {

    private var _binding: FragmentAlertsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlertsViewModel by viewModels()
    private lateinit var alertAdapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertAdapter = AlertsAdapter { alert ->
            viewModel.markAsRead(alert.id)
        }

        binding.rvAlerts.apply {
            adapter = alertAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.btnMarkAllRead.setOnClickListener {
            viewModel.markAllAsRead()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.alerts.collect { alerts ->
                alertAdapter.submitList(alerts)
                binding.tvEmptyState.visibility = if (alerts.isEmpty()) View.VISIBLE else View.GONE
                binding.rvAlerts.visibility = if (alerts.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
