package com.ganargatul.kadesubmtwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.adapter.NextEventAdapter
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.LeagueItems
import com.ganargatul.kadesubmtwo.model.NextEventResponse
import com.ganargatul.kadesubmtwo.model.NextEventsItems
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response

class NextEvent : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: NextEventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_event)
        val pb= findViewById<ProgressBar>(R.id.pb_next_event)
        pb.visibility = View.VISIBLE
        recyclerview= findViewById(R.id.rv_list_next_event)
        recyclerview.layoutManager= LinearLayoutManager(baseContext)
        val items= intent.getParcelableExtra<LeagueItems>("Data")
        RetroConfig().services.getNextEvent(items.id.toString()).enqueue(object: retrofit2.Callback<NextEventResponse>{
            override fun onFailure(call: Call<NextEventResponse>, t: Throwable) {
                d("onFailure",t.message)
            }

            override fun onResponse(
                call: Call<NextEventResponse>,
                response: Response<NextEventResponse>
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

    private fun showItems(it: List<NextEventsItems>) {
        adapter= NextEventAdapter(baseContext,it){
            var nowItems = NextEventsItems(it.idEvent,it.strEvent,it.dateEvent,it.strTime,it.idHomeTeam,it.idAwayTeam)
            startActivity<DetailNextEvent>("Data" to nowItems)
        }

        recyclerview.adapter = adapter
    }
}
