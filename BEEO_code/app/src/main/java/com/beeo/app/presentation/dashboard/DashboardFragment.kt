package com.beeo.app.presentation.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.beeo.app.R
import com.beeo.app.databinding.FragmentDashboardBinding
import com.beeo.app.domain.model.ActivityTrend
import com.beeo.app.domain.model.DailyActivity
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var activityAdapter: ActivityHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCharts()
        observeData()
    }

    private fun setupRecyclerView() {
        activityAdapter = ActivityHistoryAdapter()
        binding.rvActivityHistory.apply {
            adapter = activityAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupCharts() {
        // Steps chart
        binding.stepsChart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            axisRight.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.textColor = Color.WHITE
            axisLeft.textColor = Color.WHITE
            legend.textColor = Color.WHITE
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.todayActivity.collect { activity ->
                activity?.let { updateTodayCard(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recentActivities.collect { activities ->
                updateStepsChart(activities)
                activityAdapter.submitList(activities)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weeklyStats.collect { stats ->
                if (stats.isNotEmpty()) {
                    updateTrendBanner(stats.first().trend)
                }
            }
        }
    }

    private fun updateTodayCard(activity: DailyActivity) {
        binding.tvTodaySteps.text = activity.steps.formatSteps()
        binding.tvTodayDistance.text = "${(activity.distanceMeters / 1000f).format1()} km"
        binding.tvTodayActive.text = "${activity.activeMinutes} min"
        binding.tvTodayZones.text = "${activity.locationChanges}"

        // Progress bar
        val baseline = 8000
        val progress = ((activity.steps.toFloat() / baseline) * 100).toInt().coerceIn(0, 100)
        binding.progressSteps.progress = progress
        binding.tvStepsGoal.text = "${activity.steps.formatSteps()} / ${baseline.formatSteps()}"
    }

    private fun updateStepsChart(activities: List<DailyActivity>) {
        if (activities.isEmpty()) return

        val reversed = activities.reversed()
        val formatter = DateTimeFormatter.ofPattern("dd/MM")
        val labels = reversed.map { it.date.format(formatter) }

        val entries = reversed.mapIndexed { i, a -> BarEntry(i.toFloat(), a.steps.toFloat()) }

        // Color by step count (green = good, yellow = ok, red = low)
        val colors = reversed.map { activity ->
            when {
                activity.steps >= 8000 -> ContextCompat.getColor(requireContext(), R.color.beeo_success)
                activity.steps >= 4000 -> ContextCompat.getColor(requireContext(), R.color.beeo_warning)
                else -> ContextCompat.getColor(requireContext(), R.color.beeo_danger)
            }
        }

        val dataSet = BarDataSet(entries, "Pasos por día").apply {
            this.colors = colors
            valueTextColor = Color.WHITE
            valueTextSize = 9f
        }

        binding.stepsChart.apply {
            data = BarData(dataSet)
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            xAxis.labelCount = labels.size
            animateY(800)
            invalidate()
        }
    }

    private fun updateTrendBanner(trend: ActivityTrend) {
        val (text, color) = when (trend) {
            ActivityTrend.IMPROVING -> "📈 Tu actividad está mejorando esta semana" to R.color.beeo_success
            ActivityTrend.STABLE -> "➡️ Tu rutina se mantiene estable" to R.color.beeo_primary
            ActivityTrend.DECLINING -> "📉 Tu actividad ha bajado esta semana" to R.color.beeo_warning
            ActivityTrend.CRITICAL_DECLINE -> "⚠️ Caída crítica en tu actividad" to R.color.beeo_danger
        }
        binding.tvTrendBanner.text = text
        binding.tvTrendBanner.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Int.formatSteps() = String.format("%,d", this)
    private fun Float.format1() = String.format("%.1f", this)
}
