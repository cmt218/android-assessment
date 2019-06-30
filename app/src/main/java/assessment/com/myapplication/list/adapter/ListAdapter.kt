package assessment.com.myapplication.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import assessment.com.myapplication.R
import assessment.com.myapplication.data.Location

class ListAdapter(private val locations: List<Location>, private val onListingClicked: (Int) -> Unit) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
        val name = listView.findViewById(R.id.name) as? TextView
        val description = listView.findViewById(R.id.description) as? TextView
        val container = listView.findViewById(R.id.container) as? ConstraintLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = locations.get(position)
        holder.name?.text = item.name
        holder.description?.text = item.description
        holder.container?.setOnClickListener { onListingClicked(item.id) }
    }

    override fun getItemCount() = locations.size
}