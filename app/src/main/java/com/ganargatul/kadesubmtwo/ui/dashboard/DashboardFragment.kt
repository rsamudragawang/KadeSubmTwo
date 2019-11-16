package com.ganargatul.kadesubmtwo.ui.dashboard

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ganargatul.kadesubmtwo.DetailLeague
import com.ganargatul.kadesubmtwo.R
import com.ganargatul.kadesubmtwo.adapter.LeagueAdapter
import com.ganargatul.kadesubmtwo.model.LeagueItems
import com.ganargatul.kadesubmtwo.ui.NextEvent
import com.ganargatul.kadesubmtwo.ui.SearchActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.support.v4.startActivity

class DashboardFragment : Fragment() {
    private var items: MutableList<LeagueItems> = mutableListOf()
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerView = root.findViewById(R.id.rv_list_league)
        recyclerView.layoutManager=LinearLayoutManager(context)
        initdata()
        setHasOptionsMenu(true)
        return root
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menu != null) {
            menu.clear()
            inflater?.inflate(R.menu.toolbar_menu,menu)
            val searchManager = this.activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.search_menu).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            searchView.queryHint = resources.getString(R.string.search_hint)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    startActivity<SearchActivity>("query" to query)
                    return true
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        }


    }
    @SuppressLint("Recycler")
    private fun initdata() {
        val name = resources.getStringArray(R.array.name_league)
        val description = resources.getStringArray(R.array.description)
        val image = resources.obtainTypedArray(R.array.photo_league)
        val id = resources.getIntArray(R.array.id_league)
        val adapter = context?.let {
            LeagueAdapter(it,items){

                var nowItems = LeagueItems(it.name,it.description,it.image,it.id)
                var bundle = Bundle()
                startActivity<NextEvent>("Data" to nowItems)

            }
        }
        items.clear()
        for (i in name.indices){
            items.add(LeagueItems(name[i],description [i],image.getResourceId(i,0),id[i]))
        }
        recyclerView.adapter = adapter

    }
}