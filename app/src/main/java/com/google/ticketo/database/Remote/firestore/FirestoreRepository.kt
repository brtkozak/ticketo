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
            "442909573185318",
            "Krzysztof Zalewski Solo Act",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/67969099_2935640893117731_5404934738190270464_n.jpg?_nc_cat=102&_nc_oc=AQlCb2gkumF3eOygJ87Hoqg8SYM5hylJ8_cTOnjcp3cyhDr0fe-S1r8SC5b8SgNugbc&_nc_ht=scontent-waw1-1.xx&oh=028e3be5422e55c81c16e6f280f459b0&oe=5E5BE12D",
            Date(1572462000),
            Date(1572472800),
            Location("Narodowe Forum Muzyki", "Wrocław", "plac Wolności 1", "50-071"),
            "PLN",
            0.0,
            0.0
        )

        val event2 = EventResponse(
            "442909573185319",
            "Paweł Domagała",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/61831638_2277328162506251_9012004386842345472_o.jpg?_nc_cat=110&_nc_oc=AQnbjSFax_EI14OEZGuPAIwNR5hMgeqxpNOHlOn5Y_OCpsb5EjDEIeNQYWnvQnItp8k&_nc_ht=scontent-waw1-1.xx&oh=8940b881c042bd1a3bb67bf618a22671&oe=5E633659",
            Date(1572462000),
            Date(1572472800),
            Location("Teatr muzyczny Captiol", "Wrocław", "Piłsudskiego 67", "50-019"),
            "PLN",
            0.0,
            0.0
        )

        val event3 = EventResponse(
            "442909573185320",
            "Kortez Przedpremierowo",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/69299202_1355961734555691_349316489116909568_o.jpg?_nc_cat=100&_nc_oc=AQkM2hm_7bXMLe7MGOIEt8QLg4VVaHawW6-xPNv1QUXpbk_AaVvJFUPrY-KKpHwiMgw&_nc_ht=scontent-waw1-1.xx&oh=bea0f5d3ae289ad60d0c35fc36fce975&oe=5E571FF3",
            Date(1572462000),
            Date(1572472800),
            Location("Opera Wrocławska", "Wrocław", "Świdnicka 35", "50-066"),
            "PLN",
            0.0,
            0.0
        )

        val event4 = EventResponse(
            "442909573185321",
            "Tymon Tymański & 3/4",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/70577341_1818148374998092_5760837370677035008_n.jpg?_nc_cat=110&_nc_oc=AQkf21xlk63T7nDz_uYkOstVkgVPtFiTzMG6bmVrwtCt4EXL9dsDfEyxj6FLPgm4hm4&_nc_ht=scontent-waw1-1.xx&oh=dd71618184fead3f47c68323c054c24a&oe=5E1ECD79",
            Date(1572462000),
            Date(1572472800),
            Location("Klub Hydrozagadka", "Warszawa", "11 listopada 22", "03-436"),
            "PLN",
            0.0,
            0.0
        )

        val event5 = EventResponse(
            "442909573185322",
            "Bitamina",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/74268568_2683893678344165_3497317347155247104_n.jpg?_nc_cat=1&_nc_oc=AQk4QjrgJpjHIsLW0lBbyt_lhStncTgnfuhXLqp76ws0Y1SpjB9fUsPZHbfRTFCfuoY&_nc_ht=scontent-waw1-1.xx&oh=fd60a61aef15956ce81b39a4b857d362&oe=5E1E9ADF",
            Date(1572462000),
            Date(1572472800),
            Location("Palladium", "Warszawa", "Złota 9", "00-019"),
            "PLN",
            0.0,
            0.0
        )

        val event6 = EventResponse(
            "442909573185323",
            "Sen nocy letniej - Felix Mendelssohn-Bartholdy / Giorgio Madia",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/71698340_2626518014059027_913999582101241856_n.jpg?_nc_cat=110&_nc_oc=AQk7LzVt8OnZTVVeFfYHasQBQhPCQ721wq1B7em8Emc-fbx1KWraV9ASgUjd87sd9tU&_nc_ht=scontent-waw1-1.xx&oh=b5464f54d193b959a8751b71f81349f5&oe=5E1E468D",
            Date(1572462000),
            Date(1572472800),
            Location("Opera Krakowska", "Kraków", "Lubicz 48", "31-512"),
            "PLN",
            0.0,
            0.0
        )
        val event7 = EventResponse(
            "442909573185324",
            "Tutaj // Zemler, Bukowski, Gubała, Sznajderman",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/71757966_2776772382347017_2543151939571417088_o.jpg?_nc_cat=108&_nc_oc=AQmT6hxJqbEsScTtlNWkIk4AkUB2hrveQn38XLo5lWNcQJddXupWI6QI02ASdfso6kA&_nc_ht=scontent-waw1-1.xx&oh=0e470c7902ac97259de4e5324694eae8&oe=5E26B2CC",
            Date(1572462000),
            Date(1572472800),
            Location("Cheder", "Kraków", "Józefa 36", "31-056"),
            "PLN",
            0.0,
            0.0
        )

        val event8 = EventResponse(
            "442909573185325",
            "Oliver Koletzki",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/68538830_2401233940197651_3554126802270027776_o.jpg?_nc_cat=109&_nc_oc=AQlsQai4jA1WXmV1DL9yBlolC6SOfM1a2u8UBmKYqzB04KUSJs45T7j0fgRMwLtADG8&_nc_ht=scontent-waw1-1.xx&oh=a2000f8c1dbf6c791b68db4ef9dc6b85&oe=5E5B1EAD",
            Date(1572462000),
            Date(1572472800),
            Location("Tama", "Poznań", "Niezłomnych 2", "61-894"),
            "PLN",
            0.0,
            0.0
        )

        val event9 = EventResponse(
            "442909573185326",
            "Ólafur Arnalds w NOSPR",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/62133288_10162058800435077_6588224229970280448_o.jpg?_nc_cat=110&_nc_oc=AQld7jNq4QQVdaSc2CBAdZbaim_7P8BJfgpQ6C6K2RHsEeXDrPfHhBu1Ua5JbRacCMs&_nc_ht=scontent-waw1-1.xx&oh=0782cd64f03b3d5b368a0c0e40099e4f&oe=5E598E07",
            Date(1572462000),
            Date(1572472800),
            Location("Tama", "Poznań", "Niezłomnych 2", "61-894"),
            "PLN",
            0.0,
            0.0
        )

        val event10 = EventResponse(
            "442909573185327",
            "Bonson x Soulpete - BonSoul",
            "https://scontent-waw1-1.xx.fbcdn.net/v/t1.0-9/71255723_404082920255401_7059229185671168000_o.jpg?_nc_cat=109&_nc_oc=AQmnm1BPswy5SXIiTkOEghvD875H4RsxnKaOSO2LPspvR5zpyaXHBVPNfhYfLkx8LDs&_nc_ht=scontent-waw1-1.xx&oh=d345e8a0fa19fec4c3c2e2de5696fe06&oe=5E5C66EE",
            Date(1572462000),
            Date(1572472800),
            Location("Królewstwo", "Katowice", "Rondo im. gen. Jerzego Ziętka 1", "40-121"),
            "PLN",
            0.0,
            0.0
        )




        events.addAll(listOf(event1,event2,event3,event4,event5,event6,event7,event8,event9,event10))

        events.forEach {
            database.collection("events").document(it.id).set(it)
                .addOnSuccessListener { documentReference ->
                }
                .addOnFailureListener { e ->
                }
        }
    }
}