package com.boarderscore.boarderscore.models

import java.io.Serializable

data class Players(var pseudo: String? = null, var score: Int = 0, var editable : Boolean = false) : Serializable