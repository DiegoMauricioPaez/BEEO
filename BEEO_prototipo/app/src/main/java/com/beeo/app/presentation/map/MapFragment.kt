package com.beeo.app.presentation.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.beeo.app.R
import com.beeo.app.databinding.FragmentMapBinding
import com.beeo.app.domain.model.DailyActivity
import com.beeo.app.domain.model.RoutePoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()

    private lateinit var mapView: MapView
    private var locationOverlay: MyLocationNewOverlay? = null
    private var selectedDayIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        setupDaySelector()
        observeData()
    }

    private fun setupMap() {
        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        mapView.controller.setZoom(15.0)

        // Location overlay
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), mapView)
        locationOverlay?.enableMyLocation()
        mapView.overlays.add(locationOverlay)

        // Compass
        val compassOverlay = CompassOverlay(requireContext(), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        // Default center (will update with actual location)
        mapView.controller.setCenter(GeoPoint(4.7110, -74.0721)) // Bogotá default
    }

    private fun setupDaySelector() {
        binding.btnToday.setOnClickListener {
            selectedDayIndex = 0
            viewModel.selectDay(0)
            updateDayButtons(0)
        }
        binding.btnDay1.setOnClickListener {
            selectedDayIndex = 1
            viewModel.selectDay(1)
            updateDayButtons(1)
        }
        binding.btnDay2.setOnClickListener {
            selectedDayIndex = 2
            viewModel.selectDay(2)
            updateDayButtons(2)
        }
        binding.btnDay3.setOnClickListener {
            selectedDayIndex = 3
            viewModel.selectDay(3)
            updateDayButtons(3)
        }
        updateDayButtons(0)
    }

    private fun updateDayButtons(selected: Int) {
        val buttons = listOf(binding.btnToday, binding.btnDay1, binding.btnDay2, binding.btnDay3)
        buttons.forEachIndexed { index, button ->
            button.isSelected = index == selected
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recentActivities.collect { activities ->
                updateDayButtonLabels(activities)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedActivity.collect { activity ->
                activity?.let { drawRoute(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.homeLocation.collect { home ->
                home?.let { drawHomeMarker(it.first, it.second) }
            }
        }
    }

    private fun updateDayButtonLabels(activities: List<DailyActivity>) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM")
        val buttons = listOf(binding.btnToday, binding.btnDay1, binding.btnDay2, binding.btnDay3)
        activities.take(4).forEachIndexed { index, activity ->
            val label = if (index == 0) "Hoy" else activity.date.format(formatter)
            buttons.getOrNull(index)?.text = label
        }
    }

    private fun drawRoute(activity: DailyActivity) {
        // Clear old overlays except location and compass
        mapView.overlays.removeAll { it is Polyline || it is Marker }

        val points = activity.routePoints
        if (points.isEmpty()) {
            binding.tvNoRoute.visibility = View.VISIBLE
            return
        }
        binding.tvNoRoute.visibility = View.GONE

        // Draw polyline route
        val polyline = Polyline().apply {
            setPoints(points.map { GeoPoint(it.latitude, it.longitude) })
            color = ContextCompat.getColor(requireContext(), R.color.beeo_primary)
            width = 8f
        }
        mapView.overlays.add(polyline)

        // Start marker
        addMarker(points.first(), "Inicio", R.drawable.ic_marker_start)

        // End marker
        if (points.size > 1) {
            addMarker(points.last(), "Fin", R.drawable.ic_marker_end)
        }

        // Center map on route
        if (points.isNotEmpty()) {
            val center = GeoPoint(
                points.map { it.latitude }.average(),
                points.map { it.longitude }.average()
            )
            mapView.controller.animateTo(center)
        }

        // Update stats card
        updateStatsCard(activity)
        mapView.invalidate()
    }

    private fun addMarker(point: RoutePoint, title: String, iconRes: Int) {
        val marker = Marker(mapView).apply {
            position = GeoPoint(point.latitude, point.longitude)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            this.title = title
            try {
                icon = ContextCompat.getDrawable(requireContext(), iconRes)
            } catch (e: Exception) {
                // use default icon
            }
        }
        mapView.overlays.add(marker)
    }

    private fun drawHomeMarker(lat: Double, lng: Double) {
        val marker = Marker(mapView).apply {
            position = GeoPoint(lat, lng)
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            title = "Tu casa"
        }
        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    private fun updateStatsCard(activity: DailyActivity) {
        binding.tvMapSteps.text = "${activity.steps.formatSteps()} pasos"
        binding.tvMapDistance.text = "${(activity.distanceMeters / 1000f).format1()} km"
        binding.tvMapActive.text = "${activity.activeMinutes} min activos"
        binding.tvMapZones.text = "${activity.locationChanges} zonas visitadas"
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
        _binding = null
    }

    private fun Int.formatSteps() = String.format("%,d", this)
    private fun Float.format1() = String.format("%.1f", this)
}
