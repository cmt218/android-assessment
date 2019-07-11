package assessment.com.myapplication.add

import androidx.room.Room
import assessment.com.myapplication.data.room.LocationDatabase
import assessment.com.myapplication.data.room.RoomLocation
import java.util.*
import java.util.concurrent.Executors

class AddModel constructor(private val presenter: AddContract.AddPresenter) : AddContract.AddModel {
    private lateinit var database: LocationDatabase
    private val executor = Executors.newSingleThreadExecutor()

    override fun viewCreated() {
        presenter.provideContext().let { context ->
            database =
                Room.databaseBuilder(context, LocationDatabase::class.java, DATABASE_NAME)
                    .build()
        }
    }

    override fun addLocation(name: String, description: String, latitude: Double, longitude: Double) {
        val toInsert = RoomLocation().apply {
            this.id = Calendar.getInstance().time.time.toInt()
            this.name = name
            this.description = description
            this.latitude = latitude
            this.longitude = longitude
        }
        executor.execute {
            database.locationDao().insertLocation(toInsert)
        }
    }

    companion object {
        const val DATABASE_NAME = "location-database"
    }
}