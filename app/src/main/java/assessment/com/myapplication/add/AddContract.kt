package assessment.com.myapplication.add

import android.content.Context

interface AddContract {
    interface AddView {
        fun provideContext(): Context
    }

    interface AddPresenter {
        fun onAddClicked(name: String, description: String, latitude: Double, longitude: Double)
        fun viewCreated()
        fun provideContext(): Context
    }

    interface AddModel {
        fun addLocation(name: String, description: String, latitude: Double, longitude: Double)
        fun viewCreated()
    }
}