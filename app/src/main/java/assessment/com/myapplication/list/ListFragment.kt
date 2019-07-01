package assessment.com.myapplication.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import assessment.com.myapplication.R
import assessment.com.myapplication.data.Location
import assessment.com.myapplication.list.adapter.ListAdapter
import assessment.com.myapplication.map.MapFragment

class ListFragment : Fragment(), ListContract.ListView {
    private var listAdapter: ListAdapter? = null

    private lateinit var ctx: Context
    private lateinit var presenter: ListContract.ListPresenter
    private lateinit var listRecycler: RecyclerView
    private lateinit var listManager: RecyclerView.LayoutManager
    private lateinit var refreshButton: ImageButton
    private lateinit var spinner: ProgressBar
    private lateinit var viewAll: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = context ?: return
        presenter = ListPresenter(this)
        presenter.viewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        listRecycler = view.findViewById(R.id.list_recycler)
        refreshButton = view.findViewById(R.id.refresh_button)
        refreshButton.setOnClickListener { presenter.explicitRefresh() }
        spinner = view.findViewById(R.id.loading_spinner)
        viewAll = view.findViewById(R.id.view_all)
        viewAll.setOnClickListener { onListingClicked(null) }

        // Request location data after reference to recyclerView and other UI is established
        presenter.loadLocations()
        return view
    }

    // Populates the recyclerview with listings and designates a click action
    override fun renderListings(locations: List<Location>) {
        listAdapter = ListAdapter(locations.toMutableList(), { id -> onListingClicked(id) })
        listManager = LinearLayoutManager(ctx)
        listRecycler.apply {
            layoutManager = listManager
            adapter = listAdapter
        }
    }

    override fun clearListingsAndShowSpinner() {
        listAdapter?.clear()
        spinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        spinner.visibility = View.GONE
    }

    // On list click, open the map and let it know which location to focus camera on
    private fun onListingClicked(id: Int?) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, MapFragment.newInstance(id))
            ?.addToBackStack(null)
            ?.commit()
    }

    override fun provideContext() = ctx
}