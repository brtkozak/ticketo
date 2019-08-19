package com.qwerty21.musicevents.data.response

import com.google.gson.annotations.SerializedName

data class ExternalLinks(
    val facebook: List<Facebook>,
    val homepage: List<Homepage>,
    val lastfm: List<Lastfm>,
    val musicbrainz: List<Musicbrainz>,
    val twitter: List<Twitter>,
    val wiki: List<Wiki>
)