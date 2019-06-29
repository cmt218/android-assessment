package assessment.com.myapplication.data.list

import android.content.Context
import android.location.Location

class ListPresenter constructor(private val view: ListContract.ListView) : ListContract.ListPresenter {
    private val model: ListContract.ListModel = ListModel(this)

    override fun loadLocations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun provideContext(): Context = view.provideContext()

    override fun viewCreated() {
        model.viewCreated()
    }
}