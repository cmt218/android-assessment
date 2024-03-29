package assessment.com.myapplication.data.room

import android.os.AsyncTask
import assessment.com.myapplication.data.Location

// Task allowing for the retrieval of all locally stored locations on a background thread
class LocationsTask constructor(private val database: LocationDatabase) : AsyncTask<Unit, Unit, List<Location>?>() {
    override fun doInBackground(vararg p0: Unit?): List<Location>? =
        database.locationDao().all?.map { roomLocation ->
            Location(
                roomLocation.id,
                roomLocation.name,
                roomLocation.latitude,
                roomLocation.longitude,
                roomLocation.description
            )
        }
}