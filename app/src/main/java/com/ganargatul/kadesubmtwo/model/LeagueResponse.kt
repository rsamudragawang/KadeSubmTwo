package com.ganargatul.kadesubmtwo.model

import com.google.gson.annotations.SerializedName

data class LeagueResponse (
    @SerializedName("leagues")
    val leagues: List<LeagueDetailsItem>?
)