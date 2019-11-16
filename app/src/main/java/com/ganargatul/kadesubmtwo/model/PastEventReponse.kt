package com.ganargatul.kadesubmtwo.model

import com.google.gson.annotations.SerializedName

data class PastEventReponse (
    @SerializedName("events")
    val events: List<PastEventItems>?
)