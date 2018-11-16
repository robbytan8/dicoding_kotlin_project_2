package com.robby.p2.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robby.p2.EventDetail
import com.robby.p2.R
import com.robby.p2.adapter.EventAdapter
import com.robby.p2.entity.League
import com.robby.p2.util.TheSportDBApi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.util.*

abstract class EventTabFragment : Fragment() {

    private lateinit var leagueAdapter: ArrayAdapter<League>
    protected lateinit var eventAdapter: EventAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return MatchTabFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    override fun onStart() {
        super.onStart()
        initLeagueData()
    }

    private fun initLeagueData() {
        val queue = Volley.newRequestQueue(ctx)
        val stringRequest = StringRequest(Request.Method.GET, TheSportDBApi().getSoccerLeague(),
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("countrys")
                val type = object : TypeToken<ArrayList<League>>() {}.type
                val data = Gson().fromJson<ArrayList<League>>(jsonArray.toString(), type)
                leagueAdapter.clear()
                leagueAdapter.addAll(data)
            }, Response.ErrorListener {
                toast("Error on service")
            })
        queue.add(stringRequest)
    }

    protected abstract fun initMatchData(leagueId: String)

    inner class MatchTabFragmentUI : AnkoComponent<EventTabFragment> {

        private lateinit var listTeam: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var swipeRefresh: SwipeRefreshLayout
        private lateinit var spinner: Spinner

        override fun createView(ui: AnkoContext<EventTabFragment>) = with(ui) {
            linearLayout {
                lparams(matchParent, matchParent) {
                    orientation = LinearLayout.VERTICAL
                    setPadding(dimen(R.dimen.layout_padding), 0, dimen(R.dimen.layout_padding), 0)
                }

                spinner = spinner {
                    leagueAdapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item)
                    adapter = leagueAdapter
                    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val selected: League = parent?.getItemAtPosition(position) as League
                            initMatchData(selected.id)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
                }.lparams(matchParent, wrapContent) { }

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(
                        R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                    )

                    relativeLayout {
                        lparams(matchParent, matchParent)

                        listTeam = recyclerView {
                            lparams(matchParent, matchParent) {
                                val orientation = LinearLayoutManager.VERTICAL
                                layoutManager = LinearLayoutManager(context)
                                val divider = DividerItemDecoration(context, orientation)
                                addItemDecoration(divider)
                                visibility = View.VISIBLE
                            }
                            eventAdapter = EventAdapter {
                                startActivity(intentFor<EventDetail>(resources.getString(R.string.parcel_event) to it))
                            }
                            adapter = eventAdapter
                        }

                        progressBar = progressBar {
                            visibility = View.INVISIBLE
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                swipeRefresh.setOnRefreshListener {
                    val selected: League = spinner.selectedItem as League
                    initMatchData(selected.id)
                    swipeRefresh.isRefreshing = false
                }
            }
        }
    }
}