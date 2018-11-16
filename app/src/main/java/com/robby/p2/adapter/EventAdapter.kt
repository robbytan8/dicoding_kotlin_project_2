package com.robby.p2.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.robby.p2.entity.Event
import com.robby.p2.item.EventUI
import org.jetbrains.anko.AnkoContext

class EventAdapter(val clickListener: (Event) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var events: ArrayList<Event> = ArrayList()
        set(value) {
            events.clear()
            events.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItems(events[position])
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvDate = itemView.findViewById<TextView>(EventUI.tv_date)
        private val tvHome = itemView.findViewById<TextView>(EventUI.tv_home)
        private val tvAway = itemView.findViewById<TextView>(EventUI.tv_away)
        private val tvScoreHome = itemView.findViewById<TextView>(EventUI.tv_home_score)
        private val tvScoreAway = itemView.findViewById<TextView>(EventUI.tv_away_score)

        fun bindItems(event: Event) {
            tvDate.text = event.dateEvent
            tvHome.text = event.homeTeam
            tvAway.text = event.awayTeam
            event.awayScore.let {
                tvScoreAway.text = event.awayScore
            }
            event.homeScore.let {
                tvScoreHome.text = event.homeScore
            }
            itemView.setOnClickListener {

                clickListener(event)
            }
        }


    }
}