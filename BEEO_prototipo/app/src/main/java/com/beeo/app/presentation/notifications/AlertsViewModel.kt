package com.beeo.app.presentation.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beeo.app.R
import com.beeo.app.data.repository.ActivityRepository
import com.beeo.app.databinding.ItemAlertBinding
import com.beeo.app.domain.model.AlertSeverity
import com.beeo.app.domain.model.AlertType
import com.beeo.app.domain.model.HabitAlert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// ─── ViewModel ────────────────────────────────────────────────────────────────

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    val alerts: StateFlow<List<HabitAlert>> =
        activityRepository.getAllAlerts()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadCount: StateFlow<Int> =
        activityRepository.getUnreadCount()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun markAsRead(id: Long) {
        viewModelScope.launch { activityRepository.markAlertAsRead(id) }
    }

    fun markAllAsRead() {
        viewModelScope.launch { activityRepository.markAllAlertsRead() }
    }
}

// ─── Adapter ──────────────────────────────────────────────────────────────────

class AlertsAdapter(
    private val onMarkRead: (HabitAlert) -> Unit
) : ListAdapter<HabitAlert, AlertsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(alert: HabitAlert) {
            val ctx = binding.root.context

            binding.tvAlertMessage.text = alert.message

            // Format timestamp
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            binding.tvAlertTime.text = sdf.format(Date(alert.timestamp))

            // Severity color
            val (bgColor, iconRes) = when (alert.severity) {
                AlertSeverity.CRITICAL -> R.color.beeo_danger to R.drawable.ic_alert_critical
                AlertSeverity.WARNING -> R.color.beeo_warning to R.drawable.ic_alert_warning
                AlertSeverity.INFO -> R.color.beeo_success to R.drawable.ic_alert_info
            }

            binding.ivSeverityIcon.setImageResource(iconRes)
            binding.cardAlert.strokeColor = ContextCompat.getColor(ctx, bgColor)

            // Read/unread state
            binding.root.alpha = if (alert.isRead) 0.6f else 1.0f

            binding.root.setOnClickListener {
                if (!alert.isRead) onMarkRead(alert)
            }

            // Type label
            binding.tvAlertType.text = when (alert.type) {
                AlertType.STEPS_DECREASED -> "Pasos reducidos"
                AlertType.SEDENTARY_TOO_LONG -> "Sedentarismo"
                AlertType.NO_OUTDOOR_ACTIVITY -> "Sin salir"
                AlertType.ROUTINE_CHANGE -> "Cambio de rutina"
                AlertType.ISOLATION_WARNING -> "Alerta de aislamiento"
                AlertType.POSITIVE_PROGRESS -> "¡Progreso positivo!"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<HabitAlert>() {
        override fun areItemsTheSame(a: HabitAlert, b: HabitAlert) = a.id == b.id
        override fun areContentsTheSame(a: HabitAlert, b: HabitAlert) = a == b
    }
}
