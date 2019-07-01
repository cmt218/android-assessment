package assessment.com.myapplication.map

import androidx.room.Room
import assessment.com.myapplication.data.room.LocationDatabase
import assessment.com.myapplication.data.room.LocationTask
import assessment.com.myapplication.data.room.LocationsTask

class MapModel constructor(private val presenter: MapContract.MapPresenter) : MapContract.MapModel {
    private lateinit var database: LocationDatabase

    override fun viewCreated() {
        presenter.provideContext().let { context ->
            database =
                Room.databaseBuilder(context, LocationDatabase::class.java, DATABASE_NAME)
                    .build()
        }
    }

    // Get data from local storage (should already have data via list page - no need to hit endpoint)
    override fun fetchLocations() {
        val dbListings = LocationsTask(database).execute().get() ?: mutableListOf()
        presenter.onLocationsLoaded(dbListings)
    }

    // Fetch single location data if fragment was initialized with it
    override fun fetchSingleLocation(id: Int) {
        val dbListing = LocationTask(database, id).execute().get()
        presenter.onSingleLocationLoaded(dbListing)
    }

    companion object {
        const val DATABASE_NAME = "location-database"
    }
}