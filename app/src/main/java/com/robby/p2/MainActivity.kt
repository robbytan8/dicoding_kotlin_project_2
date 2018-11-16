package com.robby.p2

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.robby.p2.fragment.ClubFragment
import com.robby.p2.fragment.MatchMainFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val fl_container = 1
        const val navigation = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
    }

    inner class MainActivityUI : AnkoComponent<MainActivity> {

        private lateinit var bottomNavigation: BottomNavigationView

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            relativeLayout {
                lparams(matchParent, matchParent)

                frameLayout {
                    id = fl_container
                }.lparams(matchParent, wrapContent) {
                    alignParentTop()
                    alignParentLeft()
                    alignParentRight()
                    topOf(navigation)
                }

                bottomNavigation = bottomNavigationView {
                    id = navigation
                    inflateMenu(R.menu.navigation)
                    itemBackgroundResource = android.R.color.holo_orange_light
                }.lparams(dip(0), wrapContent) {
                    alignParentBottom()
                    alignParentLeft()
                    alignParentRight()
                }
                bottomNavigation.setOnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.navigation_club -> {
                            val club = ClubFragment.newInstance()
                            supportFragmentManager.beginTransaction().replace(fl_container, club).commit()
                        }
                        R.id.navigation_match -> {
                            val club2 = MatchMainFragment.newInstance()
                            supportFragmentManager.beginTransaction().replace(fl_container, club2).commit()
                        }
                    }
                    false
                }
                bottomNavigation.selectedItemId = R.id.navigation_club
            }
        }

    }
}
