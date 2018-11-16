package com.robby.p2.util

import android.net.Uri
import com.robby.p2.BuildConfig

class TheSportDBApi {

    /**
     * https://www.thesportsdb.com/api/v1/json/1/searchteams.php?sname={TeamShortCode}
     */
    fun getTeam(teamName: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("searchteams.php")
            .appendQueryParameter("t", teamName)
            .build()
            .toString()
    }

    /**
     * Return URI String of next 15 events of league (https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328)
     */
    fun getNextEventsInLeague(leagueId: String?) : String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventsnextleague.php")
            .appendQueryParameter("id", leagueId)
            .build()
            .toString()
    }

    /**
     * Return URI String of next 15 events of league (https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328)
     */
    fun getLastEventsInLeague(leagueId: String?) : String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("eventspastleague.php")
            .appendQueryParameter("id", leagueId)
            .build()
            .toString()
    }

    /**
     * Return URI String of list team in a league (https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328)
     */
    fun getTeamsInLeague(leagueId: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookup_all_teams.php")
            .appendQueryParameter("id", leagueId)
            .build()
            .toString()
    }

    // https://www.thesportsdb.com/api/v1/json/1/search_all_leagues.php?c=England&s=Soccer
    fun getSoccerLeague(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("search_all_leagues.php")
            .appendQueryParameter("s", "soccer")
            .build()
            .toString()
    }
}