package com.planradar.cities.ui.cities.citiesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.planradar.cities.R
import com.planradar.cities.data.CityRecord
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesListAdapter(cityEvents: CityEvents) : RecyclerView.Adapter<CitiesListAdapter.ViewHolder>(),
    Filterable {

    private var cities: List<CityRecord> = arrayListOf()
    private var filteredCityList: List<CityRecord> = arrayListOf()
    private val listener: CityEvents = cityEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredCityList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredCityList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(city: CityRecord, listener: CityEvents) {
            itemView.city_name_tv.text = city.title
            itemView.city_cell.setOnClickListener {
                listener.onViewClicked(city)
            }
            itemView.city_info_iv.setOnClickListener {
                listener.onInfoClicked(city)
            }
        }
    }

    fun setAllCities(cities: List<CityRecord>) {
        this.cities = cities
        this.filteredCityList = cities
        notifyDataSetChanged()
    }

    interface CityEvents {
        fun onItemDeleted(city: CityRecord, position: Int)
        fun onViewClicked(city: CityRecord)
        fun onInfoClicked(city: CityRecord)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredCityList = if (charString.isEmpty()) {
                    cities
                } else {
                    val filteredList = arrayListOf<CityRecord>()
                    for (row in cities) {
                        if (row.title!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredCityList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredCityList = p1?.values as List<CityRecord>
                notifyDataSetChanged()
            }

        }
    }

    fun deleteItem(position: Int) {
        listener.onItemDeleted(cities[position], position)
    }

}
