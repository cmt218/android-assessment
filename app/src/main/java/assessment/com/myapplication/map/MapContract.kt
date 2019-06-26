package assessment.com.myapplication.map

import assessment.com.myapplication.data.Location

interface MapContract {

    interface MapView {

    }

    interface MapPresenter {
        fun loadLocations()
        fun onLocationsLoaded(locations: List<Location>)
    }

    interface MapModel {
        fun fetchLocations()
    }
}