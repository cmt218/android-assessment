package assessment.com.myapplication.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import assessment.com.myapplication.R

class AddFragment : Fragment(), AddContract.AddView {
    private lateinit var ctx: Context
    private lateinit var presenter: AddContract.AddPresenter
    private lateinit var addAction: TextView
    private lateinit var descriptionEditText: EditText
    private lateinit var latitudeEditText: EditText
    private lateinit var longitudeEditText: EditText
    private lateinit var nameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = context ?: return
        presenter = AddPresenter(this)
        presenter.viewCreated()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        addAction = view.findViewById(R.id.add_action)
        addAction.setOnClickListener { onAddClicked() }
        descriptionEditText = view.findViewById(R.id.description_input)
        latitudeEditText = view.findViewById(R.id.latitude_input)
        longitudeEditText = view.findViewById(R.id.longitude_input)
        nameEditText = view.findViewById(R.id.name_input)

        return view
    }

    private fun onAddClicked() {
        val name = nameEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val latitude = latitudeEditText.text.toString().toDouble()
        val longitude = longitudeEditText.text.toString().toDouble()

        presenter.onAddClicked(name, description, latitude, longitude)
        fragmentManager?.popBackStack()
    }

    override fun provideContext() = ctx
}