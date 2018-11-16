package com.robby.p2.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.robby.p2.entity.Club
import com.robby.p2.item.ClubUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext

class ClubAdapter(val clickListener: (Club) -> Unit) : RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    var clubs: ArrayList<Club> = ArrayList()
    set(value) {
        clubs.clear()
        clubs.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ClubViewHolder {
        return ClubViewHolder(ClubUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = clubs.size

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bindItems(clubs[position])
    }

    inner class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivLogo = itemView.findViewById<ImageView>(ClubUI.iv_logo)
        private val tvName = itemView.findViewById<TextView>(ClubUI.tv_name)

        fun bindItems(club: Club) {
            club.logo.let { Picasso.get().load(it).into(ivLogo) }
            tvName.text = club.name
            itemView.setOnClickListener {
                clickListener(club)
            }
        }
    }
}
