package com.johnreg.gymrecord.constants

import java.io.Serializable

data class IntentData(
    val image: Int,
    val textTitle: String
) : Serializable