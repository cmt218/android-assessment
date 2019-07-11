package assessment.com.myapplication.add

import android.content.Context

class AddPresenter constructor(private val view: AddContract.AddView) : AddContract.AddPresenter {
    private var model: AddContract.AddModel = AddModel(this)

    override fun onAddClicked(name: String, description: String, latitude: Double, longitude: Double) {
        model.addLocation(name, description, latitude, longitude)
    }

    override fun viewCreated() {
        model.viewCreated()
    }

    override fun provideContext() = view.provideContext()
}