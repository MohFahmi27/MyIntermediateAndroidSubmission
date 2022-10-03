package com.mohfahmi.storyapp.map_story

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.mohfahmi.storyapp.core.R.drawable.ic_marker_story
import com.mohfahmi.storyapp.core.common_ui.ErrorBottomSheetDialogFragment
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapStoryActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel: MapStoryViewModel by viewModel()
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_story)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_story) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun setMapStyle(map: GoogleMap) {
        map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this,
                R.raw.map_style
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        setMapStyle(map)
        getStoriesWithLocation()
    }


    private fun getStoriesWithLocation() {
        viewModel.tokenKey.observe(this) {
            viewModel.getStoriesWithLocation(it).observe(this, ::manageAllStoriesResponse)
        }
    }

    private fun manageAllStoriesResponse(response: UiState<ArrayList<Story>>) {
        val boundsBuilder = LatLngBounds.Builder()
        when (response.status) {
            Status.LOADING -> Unit
            Status.HIDE_LOADING -> Unit
            Status.SUCCESS -> {
                response.data?.forEach { story ->
                    val latLng = LatLng(story.lat as Double, story.lon as Double)
                    map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(story.name)
                            .icon(getMarkerIconFromDrawable(ic_marker_story))
                    )
                    boundsBuilder.include(latLng)
                }
                val bounds: LatLngBounds = boundsBuilder.build()
                map.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        0
                    )
                )
            }
            Status.ERROR -> {
                ErrorBottomSheetDialogFragment(
                    response.message
                        ?: getString(com.mohfahmi.storyapp.core.R.string.something_went_wrong)
                ).show(supportFragmentManager, ErrorBottomSheetDialogFragment.TAG)
            }
        }
    }

    private fun getMarkerIconFromDrawable(@DrawableRes drawable: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, drawable, null)
            ?: return BitmapDescriptorFactory.defaultMarker()
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}