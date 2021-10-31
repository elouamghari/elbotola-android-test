package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class Competition(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
){
    fun getImage(): String{
        return "https://images.elbotola.com/stats/competitions/$id.png"
    }
}