package com.planradar.cities.ui.cities.citiesList

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.planradar.cities.R
import com.planradar.cities.data.CityRecord
import com.planradar.cities.ui.AddCityActivity
import com.planradar.cities.ui.cities.cityDetails.CityDetailsActivity
import com.planradar.cities.ui.cities.cityHistorical.CityHistoricalActivity
import com.planradar.cities.utils.Constants
import com.planradar.cities.utils.ItemSwipeCallback
import kotlinx.android.synthetic.main.activity_cities_list.*
import kotlinx.android.synthetic.main.activity_cities_list.toolbar
import kotlinx.android.synthetic.main.content_cities_list.*


class CitiesListActivity : AppCompatActivity(), CitiesListAdapter.CityEvents {

    private lateinit var citiesViewModel: CitiesViewModel
    private lateinit var searchView: SearchView
    private lateinit var citiesAdapter: CitiesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities_list)
        setSupportActionBar(toolbar)
        cities_lbl.text=getString(R.string.cities)
        rv_city_list.layoutManager = LinearLayoutManager(this)
        citiesAdapter = CitiesListAdapter(this)
        rv_city_list.adapter = citiesAdapter
        ItemTouchHelper(
            ItemSwipeCallback(
                citiesAdapter,
                applicationContext,
                false
            )
        ).attachToRecyclerView(rv_city_list)

        citiesViewModel = ViewModelProviders.of(this).get(CitiesViewModel::class.java)
        citiesViewModel.getCitiesList().observe(this, Observer {
            citiesAdapter.setAllCities(it)
        })

        add_city_btn.setOnClickListener {
            resetSearchView()
            val intent = Intent(this@CitiesListActivity, AddCityActivity::class.java)
            startActivityForResult(intent, Constants.INTENT_CREATE_City)
        }
    }

    override fun onItemDeleted(city: CityRecord, position: Int) {
        citiesViewModel.deleteCity(city)
        val snackbar = Snackbar.make(container, R.string.deleted, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun onViewClicked(city: CityRecord) {
        resetSearchView()
        val intent = Intent(this@CitiesListActivity, CityDetailsActivity::class.java)
        intent.putExtra(Constants.INTENT_CityName, city.title)
        startActivity(intent)
    }

    override fun onInfoClicked(city: CityRecord) {
        resetSearchView()
        val intent = Intent(this@CitiesListActivity, CityHistoricalActivity::class.java)
        intent.putExtra(Constants.INTENT_CityName, city.title)
        startActivity(intent)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val city = data?.getParcelableExtra<CityRecord>(Constants.INTENT_OBJECT)!!
            when (requestCode) {
                Constants.INTENT_CREATE_City -> {
                    citiesViewModel.saveCity(city)
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        searchView = menu?.findItem(R.id.search_active_item)?.actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                citiesAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                citiesAdapter.filter.filter(newText)
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.search_active_item -> true

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        resetSearchView()
        super.onBackPressed()
    }

    private fun resetSearchView() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
    }
}
