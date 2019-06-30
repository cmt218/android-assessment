package assessment.com.myapplication.map

import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.geometry.LatLng

class MapPresenter constructor(private val view: MapContract.MapView): MapContract.MapPresenter {
    private val model: MapContract.MapModel = MapModel(this)

    override fun loadLocations() {
        model.fetchLocations()
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        view.renderMarkers(locations)
    }

    override fun loadSingleLocation(id: Int) {
        model.fetchSingleLocation(id)
    }

    override fun onSingleLocationLoaded(location: Location) {
        view.zoomToLocation(LatLng(location.latitude, location.longitude))
    }

    override fun provideContext() = view.provideContext()

    override fun viewCreated() {
        model.viewCreated()
    }
}