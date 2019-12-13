package com.google.ticketo

import com.google.ticketo.database.Local.Daos.Converters.DateConverter
import com.google.ticketo.database.Remote.facebook.FacebookRepository
import com.google.ticketo.database.Remote.firestore.FirestoreRepository
import com.google.ticketo.database.Repository
import com.google.ticketo.model.DtoConverter
import com.google.ticketo.model.EventDto
import com.google.ticketo.model.Location
import com.google.ticketo.model.Responses.eventResponse.EventResponse
import org.junit.Test

import org.junit.Assert.*
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun subtractMinutesFromDateTest (){
        val dateNow = Date()
        val minutesDifference = 5
        val dateLimit= Repository.getUpdateTime(dateNow, minutesDifference)
        var dateDifference = dateNow.time - dateLimit.time
        dateDifference=TimeUnit.MINUTES.convert(dateDifference, TimeUnit.MILLISECONDS)
        assertEquals(5, dateDifference)
    }

    @Test
    fun removeUnwantedEventsTest(){
        val cityToIgnore = "Wrocław"
        val eventDto = EventDto()
        val location = Location(city = cityToIgnore)
        val pair = Pair(mutableListOf(eventDto), mutableListOf(location))
        pair.apply {
            DtoConverter.removeUnwantedEvents(cityToIgnore, Date(), Date(), this)
        }
        assertEquals(0, pair.first.size)
    }

    @Test
    fun timestampToDateConverterText(){
        val timestamp : Long = 1572253200
        val dateConverter = DateConverter()
        val convertedDate = dateConverter.toDate(timestamp)
        val date = Date(timestamp)
        assertEquals(date, convertedDate)
    }

    @Test
    fun closestDayTest(){
        val closestSaturday = Repository.getClosestDay(DayOfWeek.FRIDAY)
        val dateNow = Date()
        val dateDiffInMilliseconds = closestSaturday.time - dateNow.time
        assert(TimeUnit.DAYS.convert(dateDiffInMilliseconds, TimeUnit.MILLISECONDS).toInt() == 0)
    }

    @Test
    fun convertEventResponseToLocationTest(){
        val eventResponse = EventResponse(location = Location(locationName = "test") )
        val location = DtoConverter.eventResponseToLocation(eventResponse)
        assert(location is Location && location.locationName == "test")
    }
}
