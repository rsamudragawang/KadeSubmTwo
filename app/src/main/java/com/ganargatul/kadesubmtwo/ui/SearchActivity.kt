package com.ganargatul.kadesubmtwo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.adapter.NextEventAdapter
import com.ganargatul.kadesubmtwo.adapter.SearchEventAdapter
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.LeagueItems
import com.ganargatul.kadesubmtwo.model.NextEventsItems
import com.ganargatul.kadesubmtwo.model.SearchEventResponse
import com.ganargatul.kadesubmtwo.model.SearchEventsItems
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    lateinit var progressbar: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SearchEventAdapter
    private var items: MutableList<SearchEventsItems> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var stringextra = intent.getStringExtra("query")
        progressbar = findViewById(R.id.progress_search)
        progressbar.visibility = View.VISIBLE
        recyclerView= findViewById(R.id.rv_search)
        recyclerView.layoutManager= LinearLayoutManager(baseContext)
        RetroConfig().services.searchEvents(stringextra).enqueue(object : retrofit2.Callback<SearchEventResponse>{
            override fun onFailure(call: Call<SearchEventResponse>, t: Throwable) {
                d("failure",t.message)
            }

            override fun onResponse(
                call: Call<SearchEventResponse>,
                response: Response<SearchEventResponse>
            ) {
                progressbar.visibility =View.GONE
                if (response.code() == 200) {
                    d("response", response.body()?.event?.size.toString())
                    if (response.body()?.event?.size.toString() == "null") {
                        Toast.makeText(baseContext, "Not Found", Toast.LENGTH_SHORT).show()

                    } else {
                        for (i in response.body()?.event?.indices!!){
                            if (response.body()?.event?.get(i)?.strSport =="Soccer"){
                                items.add(SearchEventsItems(response.body()?.event?.get(i)?.idEvent,response.body()?.event?.get(i)?.strEvent,response.body()?.event?.get(i)?.dateEvent,
                                    response.body()?.event?.get(i)?.strTime,response.body()?.event?.get(i)?.idHomeTeam,response.body()?.event?.get(i)?.idAwayTeam,response.body()?.event?.get(i)?.strSport))
                            }
                        }
                        d("items", items.toString())
                        adapter= SearchEventAdapter(baseContext,items){
                            var nowItems = NextEventsItems(it.idEvent,it.strEvent,it.dateEvent,it.strTime,it.idHomeTeam,it.idAwayTeam)
                            startActivity<DetailNextEvent>("Data" to nowItems)
                        }
                        recyclerView.adapter = adapter

                    }
                }
            }

        })
    }

}
