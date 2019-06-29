package assessment.com.myapplication.data.room

import android.os.AsyncTask
import assessment.com.myapplication.data.Location

class LocationsTask : AsyncTask<LocationDatabase, Unit, List<Location>>() {
    override fun doInBackground(vararg database: LocationDatabase?): List<Location> =
        database.get(0)?.locationDao()?.all?.map { roomLocation ->
            Location(
                roomLocation.id,
                roomLocation.name,
                roomLocation.latitude,
                roomLocation.longitude,
                roomLocation.description
            )
        } ?: mutableListOf()
}