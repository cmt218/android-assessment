package assessment.com.myapplication.map

import android.os.AsyncTask
import android.util.Log
import androidx.room.Room
import assessment.com.myapplication.R
import assessment.com.myapplication.data.GTService
import assessment.com.myapplication.data.Location
import assessment.com.myapplication.data.room.LocationDatabase
import assessment.com.myapplication.data.room.RoomLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class MapModel constructor(private val presenter: MapContract.MapPresenter) : MapContract.MapModel {

    private lateinit var database: LocationDatabase
    private lateinit var retrofit: Retrofit
    private lateinit var service: GTService
    private val executor = Executors.newSingleThreadExecutor()

    override fun viewCreated() {
        presenter.provideContext().let { context ->
            database =
                Room.databaseBuilder(context, LocationDatabase::class.java, context.getString(R.string.database_name))
                    .build()
            retrofit = Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(GTService::class.java)
        }
    }

    override fun fetchLocations() {

        val db = LocationsTask().execute(database).get()
        if(db.isNotEmpty()) {
            presenter.onLocationsLoaded(db)
        } else {
            fetchLocationsRemoteAndStore()
        }

    }

    private fun fetchLocationsRemoteAndStore() {
        service.getLocations().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                response.body()?.let {
                    executor.execute {
                        database.locationDao().insertLocations(it.map { location ->
                            RoomLocation().apply {
                                id = location.id
                                name = location.name
                                latitude = location.latitude
                                longitude = location.longitude
                                description = location.description
                            }
                        })
                    }
                    presenter.onLocationsLoaded(it)
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.localizedMessage)
            }
        })
    }

    private class LocationsTask : AsyncTask<LocationDatabase, Unit, List<Location>>() {
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
}