package com.boarderscore.boarderscore.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.boarderscore.boarderscore.R
import com.boarderscore.boarderscore.models.HandlerType
import com.boarderscore.boarderscore.models.Players
import com.boarderscore.boarderscore.utils.StaticFields.nbPlayers
import java.lang.Exception


class PlayersAdapter(val data: ArrayList<Players>, private val notifyParent: (player: Players?, handlerType: HandlerType) -> Unit) :
    RecyclerView.Adapter<PlayersAdapter.VH>() {


    val map = mutableMapOf<String, Int>()


    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gopher: ImageView? = itemView.findViewById(R.id.iv_player)
        val pseudo: TextView? = itemView.findViewById(R.id.tv_pseudo)
        val score: TextView? = itemView.findViewById(R.id.tv_score)
        val delete: ImageView? = itemView.findViewById(R.id.iv_delete)
        val layout: View? = itemView.findViewById(R.id.layout_player)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.pseudo?.text = data[position].pseudo
        holder.score?.text = data[position].score.toString()
        data[position].pseudo?.let {
            holder.gopher?.setImageResource(data[position].picture)
        }
        holder.delete?.setOnClickListener {
            var player : Players? = null
            if (data.elementAtOrNull(position) != null) {
                if (map.containsKey(data[position].pseudo)) {
                    player = data[position]
                }
            }
            removeAt(position)
            notifyParent(player, HandlerType.REMOVE)
        }
        if (data[position].editable) {
            holder.score?.visibility = View.GONE
            holder.delete?.visibility = View.VISIBLE
        } else {
            holder.score?.visibility = View.VISIBLE
            holder.delete?.visibility = View.GONE
        }

        holder.layout?.setOnClickListener {
            notifyParent(data[position], HandlerType.EDIT)
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


}