import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.davidm.satisbeer.TestAppComponent
import com.davidm.satisbeer.TestSatisBeerApp
import com.davidm.satisbeer.featurehome.R
import com.davidm.satisbeer.featurehome.view.HomeActivity
import com.davidm.satisbeer.featurehome.view.ListItemViewHolder
import com.davidm.satisbeer.featurehome.view.ListViewAction
import com.davidm.satisbeer.testdispatchers.TestDispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Inject
    lateinit var dispatchers: TestDispatchers

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {

        (TestSatisBeerApp.instance.appComponent as TestAppComponent).inject(this)

        IdlingRegistry.getInstance().register(dispatchers.cpu.counter)
        IdlingRegistry.getInstance().register(dispatchers.main.counter)
        IdlingRegistry.getInstance().register(dispatchers.io.counter)

    }

    @Test
    fun homeActivityTest() {
        onView(withId(R.id.beerList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ListItemViewHolder>(
                0,
                ListViewAction().clickChildViewWithId(R.id.beerMoreInfoButton)
            )
        )
    }
}
