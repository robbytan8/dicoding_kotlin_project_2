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
import com.robby.p2.ClubDetail
import com.robby.p2.R
import com.robby.p2.adapter.ClubAdapter
import com.robby.p2.entity.Club
import com.robby.p2.entity.League
import com.robby.p2.util.TheSportDBApi
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class ClubFragment : Fragment() {

    companion object {
        fun newInstance(): ClubFragment = ClubFragment()
    }

    private lateinit var leagueAdapter: ArrayAdapter<League>
    private lateinit var clubAdapter: ClubAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ClubFragmentUI().createView(AnkoContext.create(ctx, this))
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

    private fun initClubData(leagueId: String?) {
        val queue = Volley.newRequestQueue(ctx)
        val stringRequest = StringRequest(Request.Method.GET, TheSportDBApi().getTeamsInLeague(leagueId),
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                if (!jsonObject.isNull("teams")) {
                    val jsonArray = jsonObject.getJSONArray("teams")
                    val type = object : TypeToken<ArrayList<Club>>() {}.type
                    val data = Gson().fromJson<ArrayList<Club>>(jsonArray.toString(), type)
                    clubAdapter.clubs = data
                } else {
                    view?.let { snackbar(it, "Sorry, the league you are chosen has no teams") }
                }
            }, Response.ErrorListener {
                toast("Error on service")
            })
        queue.add(stringRequest)
    }

    inner class ClubFragmentUI : AnkoComponent<ClubFragment> {

        private lateinit var listTeam: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var swipeRefresh: SwipeRefreshLayout
        private lateinit var spinner: Spinner

        override fun createView(ui: AnkoContext<ClubFragment>) = with(ui) {
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
                            val selected: League? = parent?.getItemAtPosition(position) as League
                            initClubData(selected?.id)
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
                            clubAdapter = ClubAdapter {
                                startActivity(intentFor<ClubDetail>(resources.getString(R.string.parcel_club) to it))
                            }
                            adapter = clubAdapter
                        }

                        progressBar = progressBar {
                            visibility = View.INVISIBLE
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }

                swipeRefresh.setOnRefreshListener {
                    val selected: League? = spinner.selectedItem as League
                    initClubData(selected?.id)
                    swipeRefresh.isRefreshing = false
                }
            }
        }

    }

}