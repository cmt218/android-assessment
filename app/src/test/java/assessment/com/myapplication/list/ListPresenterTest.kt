package assessment.com.myapplication.list

import assessment.com.myapplication.data.Location
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

class ListPresenterTest {
    val view: ListContract.ListView = mock()
    val presenter = ListPresenter(view)
    val model: ListContract.ListModel = mock()

    @Before
    fun initialize() {
        presenter.setModel(model)
    }

    @Test
    fun testExplicitRefresh() {
        presenter.explicitRefresh()
        verify(view).clearListingsAndShowSpinner()
        verify(model).explicitRefresh()
    }

    @Test
    fun testLoadLocations() {
        presenter.loadLocations()
        verify(view).clearListingsAndShowSpinner()
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
        verify(view).hideSpinner()
        verify(view).renderListings(locations)
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