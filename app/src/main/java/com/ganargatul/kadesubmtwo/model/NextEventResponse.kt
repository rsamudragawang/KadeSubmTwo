package com.ganargatul.kadesubmtwo.model

import com.google.gson.annotations.SerializedName

data class NextEventResponse (
    @SerializedName("events")
    val events: List<NextEventsItems>?

)