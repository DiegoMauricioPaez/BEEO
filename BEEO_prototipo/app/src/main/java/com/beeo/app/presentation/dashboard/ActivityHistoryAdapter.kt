package com.beeo.app.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeo.app.R
import com.beeo.app.databinding.ItemActivityHistoryBinding
import com.beeo.app.domain.model.DailyActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class ActivityHistoryAdapter :
    ListAdapter<DailyActivity, ActivityHistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemActivityHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), getItemOrNull(position + 1))
    }

    private fun getItemOrNull(position: Int): DailyActivity? =
        if (position < itemCount) getItem(position) else null

    inner class ViewHolder(private val binding: ItemActivityHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: DailyActivity, previousActivity: DailyActivity?) {
            val ctx = binding.root.context
            val today = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM")

            // Date label
            val dateLabel = when (activity.date) {
                today -> "Hoy"
                today.minusDays(1) -> "Ayer"
                else -> "${activity.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es"))} ${activity.date.format(formatter)}"
            }
            binding.tvDate.text = dateLabel

            // Steps
            binding.tvSteps.text = "${activity.steps.formatSteps()} pasos"
            binding.tvDistance.text = "${(activity.distanceMeters / 1000f).format1()} km"
            binding.tvActiveTime.text = "${activity.activeMinutes} min activos"
            binding.tvZones.text = "${activity.locationChanges} zonas"

            // Step bar
            val baselineSteps = 8000f
            binding.progressDaySteps.progress = ((activity.steps / baselineSteps) * 100).toInt().coerceIn(0, 100)

            // Color step count by threshold
            val stepsColor = when {
                activity.steps >= 8000 -> R.color.beeo_success
                activity.steps >= 4000 -> R.color.beeo_warning
                else -> R.color.beeo_danger
            }
            binding.tvSteps.setTextColor(ContextCompat.getColor(ctx, stepsColor))

            // Change vs previous day
            previousActivity?.let { prev ->
                val diff = activity.steps - prev.steps
                val diffPct = if (prev.steps > 0) (diff.toFloat() / prev.steps * 100).toInt() else 0
                val sign = if (diff >= 0) "+" else ""
                binding.tvChange.text = "$sign${diffPct}%"
                val changeColor = if (diff >= 0) R.color.beeo_success else R.color.beeo_danger
                binding.tvChange.setTextColor(ContextCompat.getColor(ctx, changeColor))
            } ?: run {
                binding.tvChange.text = ""
            }
        }

        private fun Int.formatSteps() = String.format("%,d", this)
        private fun Float.format1() = String.format("%.1f", this)
    }

    class DiffCallback : DiffUtil.ItemCallback<DailyActivity>() {
        override fun areItemsTheSame(a: DailyActivity, b: DailyActivity) = a.date == b.date
        override fun areContentsTheSame(a: DailyActivity, b: DailyActivity) = a == b
    }
}
