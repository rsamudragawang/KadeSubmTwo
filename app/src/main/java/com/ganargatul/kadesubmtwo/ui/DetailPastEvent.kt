package com.ganargatul.kadesubmtwo.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.NextEventsItems
import com.ganargatul.kadesubmtwo.model.PastEventItems
import com.ganargatul.kadesubmtwo.model.PastEventReponse
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import retrofit2.Call
import retrofit2.Response

class DetailPastEvent : AppCompatActivity() {
    //score , goalHome , redHome , yellowHome, goalAway , redAway , yellowAway , strEvent imageView
    lateinit var score: TextView
    lateinit var goalHome: TextView
    lateinit var redHome: TextView
    lateinit var yellowHome: TextView
    lateinit var goalAway: TextView
    lateinit var redAway: TextView
    lateinit var yellowAway: TextView
    lateinit var strEvent: TextView
    lateinit var imageView: ImageView
    lateinit var items: PastEventItems
    lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_past_event)
        items= intent.getParcelableExtra("Data")
        progressbar = findViewById(R.id.detail_league_prog)
        progressbar.visibility = View.VISIBLE
        score = findViewById(R.id.score_detail)
        goalHome = findViewById(R.id.goalHome_detail)
        redHome = findViewById(R.id.redHome_detail)
        yellowHome = findViewById(R.id.yellowHome_detail)
        goalAway = findViewById(R.id.goalAway_detail)
        redAway = findViewById(R.id.redAway_detail)
        yellowAway = findViewById(R.id.yellowAway_detail)
        strEvent = findViewById(R.id.title_detail)
        imageView = findViewById(R.id.image_detail)

        RetroConfig().services.getDetailEvent(items.idEvent.toString()).enqueue(object : retrofit2.Callback<PastEventReponse>{
            override fun onFailure(call: Call<PastEventReponse>, t: Throwable) {
                d("onFailure",t.message)
            }

            override fun onResponse(
                call: Call<PastEventReponse>,
                response: Response<PastEventReponse>
            ) {
                if (response.code() == 200){
                    response.body()?.events.let {
                        it?.let { it1 -> showItems(it1) }
                    }
                }
            }

        })

    }
    //score , goalHome , redHome , yellowHome, goalAway , redAway , yellowAway , strEvent imageView

    @SuppressLint("SetTextI18n")
    private fun showItems(it1: List<PastEventItems>) {
        progressbar.visibility = View.GONE
        it1[0].strThumb.let {  Picasso.get().load(it).into(imageView) }
        score.text = it1[0].intHomeScore.toString() +"  " + it1[0].intAwayScore.toString()
        goalHome.text = it1[0].strHomeGoalDetails
        redHome.text = it1[0].strHomeRedCards
        yellowHome.text = it1[0].strHomeYellowCards
        goalAway.text = it1[0].strAwayGoalDetails
        redAway.text = it1[0].strAwayRedCards
        yellowAway.text = it1[0].strAwayYellowCards
        strEvent.text = it1[0].strEvent

    }
}
