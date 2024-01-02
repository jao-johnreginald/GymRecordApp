package com.johnreg.gymrecord.recyclerview

data class Workout(
    val image: Int,
    val textTitle: String,
    val textRecord: String?,
    val textDate: String?,
    val rating: Float
)