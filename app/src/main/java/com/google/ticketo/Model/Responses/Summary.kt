package com.google.ticketo.Model.Responses

import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("total_count")
    val totalCount: Int
)