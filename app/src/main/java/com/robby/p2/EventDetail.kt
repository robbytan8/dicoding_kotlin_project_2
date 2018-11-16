package com.robby.p2

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.robby.p2.entity.Event
import com.robby.p2.util.TheSportDBApi
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class EventDetail : AppCompatActivity() {

    private lateinit var badgeHome: ImageView
    private lateinit var badgeAway: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        if (intent != null && intent.hasExtra(resources.getString(R.string.parcel_event))) {
            val event = intent.getParcelableExtra<Event>(resources.getString(R.string.parcel_event))
            val eventDetailUi = EventDetailUI(event)
            eventDetailUi.setContentView(this)
            getTeamImage(event.homeTeam, eventDetailUi, badgeHome)
            getTeamImage(event.awayTeam, eventDetailUi, badgeAway)
        }
    }

    private fun getTeamImage(teamName: String?, eventDetailUI: EventDetailUI, imageView: ImageView) {
        val queue = Volley.newRequestQueue(ctx)
        var result: String? = null
        val stringRequest = StringRequest(Request.Method.GET, TheSportDBApi().getTeam(teamName),
            Response.Listener<String> { response ->
                val jsonObject = JSONObject(response)
                if (!jsonObject.isNull("teams")) {
                    val jsonArray = jsonObject.getJSONArray("teams")
                    val teamJson = jsonArray.getJSONObject(0)
                    eventDetailUI.setBadge(imageView, teamJson["strTeamBadge"].toString())
                }
            }, Response.ErrorListener {
                toast("Error on service")
            })
        queue.add(stringRequest)
    }

    inner class EventDetailUI(private var event: Event) : AnkoComponent<EventDetail> {

        fun setBadge(imageView: ImageView, imagePath: String) {
            imagePath.let { Picasso.get().load(it).into(imageView) }
        }

        override fun createView(ui: AnkoContext<EventDetail>) = with(ui) {
            scrollView {
                lparams(matchParent, matchParent) {
                    setPadding(dimen(R.dimen.layout_padding), 0, dimen(R.dimen.layout_padding), 0)
                }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    linearLayout {
                        lparams(matchParent, wrapContent) {
                            orientation = LinearLayout.VERTICAL
                        }

                        textView {
                            var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                            val date = sdf.parse(event.dateEvent)
                            sdf = SimpleDateFormat("E, d MMM yyyy", Locale.US)
                            text = sdf.format(date)
                            gravity = Gravity.CENTER
                            textAppearance = android.R.style.TextAppearance_Medium
                            textColor = ContextCompat.getColor(context, R.color.text_highlight)
                        }.lparams(matchParent, wrapContent)

                        //  Badge
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                            }

                            badgeHome = imageView {
                            }.lparams(dimen(R.dimen.image_detail_size), dimen(R.dimen.image_detail_size)) {
                                weight = 1F
                                gravity = Gravity.START
                            }

                            badgeAway = imageView {
                            }.lparams(dimen(R.dimen.image_detail_size), dimen(R.dimen.image_detail_size)) {
                                weight = 1F
                                gravity = Gravity.END
                            }
                        }

                        //  Team
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                            }

                            textView {
                                text = event.homeTeam
                                gravity = Gravity.START
                                textAppearance = android.R.style.TextAppearance_Medium
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView(resources.getString(R.string.text_vs)) {
                                gravity = Gravity.CENTER
                                textAppearance = android.R.style.TextAppearance_Medium
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView {
                                text = event.awayTeam
                                gravity = Gravity.END
                                textAppearance = android.R.style.TextAppearance_Medium
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }
                        }

                        view {
                            backgroundResource = R.color.colorPrimary
                        }.lparams(matchParent, dimen(R.dimen.line_width)) {
                            setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                        }

                        //  Goals
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                            }

                            textView {
                                text = event.homeScore
                                gravity = Gravity.START
                                textAppearance = android.R.style.TextAppearance_Medium
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView(resources.getString(R.string.text_goals)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView {
                                text = event.awayScore
                                gravity = Gravity.END
                                textAppearance = android.R.style.TextAppearance_Medium
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }
                        }

                        //  Scorer
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                            }

                            textView {
                                text = event.homeGoalDetails
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView(resources.getString(R.string.text_scorer)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView {
                                text = event.awayGoalDetails
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }
                        }

                        view {
                            backgroundResource = R.color.colorPrimary
                        }.lparams(matchParent, dimen(R.dimen.line_width)) {
                            setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                        }

                        //  Line Up GK
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                                setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                            }

                            textView {
                                text = event.homeLineGk
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView(resources.getString(R.string.text_gk)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView {
                                text = event.awayLineGk
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }
                        }

                        //  Line Up Defense
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                                setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                            }

                            textView {
                                text = event.homeLineDef
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView(resources.getString(R.string.text_def)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView {
                                text = event.awayLineDef
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }
                        }

                        //  Line Up Midfield
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                                setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                            }

                            textView {
                                text = event.homeLineMid
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView(resources.getString(R.string.text_mid)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }

                            textView {
                                text = event.awayLineMid
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 0.5F
                            }
                        }

                        //  Line Up Forward
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                                setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                            }

                            textView {
                                text = event.homeLineFor
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView(resources.getString(R.string.text_forward)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView {
                                text = event.awayLineFor
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }
                        }

                        //  Line Up Subs
                        linearLayout {
                            lparams(matchParent, wrapContent) {
                                orientation = LinearLayout.HORIZONTAL
                                setMargins(0, dimen(R.dimen.component_margin), 0, dimen(R.dimen.component_margin))
                            }

                            textView {
                                text = event.homeSubs
                                gravity = Gravity.START
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView(resources.getString(R.string.text_subs)) {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.text_highlight)
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }

                            textView {
                                text = event.awaySubs
                                gravity = Gravity.END
                            }.lparams(dip(0), wrapContent) {
                                weight = 1F
                            }
                        }
                    }
                }
            }

        }

    }
}