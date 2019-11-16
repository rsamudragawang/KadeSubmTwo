package com.ganargatul.kadesubmtwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.adapter.NextEventAdapter
import com.ganargatul.kadesubmtwo.adapter.PastEventAdapter
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response

class PastEvent : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: PastEventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_event)
        val pb= findViewById<ProgressBar>(R.id.pb_past_event)
        pb.visibility = View.VISIBLE
        recyclerview= findViewById(R.id.rv_list_past_event)
        recyclerview.layoutManager= LinearLayoutManager(baseContext)
        val items= intent.getParcelableExtra<LeagueItems>("Data")
        RetroConfig().services.getPastEvent(items.id.toString()).enqueue(object: retrofit2.Callback<PastEventReponse>{
            override fun onFailure(call: Call<PastEventReponse>, t: Throwable) {
                Log.d("onFailure", t.message)
            }

            override fun onResponse(
                call: Call<PastEventReponse>,
                response: Response<PastEventReponse>
            ) {
                pb.visibility = View.GONE
                if (response.code() == 200){
                    response.body()?.events.let {
                        it?.let { it1 -> showItems(it1) }
                    }
                }
            }

        })
    }

    private fun showItems(it1: List<PastEventItems>) {
        adapter= PastEventAdapter(baseContext,it1){
            var nowItems = PastEventItems(it.idEvent,it.strEvent,it.dateEvent,it.strTime,it.idHomeTeam,it.idAwayTeam,
                it.strThumb,it.strHomeGoalDetails,it.strHomeRedCards,it.strHomeYellowCards,it.strAwayRedCards,it.strAwayYellowCards,
                it.strAwayGoalDetails,it.intHomeScore,it.intAwayScore)
            startActivity<DetailPastEvent>("Data" to nowItems)
        }

        recyclerview.adapter = adapter
    }
}
