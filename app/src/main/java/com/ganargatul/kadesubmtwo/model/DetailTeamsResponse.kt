package com.ganargatul.kadesubmtwo.model

import com.google.gson.annotations.SerializedName

data class DetailTeamsResponse (
    @SerializedName("teams")
    val events: List<DetailTeamsItems>?
)