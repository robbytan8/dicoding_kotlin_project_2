package com.robby.p2.item

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.robby.p2.R
import org.jetbrains.anko.*

class EventUI : AnkoComponent<ViewGroup> {

    companion object {
        const val tv_date = 1
        const val tv_home = 2
        const val tv_away = 3
        const val tv_home_score = 4
        const val tv_away_score = 5
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent) {
                orientation = LinearLayout.VERTICAL
            }

            textView {
                id = tv_date
                gravity = Gravity.CENTER
                textColor = ContextCompat.getColor(context, R.color.text_highlight)
            }.lparams(matchParent, wrapContent)

            linearLayout {
                lparams(matchParent, wrapContent) {
                    orientation = LinearLayout.HORIZONTAL
                }

                textView {
                    id = tv_home
                    gravity = Gravity.END
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(dip(0), wrapContent) {
                    weight = 1F
                }

                textView {
                    id = tv_home_score
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView ("VS") {
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView {
                    id = tv_away_score
                    gravity = Gravity.CENTER
                }.lparams(dip(0), wrapContent) {
                    weight = 0.5F
                }

                textView {
                    id = tv_away
                    gravity = Gravity.START
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(dip(0), wrapContent) {
                    weight = 1F
                }
            }
        }
    }
}