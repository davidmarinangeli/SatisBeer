import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.davidm.satisbeer.featurehome.R
import com.davidm.satisbeer.featurehome.view.HomeActivity
import com.davidm.satisbeer.featurehome.view.ListItemViewHolder
import com.davidm.satisbeer.featurehome.view.ListViewAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityBaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun homeActivityTest() {

        onView(withId(R.id.beerList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ListItemViewHolder>(
                1,
                ListViewAction().clickChildViewWithId(R.id.beerMoreInfoButton)
            )
        )
    }
}
