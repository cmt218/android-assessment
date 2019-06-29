package assessment.com.myapplication.data.list

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import assessment.com.myapplication.R

class ListFragment : Fragment(), ListContract.ListView {
    private lateinit var presenter: ListContract.ListPresenter
    private lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = context ?: return
        presenter = ListPresenter(this)
        presenter.viewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        // find recyclerview??
        return view
    }

    override fun renderListings(locations: List<Location>) {
        // Needa add recyclerview to this layout and populate it
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun provideContext() = ctx
}