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

    override fun viewCreated() {
        presenter.provideContext().let { context ->
            database =
                Room.databaseBuilder(context, LocationDatabase::class.java, DATABASE_NAME)
                    .build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(GTService::class.java)
        }
    }

    override fun explicitRefresh() {
        fetchLocationsRemoteAndStore()
    }

    override fun fetchLocations() {
        val dbListings = LocationsTask(database).execute().get() ?: mutableListOf()
        if(dbListings.isNotEmpty()) {
            presenter.onLocationsLoaded(dbListings)
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

    companion object {
        const val BASE_URL = "https://annetog.gotenna.com/development/scripts/"
        const val DATABASE_NAME = "location-database"
    }
}