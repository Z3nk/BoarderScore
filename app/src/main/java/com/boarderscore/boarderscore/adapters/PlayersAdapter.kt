package com.boarderscore.boarderscore.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.models.Players

class PlayersAdapter(val data: List<Players>, private val notifyParent: (fireman: Players) -> Unit) :
    RecyclerView.Adapter<PlayersAdapter.VH>() {


    val map = mutableMapOf<String, Int>()
    val list =
        listOf(R.drawable.gopher11, R.drawable.gopher10,R.drawable.gopher9,R.drawable.gopher8,R.drawable.gopher7,R.drawable.gopher6, R.drawable.gopher3, R.drawable.gopher4, R.drawable.gopher5, R.drawable.gopher1)


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gopher: ImageView? = itemView.findViewById(R.id.iv_player)
        val pseudo: TextView? = itemView.findViewById(R.id.tv_pseudo)
        val score: TextView? = itemView.findViewById(R.id.tv_score)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.pseudo?.text = data[position].pseudo
        holder.score?.text = data[position].score.toString()
        data[position].pseudo?.let {
            holder.gopher?.setImageResource(getGopher(it))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return PlayersAdapter.VH(LayoutInflater.from(parent.context).inflate(R.layout.row_player, parent, false))
    }

    private fun getGopher(username: String): Int {
        if (!map.containsKey(username)) {
            map[username] = if (map.size < list.size) list[map.size] else R.drawable.ic_jim
        }
        return map[username]!!
    }


}