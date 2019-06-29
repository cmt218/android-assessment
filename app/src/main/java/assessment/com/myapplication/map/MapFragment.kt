package assessment.com.myapplication.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import assessment.com.myapplication.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions


class MapFragment : Fragment(), MapContract.MapView, OnMapReadyCallback {
    private lateinit var presenter: MapContract.MapPresenter
    private lateinit var mapView: MapView
    private lateinit var symbolManager: SymbolManager
    private lateinit var ctx: Context

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
        mapboxMap.setStyle(Style.MAPBOX_STREETS, object : Style.OnStyleLoaded {
            override fun onStyleLoaded(style: Style) {
                val markerDrawable = ContextCompat.getDrawable(ctx, R.drawable.ic_place_red_24dp)
                markerDrawable?.let { drawable ->
                    style.addImage(MARKER_ICON, drawable)
                } ?: Log.e(this.javaClass.simpleName, "failed to load marker resource")
                symbolManager = SymbolManager(mapView, mapboxMap, style)
                presenter.loadLocations()
            }
        })
    }

    override fun renderMarkers(coordinates: List<LatLng>) {
        for (coordinate in coordinates) {
            val symbolOptions = SymbolOptions()
                .withLatLng(coordinate)
                .withIconImage(MARKER_ICON)
            symbolManager.create(symbolOptions)
        }
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
        const val MAPBOX_KEY = "pk.eyJ1IjoidG9tbGluc29uNjMxIiwiYSI6ImNqeGR5aGtseTBqZHIzeW13bnV6ZXFtdWgifQ.phJvUqBz9mgl2GVz8o2zuA"
        const val MARKER_ICON = "marker_icon"
    }
}