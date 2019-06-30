package assessment.com.myapplication.list

import android.content.Context
import assessment.com.myapplication.data.Location

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