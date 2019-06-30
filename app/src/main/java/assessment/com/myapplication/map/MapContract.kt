package assessment.com.myapplication.map

import android.content.Context
import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.geometry.LatLng

interface MapContract {
    interface MapView {
        fun renderMarkers(locations: List<Location>)
        fun zoomToLocation(coordinate: LatLng)
        fun provideContext(): Context
    }

    interface MapPresenter {
        fun loadLocations()
        fun loadSingleLocation(id: Int)
        fun onLocationsLoaded(locations: List<Location>)
        fun onSingleLocationLoaded(location: Location)
        fun provideContext(): Context
        fun viewCreated()
    }

    interface MapModel {
        fun fetchLocations()
        fun fetchSingleLocation(id: Int)
        fun viewCreated()
    }
}