package assessment.com.myapplication.data.list

import android.content.Context
import android.location.Location

interface ListContract {
    interface ListView {
        fun renderListings(locations: List<Location>)
        fun provideContext(): Context
    }

    interface ListPresenter {
        fun loadLocations()
        fun onLocationsLoaded(locations: List<Location>)
        fun provideContext(): Context
        fun viewCreated()
    }

    interface ListModel {
        fun fetchLocations()
        fun viewCreated()
    }
}