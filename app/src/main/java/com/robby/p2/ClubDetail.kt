package com.robby.p2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.LinearLayout
import com.robby.p2.entity.Club
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class ClubDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        if (intent.hasExtra(resources.getString(R.string.parcel_club))) {
            val club = intent.getParcelableExtra<Club>(resources.getString(R.string.parcel_club))
            title = club.name
            ClubDetailUI(club).setContentView(this)
        }
    }

    inner class ClubDetailUI(private var club: Club) : AnkoComponent<ClubDetail> {
        override fun createView(ui: AnkoContext<ClubDetail>) = with(ui) {
            scrollView {
                lparams(matchParent, matchParent) {
                    padding = dip(dimen(R.dimen.layout_padding))
                }

                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    imageView {
                        club.logo.let { Picasso.get().load(it).into(this) }
                    }.lparams(dimen(R.dimen.image_detail_size), dimen(R.dimen.image_detail_size)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView {
                        text = club.name
                        textAppearance = android.R.style.TextAppearance_Medium
                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView {
                        text = club.description
                    }.lparams(wrapContent, wrapContent)
                }
            }
        }

    }
}