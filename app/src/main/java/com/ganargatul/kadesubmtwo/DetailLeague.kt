package com.ganargatul.kadesubmtwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.ganargatul.kadesubmtwo.connection.RetroConfig
import com.ganargatul.kadesubmtwo.model.LeagueDetailsItem
import com.ganargatul.kadesubmtwo.model.LeagueItems
import com.ganargatul.kadesubmtwo.model.LeagueResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.progressDialog
import retrofit2.Call
import retrofit2.Response

class DetailLeague : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        var progressbar = findViewById<ProgressBar>(R.id.detail_league_prog)
        progressbar.visibility = View.VISIBLE
        val items= intent.getParcelableExtra<LeagueItems>("Data")
        var name = findViewById<TextView>(R.id.title_detail)
        var desc = findViewById<TextView>(R.id.desc_detail)
        var img = findViewById<ImageView>(R.id.image_detail)
        var website = findViewById<TextView>(R.id.website_detail)


        RetroConfig().services.getDetailLeague(items.id.toString()).enqueue(object: retrofit2.Callback<LeagueResponse>{
            override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {
                d("failure",t.message)
            }

            override fun onResponse(
                call: Call<LeagueResponse>,
                response: Response<LeagueResponse>
            ) {
                d("onresponse", response.body().toString())
                items.image?.let { Picasso.get().load(it).into(img) }
                name.text = items.name
                progressbar.visibility = View.GONE
                desc.text = response.body()?.leagues?.get(0)?.strDescriptionEN
                website.text = response.body()?.leagues?.get(0)?.strWebsite
            }

        })

    }
}
