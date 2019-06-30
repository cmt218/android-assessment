package assessment.com.myapplication.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import assessment.com.myapplication.R
import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions


class MapFragment : Fragment(), MapContract.MapView, OnMapReadyCallback {
    private var zoomToLocation: Int? = null
    private var mapboxMap: MapboxMap? = null
    private lateinit var ctx: Context
    private lateinit var presenter: MapContract.MapPresenter
    private lateinit var mapView: MapView
    private lateinit var symbolManager: SymbolManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = context ?: return
        presenter = MapPresenter(this)
        presenter.viewCreated()
        Mapbox.getInstance(ctx, MAPBOX_KEY);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return view
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.MAPBOX_STREETS, object : Style.OnStyleLoaded {
            override fun onStyleLoaded(style: Style) {
                val markerDrawable = ContextCompat.getDrawable(ctx, R.drawable.ic_place_red_24dp)
                markerDrawable?.let { drawable ->
                    style.addImage(MARKER_ICON, drawable)
                } ?: Log.e(this.javaClass.simpleName, "failed to load marker resource")
                symbolManager = SymbolManager(mapView, mapboxMap, style)
                presenter.loadLocations()
                zoomToLocation?.let { presenter.loadSingleLocation(it) }
            }
        })
    }

    override fun renderMarkers(locations: List<Location>) {
        for (location in locations) {
            val symbolOptions = SymbolOptions()
                .withLatLng(LatLng(location.latitude, location.longitude))
                .withTextField(location.name)
                .withTextOffset(arrayOf(0f, 2f))
                .withIconImage(MARKER_ICON)
            symbolManager.create(symbolOptions)
        }
    }

    override fun zoomToLocation(coordinate: LatLng) {
        val position = CameraPosition.Builder()
            .target(coordinate)
            .zoom(16.0)
            .build()
        mapboxMap?.animateCamera(CameraUpdateFactory.newCameraPosition(position))
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun provideContext(): Context = ctx

    companion object {
        const val MAPBOX_KEY =
            "pk.eyJ1IjoidG9tbGluc29uNjMxIiwiYSI6ImNqeGR5aGtseTBqZHIzeW13bnV6ZXFtdWgifQ.phJvUqBz9mgl2GVz8o2zuA"
        const val MARKER_ICON = "marker_icon"

        fun newInstance(location: Int?): MapFragment = MapFragment().apply { zoomToLocation = location }
    }
}