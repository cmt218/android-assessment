package assessment.com.myapplication.data

import retrofit2.Call
import retrofit2.http.GET

interface GTService {
    @GET("get_map_pins.php")
    fun getLocations(): Call<List<Location>>
}