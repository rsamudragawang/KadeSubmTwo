package com.ganargatul.kadesubmtwo.model

import com.google.gson.annotations.SerializedName

data class SearchEventResponse(
    @SerializedName("event")
    val event: List<SearchEventsItems>? = null
)