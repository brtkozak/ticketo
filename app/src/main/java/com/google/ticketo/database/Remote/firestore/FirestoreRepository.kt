package com.google.ticketo.database.Remote.firestore

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.ticketo.model.*
import com.google.ticketo.model.Responses.eventResponse.EventResponse
import io.reactivex.Single
import java.util.*

//SINGLETON
class FirestoreRepository {

    private val database = FirebaseFirestore.getInstance()
    val fireAuth = FirebaseAuth.getInstance()

    companion object {
        private var instance: FirestoreRepository? = null

        @Synchronized
        fun getInstance(): FirestoreRepository {
            if (instance == null)
                instance =
                    FirestoreRepository()
            return instance as FirestoreRepository
        }
    }

    fun getCurrentUser(): Single<User> =
        database
            .collection("users")
            .document(fireAuth.uid!!)
            .single()
            .map {
                it.toObject(User::class.java)!!
            }

    fun getAllEvents(): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun getEvent(id: String): Single<Pair<EventDto?, Location?>> =
        database.collection("events")
            .whereEqualTo("id", id)
            .single()
            .map { it ->
                DtoConverter.querySnapshotToEvent(it)
            }

    fun getEventsByCity(city: String): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .whereEqualTo("location.city", city)
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }

    fun discoverEvents(city: String, startDate: Date, endDate: Date) : Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }.map {
                DtoConverter.removeUnwantedEvents(city, startDate, endDate, it)
            }

    fun getEventsByDates(
        startDate: Date,
        endDate: Date
    ): Single<Pair<List<EventDto>, List<Location>>> =
        database.collection("events")
            .whereGreaterThanOrEqualTo("startDate", startDate)
            .whereLessThanOrEqualTo("startDate", endDate)
            .single()
            .map {
                DtoConverter.querySnapshotToEventsList(it)
            }


    fun getBuyersCount(eventId: String): Single<Int> =
        database.collection("events")
            .document(eventId)
            .collection("buyers")
            .single()
            .map {
                it.size()
            }

    fun getSellersCount(eventId: String): Single<Int> =
        database.collection("events")
            .document(eventId)
            .collection("sellers")
            .single()
            .map {
                it.size()
            }

    fun getGroup(eventId: String, group: String): LiveData<List<User>> =
        UserListLiveData(
            database.collection("events")
                .document(eventId)
                .collection(group)
        )

    fun getComments(eventId : String) : LiveData<List<Comment>> =
        CommentListLiveData(
            database.collection("events")
                .document(eventId)
                .collection("comments")
        )

    fun getUser(userId: String): LiveData<User> =
        UserLiveData(
            database.collection("users")
                .document(userId)
        )

    fun getReviews(userId: String, reviewType: String): LiveData<List<Review>> =
        ReviewListLiveData(
            database.collection("users")
                .document(userId)
                .collection(reviewType)
        )

    fun addReview(userId: String, reviewType: String) : Single<Boolean> =
        database
            .collection("users")
            .document(userId)
            .collection(reviewType)
            .document(fireAuth.uid!!).set(Review(fireAuth.uid!!))
            .single()

    fun removeReview(userId: String, reviewType: String) : Single<Boolean> =
        database
            .collection("users")
            .document(userId)
            .collection(reviewType)
            .document(fireAuth.uid!!)
            .delete()
            .single()

    fun deleteComment(eventId: String, commentId : String) : Single<Boolean> =
        database
            .collection("events")
            .document(eventId)
            .collection("comments")
            .document(commentId)
            .delete()
            .single()

    fun addToGroup(user: User, eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(user.firebaseId!!).set(user)
            .single()

    fun removeFromGroup(eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(fireAuth.uid!!)
            .delete()
            .single()

    fun checkIfUserInGroup(eventId: String, group: String): Single<Boolean> =
        database.collection("events")
            .document(eventId)
            .collection(group)
            .document(fireAuth.uid!!)
            .single()
            .map {
                it.exists()
            }

    fun getSearchByName(search: String): Single<List<Pair<String, String>>> {
        return database.collection("events")
            .orderBy("name")
            .startAt(search.capitalize())
            .endAt(search.capitalize() + "\uf8ff")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsNameList(it)
            }
    }

    fun getSearchByLocation(search: String): Single<List<Pair<String, String>>> {
        return database.collection("events")
            .orderBy("location.city")
            .startAt(search.capitalize())
            .endAt(search.capitalize() + "\uf8ff")
            .single()
            .map {
                DtoConverter.querySnapshotToEventsLocationList(it)
            }
    }

    fun sendComment(comment : Comment, eventId : String) : Single<Boolean> =
        database
            .collection("events")
            .document(eventId)
            .collection("comments")
            .add(comment)
            .single()

    fun insertEvent() {
        val events = mutableListOf<EventResponse>()

//        val event1 = EventResponse(
//            "442909573185318",
//            "Dawid Podsiadło i Taco Hemingway na PGE Narodowym",
//            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/61306347_2479059405462027_4471323510781771776_o.jpg?_nc_cat=102&_nc_oc=AQkwpSSJsxkMDlseCZ1z6VqK1JAiba3mgSUfs09ngplhNp8-__x8ZBzg6hqGujzOejc&_nc_ht=scontent-waw1-1.xx&oh=a80cd940d33db06933ba636fc88678a7&oe=5E008A1A",
//            Date(1569690000),
//            Date(1569706200),
//            Location("PGE Narodowy", "Warszawa", "al. Ks. J. Poniatowskiego 1", "03-901"),
//            "PLN",
//            49.0,
//            399.0
//        )



        val event1 = EventResponse(
            "542909573185318",
            "Daria Zawiałow | Helsinki Tour vol2",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/78309556_2644188482309128_88396147848118272_o.jpg?_nc_cat=104&_nc_ohc=9OtqI2F-VaYAQl3Utitw3A7rN2qHHlS9obno-Ag3y1cHF08jq-WI2pLVA&_nc_ht=scontent-waw1-1.xx&oh=7b5ff758156c8454722dfe7d851917b9&oe=5E6BF725",
            Date(1572462000),
            Date(1572472800),
            Location("A2 - Centrum Koncertowe", "Wrocław", "Góralska 5", "53-610"),
            "PLN",
            0.0,
            0.0
        )

        val event2 = EventResponse(
            "542909573185319",
            "Krzysztof Krawczyk | Warszawa",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-0/p180x540/66597675_10158527253489128_2053576344146542592_o.jpg?_nc_cat=101&_nc_ohc=DZrBj84-9MMAQlTQJNXpUCJh5YnD5bHSXWGAXv1EbzJKOp8BwoSI1NohA&_nc_ht=scontent-waw1-1.xx&oh=b1c8e81b39c5b16384a15c92e5b760c6&oe=5E874562",
            Date(1572462000),
            Date(1572472800),
            Location("Hulakula Rozrywkowe Centrum Miasta", "Warszawa", "Jagiellońska 82b", "03-301"),
            "PLN",
            0.0,
            0.0
        )

        val event3 = EventResponse(
            "542909573185320",
            "Kwiat Jabłoni",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/73352276_780521212376861_1072009032329330688_o.jpg?_nc_cat=106&_nc_ohc=CMEfAE_BJA0AQlU470sTAGwA0lKodGwyPnvM-DVjpOjWvKmrA2VGKYDOQ&_nc_ht=scontent-waw1-1.xx&oh=2a87ff7b86db432b10dd5f0fe0481d2e&oe=5E7F0F07",
            Date(1572462000),
            Date(1572472800),
            Location("Luneta", "Kraków", "Kamienna 16", "31-403"),
            "PLN",
            0.0,
            0.0
        )

        val event4 = EventResponse(
            "542909573185321",
            "Edyta Górniak - Akustycznie",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/52355795_2202413796504978_3227750875942354944_o.jpg?_nc_cat=107&_nc_ohc=rYqhAS83ALcAQnqr5LjUjvRY20Cvf9AWUqO8cE4POClwcTF4_nDhy0zrA&_nc_ht=scontent-waw1-1.xx&oh=23fa68eb287bf9dcb1137df4605c9f20&oe=5E734BD6",
            Date(1572462000),
            Date(1572472800),
            Location("Polska Filharmonia Bałtycka", "Gdańsk", "Ołowianka 1", "80-751"),
            "PLN",
            0.0,
            0.0
        )

        val event5 = EventResponse(
            "542909573185322",
            "Kukon Radio Taxi tour",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/71798869_2367030686878984_6985240771905454080_n.jpg?_nc_cat=109&_nc_ohc=QsY5MgeXjAYAQmtkuJAVJpXeQfyIDrX_r9TpQeQVLVUiPxnH4-44hGMqg&_nc_ht=scontent-waw1-1.xx&oh=cf1e036f5aeaf88cdf34d50dd5c369ad&oe=5EB431B9",
            Date(1572462000),
            Date(1572472800),
            Location("Magazyn", "Katowice", "Mariacka 18", "40-014"),
            "PLN",
            0.0,
            0.0
        )

        val event6 = EventResponse(
            "542909573185323",
            "Lady Pank - LP1",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/74591909_10156320504821883_7247352004526211072_o.jpg?_nc_cat=105&_nc_ohc=NizfIXe7IXAAQm-IG7tBFH1_S7CjY5Q8OhLNNK1EbiaEv2lStr8PvGoVg&_nc_ht=scontent-waw1-1.xx&oh=64bd8e1f4975279bab390bc723289af9&oe=5E70C37C",
            Date(1572462000),
            Date(1572472800),
            Location("Klub Stodoła", "Warszawa", "Batorego 10", "02-591"),
            "PLN",
            0.0,
            0.0
        )

        events.addAll(listOf(event1,event2,event3,event4,event5,event6))

        events.forEach {
            database.collection("events").document(it.id).set(it)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                }
        }
    }
}