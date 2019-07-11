package assessment.com.myapplication.list

import android.content.Context
import androidx.annotation.VisibleForTesting
import assessment.com.myapplication.data.Location

class ListPresenter constructor(private val view: ListContract.ListView) : ListContract.ListPresenter {
    private var model: ListContract.ListModel = ListModel(this)

    override fun explicitRefresh() {
        view.clearListingsAndShowSpinner()
        model.explicitRefresh()
    }

    override fun loadLocations() {
        view.clearListingsAndShowSpinner()
        model.fetchLocations()
    }

    override fun onLocationsLoaded(locations: List<Location>) {
        view.hideSpinner()
        locations.sortedBy { it.name }
        view.renderListings(locations)
    }

    override fun provideContext(): Context = view.provideContext()

    override fun viewCreated() {
        model.viewCreated()
    }

    @VisibleForTesting
    fun setModel(model: ListContract.ListModel) {
        this.model = model
    }
}