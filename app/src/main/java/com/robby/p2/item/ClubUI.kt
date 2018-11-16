package com.robby.p2.item

import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import com.robby.p2.R
import org.jetbrains.anko.*

class ClubUI : AnkoComponent<ViewGroup> {

    companion object {
        const val iv_logo = 1
        const val tv_name = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        linearLayout {
            lparams (matchParent, wrapContent){
                orientation = LinearLayout.HORIZONTAL
                topPadding = dimen(R.dimen.row_padding)
                bottomPadding = dimen(R.dimen.row_padding)
            }

            imageView {
                id = iv_logo
            }.lparams(dimen(R.dimen.image_row_size), dimen(R.dimen.image_row_size))

            textView {
                id = tv_name
            }.lparams (matchParent, wrapContent){
                gravity = Gravity.CENTER_VERTICAL
                leftMargin = dimen(R.dimen.component_margin)
            }
        }
    }
}