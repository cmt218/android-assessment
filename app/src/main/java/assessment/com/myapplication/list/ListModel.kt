package assessment.com.myapplication.list

import android.util.Log
import androidx.room.Room
import assessment.com.myapplication.data.GTService
import assessment.com.myapplication.data.Location
import assessment.com.myapplication.data.room.LocationDatabase
import assessment.com.myapplication.data.room.LocationsTask
import assessment.com.myapplication.data.room.RoomLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

class ListModel constructor(private val presenter: ListContract.ListPresenter) : ListContract.ListModel {
    private lateinit var database: LocationDatabase
    private lateinit var retrofit: Retrofit
    private lateinit var service: GTService
    private val executor = Executors.newSingleThreadExecutor()

    // Initialize model members when view is created so they are ready for requests
    override fun viewCreated() {
        presenter.provideContext().let { context ->
            database =
                Room.databaseBuilder(context, LocationDatabase::class.java, DATABASE_NAME)
                    .build()
        }
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(GTService::class.java)
    }

    // Explicitly fetches data from endpoint on user initiated refresh
    override fun explicitRefresh() {
        fetchLocationsRemoteAndStore()
    }

    // Attempt first to fetch data from local storage, if it does not exist then fetch from endpoint
    override fun fetchLocations() {
        val dbListings = LocationsTask(database).execute().get() ?: mutableListOf()
        if (dbListings.isNotEmpty()) {
            presenter.onLocationsLoaded(dbListings)
        } else {
            fetchLocationsRemoteAndStore()
        }
    }

    // Fetches data from endpoint, notify presenter on data loaded, and update local storage
    private fun fetchLocationsRemoteAndStore() {
        service.getLocations().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                response.body()?.let {
                    storeLocations(it)
                    presenter.onLocationsLoaded(it)
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                Log.e(this.javaClass.simpleName, t.localizedMessage)
            }
        })
    }

    // Stores list of locations to local storage on background thread
    private fun storeLocations(locations: List<Location>) {
        executor.execute {
            database.locationDao().insertLocations(locations.map { location ->
                RoomLocation().apply {
                    id = location.id
                    name = location.name
                    latitude = location.latitude
                    longitude = location.longitude
                    description = location.description
                }
            })
        }
    }

    companion object {
        const val BASE_URL = "https://annetog.gotenna.com/development/scripts/"
        const val DATABASE_NAME = "location-database"
    }
}