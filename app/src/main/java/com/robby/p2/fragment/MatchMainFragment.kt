package com.robby.p2.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTabHost
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.robby.p2.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.fragmentTabHost

class MatchMainFragment : Fragment() {

    companion object {
        fun newInstance(): MatchMainFragment = MatchMainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return MatchMainFragmentUI().createView(AnkoContext.create(ctx, this))
    }

    inner class MatchMainFragmentUI : AnkoComponent<MatchMainFragment> {

        private lateinit var frameTab: FrameLayout
        private lateinit var tabHost: FragmentTabHost

        override fun createView(ui: AnkoContext<MatchMainFragment>) = with(ui) {
            linearLayout {
                lparams(matchParent, matchParent) {
                    orientation = LinearLayout.VERTICAL
                }

                tabHost = fragmentTabHost {
                    id = android.R.id.tabhost

                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        lparams(matchParent, matchParent)

                        horizontalScrollView {
                            lparams(matchParent, wrapContent)

                            tabWidget {
                                id = android.R.id.tabs
                            }
                        }

                        frameLayout {
                            id = android.R.id.tabcontent
                        }

                        frameTab = frameLayout {
                            id = android.R.id.tabcontent
                        }
                    }
                }
                tabHost.setup(ctx, childFragmentManager, android.R.id.tabcontent)
                tabHost.addTab(
                    tabHost.newTabSpec(resources.getString(R.string.tab_last_match))
                        .setIndicator(resources.getString(R.string.tab_last_match)),
                    LastEventFragment::class.java, null
                )
                tabHost.addTab(
                    tabHost.newTabSpec(resources.getString(R.string.tab_next_match))
                        .setIndicator(resources.getString(R.string.tab_next_match)),
                    NextEventFragment::class.java, null
                )
            }
        }
    }
}