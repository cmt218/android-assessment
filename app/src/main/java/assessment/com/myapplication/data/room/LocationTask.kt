package assessment.com.myapplication.data.room

import android.os.AsyncTask
import assessment.com.myapplication.data.Location

// Task allowing for the retrieval of one locally stored location on a background thread
class LocationTask constructor(private val database: LocationDatabase, private val id: Int)
    : AsyncTask<Unit, Unit, Location>() {
    override fun doInBackground(vararg p0: Unit?): Location {
        val roomLocation = database.locationDao().getSingle(id)
        return roomLocation.run {
            Location(id, name, latitude, longitude, description)
        }
    }
}