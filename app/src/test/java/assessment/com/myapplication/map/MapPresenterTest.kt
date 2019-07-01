package assessment.com.myapplication.map

import assessment.com.myapplication.data.Location
import com.mapbox.mapboxsdk.geometry.LatLng
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class MapPresenterTest {
    val view: MapContract.MapView = mock()
    val presenter = MapPresenter(view)
    val model: MapContract.MapModel = mock()

    @Before
    fun initialize() {
        presenter.setModel(model)
    }

    @Test
    fun testLoadLocations() {
        presenter.loadLocations()
        verify(model).fetchLocations()
    }

    @Test
    fun testOnLocationsLoaded() {
        val locations = listOf(
            Location(
                0,
                "test location",
                0.0,
                0.0,
                "test description"
            )
        )
        presenter.onLocationsLoaded(locations)
        verify(view).renderMarkers(locations)
    }

    @Test
    fun testLoadSingleLocation() {
        presenter.loadSingleLocation(0)
        verify(model).fetchSingleLocation(0)
    }

    @Test
    fun testOnSingleLocationLoaded() {
        val location = Location(
            0,
            "test location",
            0.0,
            0.0,
            "test description"
        )
        presenter.onSingleLocationLoaded(location)
        verify(view).zoomToLocation(LatLng(location.latitude, location.longitude))
    }

    @Test
    fun testProvideContext() {
        presenter.provideContext()
        verify(view).provideContext()
    }

    @Test
    fun testViewCreated() {
        presenter.viewCreated()
        verify(model).viewCreated()
    }
}