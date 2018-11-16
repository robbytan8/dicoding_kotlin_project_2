package com.robby.p2.fragment

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robby.p2.entity.Event
import com.robby.p2.util.TheSportDBApi
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.util.*

class LastEventFragment : EventTabFragment() {

    override fun initMatchData(leagueId: String) {
        val queue = Volley.newRequestQueue(ctx)
        val stringRequest = StringRequest(Request.Method.GET, TheSportDBApi().getLastEventsInLeague(leagueId),
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                if (!jsonObject.isNull("events")) {
                    val jsonArray = jsonObject.getJSONArray("events")
                    val type = object : TypeToken<ArrayList<Event>>() {}.type
                    val data = Gson().fromJson<ArrayList<Event>>(jsonArray.toString(), type)
                    eventAdapter.events = data
                } else {
                    view?.let { snackbar(it, "There are no last event match in this league") }
                }
            }, Response.ErrorListener {
                toast("Error on service")
            })
        queue.add(stringRequest)
    }
}