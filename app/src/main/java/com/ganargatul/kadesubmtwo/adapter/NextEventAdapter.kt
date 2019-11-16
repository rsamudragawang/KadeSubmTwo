package com.ganargatul.kadesubmtwo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.model.LeagueItems
import com.ganargatul.kadesubmtwo.model.NextEventsItems
import com.squareup.picasso.Picasso

class NextEventAdapter(private  val context: Context, private val Items: List<NextEventsItems>, private val listener: (NextEventsItems) -> Unit): RecyclerView.Adapter<NextEventAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.title_items)

        fun bindItem(items: NextEventsItems, listener: (NextEventsItems) -> Unit){
            name.text = items.strEvent
            itemView.setOnClickListener { listener(items) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextEventAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.next_event_item,parent,false))
    }

    override fun getItemCount(): Int = Items.size

    override fun onBindViewHolder(holder: NextEventAdapter.ViewHolder, position: Int) {
        holder.bindItem(Items[position],listener)
    }
}