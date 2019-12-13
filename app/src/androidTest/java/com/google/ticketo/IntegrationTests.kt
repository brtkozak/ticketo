package com.google.ticketo

import android.content.Context
import android.content.res.Resources
import android.os.SystemClock
import android.util.EventLogTags
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import com.google.ticketo.database.Local.Daos.EventDao
import com.google.ticketo.database.Local.Daos.EventIntentsDao
import com.google.ticketo.database.Local.LocalDatabase
import com.google.ticketo.model.EventDto
import com.google.ticketo.ui.dashboard.EventAdapter
import com.google.ticketo.ui.event_details.users.users_list.UserAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import androidx.test.espresso.action.ViewActions.typeText
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.ticketo.ui.search.ResultAdapter
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4::class)
class IntegrationTests{

    private lateinit var db: LocalDatabase
    private lateinit var eventIntentsDao: EventIntentsDao
    private lateinit var eventDao: EventDao

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
        eventIntentsDao = db.eventIntentsDao()
        eventDao=db.eventDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close( )
    }

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    @Test
    fun addEventToFavourite(){
        onView(withId(R.id.dashboard_fragment_events_this_weekend)).perform(actionOnItemAtPosition<EventAdapter.EventHolder>(0, click()))
        SystemClock.sleep(1500)
        onView(withId(R.id.event_details_favourite)).perform(click())
        SystemClock.sleep(1500)
        val favouritesCount = eventIntentsDao.getFavouritesCount()
        assert(favouritesCount > 0)
    }

    @Test
    fun addBuyOffer(){
        onView(withId(R.id.dashboard_fragment_discover_events)).perform(actionOnItemAtPosition<EventAdapter.EventHolder>(0, click()))
        SystemClock.sleep(1500)
        onView(withId(R.id.event_details_buy)).perform(click())
        SystemClock.sleep(1500)
        val butOffers = eventIntentsDao.getBuyOffersCount()
        assert(butOffers > 0)
    }

    @Test
    fun addSellOffer(){
        onView(withId(R.id.dashboard_fragment_events_in_city)).perform(actionOnItemAtPosition<EventAdapter.EventHolder>(0, click()))
        SystemClock.sleep(1500)
        onView(withId(R.id.event_details_sell)).perform(click())
        SystemClock.sleep(1500)
        val sellOffers = eventIntentsDao.getSellOffersCout()
        assert(sellOffers > 0)
    }

    @Test
    fun checkDatabaseEventInsert(){
        eventDao.insertEvent(EventDto(id = "test"))
        val event = eventDao.getEventDtoById("test")
        assert(event != null)
    }

    @Test
    fun removeBuyIntentViaProfile(){
        val city = "Poznań"
        onView(withId(R.id.dashboard_fragment_search_cardview)).perform(click())
        SystemClock.sleep(1500)
        onView(isAssignableFrom(EditText::class.java)).perform(typeText(city))
        SystemClock.sleep(2000)
        onView(withId(R.id.search_result)).perform(actionOnItemAtPosition<ResultAdapter.ResultHolder>(0, click()))
        SystemClock.sleep(1500)
        val titleView =  onView(withId(R.id.events_by_city_toolbar_text))
        val titleValue =  getText(titleView)
        assert(titleValue == Resources.getSystem().getString(R.string.events_in_city) + " " + city)
    }

    fun getText(matcher: ViewInteraction): String {
        var text = String()
        matcher.perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "Text of the view"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                text = tv.text.toString()
            }
        })

        return text
    }
}
