package assessment.com.myapplication.list

import android.content.Context
import assessment.com.myapplication.data.Location

interface ListContract {
    interface ListView {
        fun clearListingsAndShowSpinner()
        fun hideSpinner()
        fun renderListings(locations: List<Location>)
        fun provideContext(): Context
    }

    interface ListPresenter {
        fun explicitRefresh()
        fun loadLocations()
        fun onLocationsLoaded(locations: List<Location>)
        fun provideContext(): Context
        fun viewCreated()
    }

    interface ListModel {
        fun explicitRefresh()
        fun fetchLocations()
        fun viewCreated()
    }
}