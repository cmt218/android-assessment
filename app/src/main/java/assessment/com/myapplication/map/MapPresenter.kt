package assessment.com.myapplication.map

import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.geometry.LatLng

class MapPresenter constructor(private val view: MapContract.MapView): MapContract.MapPresenter {
    private val model: MapContract.MapModel = MapModel(this)

    override fun loadLocations() {
        model.fetchLocations()
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        val coordinates = locations.map { location -> LatLng(location.latitude, location.longitude) }
        view.renderMarkers(coordinates)
    }

    override fun provideContext() = view.provideContext()

    override fun viewCreated() {
        model.viewCreated()
    }
}