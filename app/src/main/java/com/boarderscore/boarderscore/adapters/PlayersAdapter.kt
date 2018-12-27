package com.boarderscore.boarderscore.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.models.Players
import com.boarderscore.boarderscore.utils.StaticFields.nbPlayers


class PlayersAdapter(val data: ArrayList<Players>, private val notifyParent: (fireman: Players) -> Unit) :
    RecyclerView.Adapter<PlayersAdapter.VH>() {


    val map = mutableMapOf<String, Int>()
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


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gopher: ImageView? = itemView.findViewById(R.id.iv_player)
        val pseudo: TextView? = itemView.findViewById(R.id.tv_pseudo)
        val score: TextView? = itemView.findViewById(R.id.tv_score)
        val delete: ImageView? = itemView.findViewById(R.id.iv_delete)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.pseudo?.text = data[position].pseudo
        holder.score?.text = data[position].score.toString()
        data[position].pseudo?.let {
            holder.gopher?.setImageResource(getGopher(it))
        }
        holder.delete?.setOnClickListener {
            removeAt(position)
        }
        if (data[position].editable) {
            holder.score?.visibility = View.GONE
            holder.delete?.visibility = View.VISIBLE
        } else {
            holder.score?.visibility = View.VISIBLE
            holder.delete?.visibility = View.GONE
        }
    }

    fun removeAt(position: Int) {
        if (data.elementAtOrNull(position) != null) {
            if (map.containsKey(data[position].pseudo)) {
                map.remove(data[position].pseudo)
            }
            data.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, data.size)
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
            map[username] = if (map.size < list.size) list[nbPlayers - 1] else R.drawable.ic_jim
        }
        return map[username]!!
    }


}