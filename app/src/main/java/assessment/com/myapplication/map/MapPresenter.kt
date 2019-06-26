package assessment.com.myapplication.map

import assessment.com.myapplication.data.Location

class MapPresenter constructor(private val view: MapContract.MapView): MapContract.MapPresenter {
    private val model: MapContract.MapModel = MapModel(this)

    override fun loadLocations() {
        model.fetchLocations()
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        val result = locations
    }
}