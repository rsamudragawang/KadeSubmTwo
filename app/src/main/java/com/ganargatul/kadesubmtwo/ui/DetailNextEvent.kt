package com.ganargatul.kadesubmtwo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.connection.ApiServices
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.DetailTeamsItems
import com.ganargatul.kadesubmtwo.model.DetailTeamsResponse
import com.ganargatul.kadesubmtwo.model.NextEventsItems
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class DetailNextEvent : AppCompatActivity() {
    lateinit var eventName: TextView
    lateinit var eventDate: TextView
    lateinit var homeBadge: ImageView
    lateinit var awayBadge: ImageView
    lateinit var eventTime: TextView
    lateinit var progressbar: ProgressBar
    lateinit var items: NextEventsItems
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_next_event)
        progressbar = findViewById(R.id.detail_league_prog)
        progressbar.visibility = View.VISIBLE
        items= intent.getParcelableExtra("Data")
        eventName = findViewById(R.id.title_detail)
        eventDate = findViewById(R.id.date_detail)
        homeBadge = findViewById(R.id.homeBadge)
        awayBadge = findViewById(R.id.awayBadge)
        eventTime = findViewById(R.id.time_detail)

        RetroConfig().services.getDetailTeam(items.idHomeTeam.toString()).enqueue(object : retrofit2.Callback<DetailTeamsResponse>{
            override fun onFailure(call: Call<DetailTeamsResponse>, t: Throwable) {
                d("failure",t.message)
            }

            override fun onResponse(
                call: Call<DetailTeamsResponse>,
                response: Response<DetailTeamsResponse>
            ) {
                if (response.code() == 200){
                    response.body()?.events.let {
                        it?.let { it1 -> getAwayBadge(it1) }
                    }
                }
            }

        })
    }

    private fun getAwayBadge(it1: List<DetailTeamsItems>) {
        RetroConfig().services.getDetailTeam(items.idAwayTeam.toString()).enqueue(object : retrofit2.Callback<DetailTeamsResponse>{
            override fun onFailure(call: Call<DetailTeamsResponse>, t: Throwable) {
                d("failure",t.message)
            }

            override fun onResponse(
                call: Call<DetailTeamsResponse>,
                response: Response<DetailTeamsResponse>
            ) {
                progressbar.visibility = View.GONE
                eventName.text=items.strEvent
                eventDate.text = items.dateEvent
                eventTime.text = items.strTime
                response.body()?.events?.get(0)?.strTeamBadge.let { Picasso.get().load(it).into(awayBadge) }
                it1[0]?.strTeamBadge.let {  Picasso.get().load(it).into(homeBadge) }
            }

        })
    }
}
