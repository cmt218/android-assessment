package assessment.com.myapplication.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import assessment.com.myapplication.R


class MapFragment : Fragment(), MapContract.MapView {
    private lateinit var presenter: MapContract.MapPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter = MapPresenter(this)
        presenter.loadLocations()
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
}