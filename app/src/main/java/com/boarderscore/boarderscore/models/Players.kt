package com.boarderscore.boarderscore.models

import com.boarderscore.boarderscore.R
import java.io.Serializable

data class Players(var pseudo: String? = null, var score: Int = 0, var editable : Boolean = false, var picture: Int = R.drawable.ic_jim) : Serializable{
    init{

        val list =
            listOf(
                R.drawable.gopher11,
                R.drawable.gopher10,
                R.drawable.gopher9,
                R.drawable.gopher8,
                R.drawable.gopher7,
                R.drawable.gopher6,
                R.drawable.gopher3,
                R.drawable.gopher4,
                R.drawable.gopher5,
                R.drawable.gopher2,
                R.drawable.gopher1,
                R.drawable.gopher12
            )
        picture = list[(Math.random() * list.size).toInt()]
    }
}