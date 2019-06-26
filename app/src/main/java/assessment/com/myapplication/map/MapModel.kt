package assessment.com.myapplication.map

import android.util.Log
import assessment.com.myapplication.data.GTService
import assessment.com.myapplication.data.Location
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapModel constructor(private val presenter: MapContract.MapPresenter) : MapContract.MapModel {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://annetog.gotenna.com/development/scripts/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: GTService = retrofit.create(GTService::class.java)

    override fun fetchLocations() {
        service.getLocations().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                response.body()?.let {
                    presenter.onLocationsLoaded(it)
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                Log.d(this.javaClass.canonicalName, t.localizedMessage)
            }
        })
    }
}