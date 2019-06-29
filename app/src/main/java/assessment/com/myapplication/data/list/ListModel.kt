package assessment.com.myapplication.data.list

import androidx.room.Room
import assessment.com.myapplication.data.GTService
import assessment.com.myapplication.data.room.LocationDatabase
import assessment.com.myapplication.map.MapModel
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
                Room.databaseBuilder(context, LocationDatabase::class.java, MapModel.DATABASE_NAME)
                    .build()
            retrofit = Retrofit.Builder()
                .baseUrl(MapModel.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            service = retrofit.create(GTService::class.java)
        }
    }

    override fun fetchLocations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}