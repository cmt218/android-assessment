package assessment.com.myapplication.map

import android.content.Context
import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.geometry.LatLng

interface MapContract {

    interface MapView {
        fun renderMarkers(coordinates: List<LatLng>)
        fun provideContext(): Context
    }

    interface MapPresenter {
        fun loadLocations()
        fun onLocationsLoaded(locations: List<Location>)
        fun provideContext(): Context
        fun viewCreated()
    }

    interface MapModel {
        fun fetchLocations()
        fun viewCreated()
    }
}