package com.google.ticketo.model.Responses

import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("total_count")
    val totalCount: Int
)