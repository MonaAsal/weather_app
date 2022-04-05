package com.planradar.cities.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.planradar.cities.R
import com.planradar.cities.data.CityRecord
import com.planradar.cities.utils.Constants
import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.content_add_city.*

class AddCityActivity : AppCompatActivity() {

    private var cityRecord: CityRecord? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        toolbar.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val intent = intent
        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            val city: CityRecord = intent.getParcelableExtra(Constants.INTENT_OBJECT)
            this.cityRecord = city
            prePopulateData(city)
            et_city_name.setSelection(et_city_name.text.length)
        }
        toolbar.title = if (cityRecord != null) getString(R.string.editCity) else getString(R.string.createCity)

    }

    private fun prePopulateData(city: CityRecord) {
        et_city_name.setText(city.title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_note -> {
                saveCity()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return true;
    }

    private fun saveCity() {
        if (validateFields()) {
            val id = if (cityRecord != null) cityRecord?.id else null
            val city = CityRecord(
                id = id, title = et_city_name.text.toString(),
                isArchived = 0
            )
            val intent = Intent()
            intent.putExtra(Constants.INTENT_OBJECT, city)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (et_city_name.text.isEmpty()) {
            til_city_name.error = getString(R.string.pleaseEnterCityName)
            et_city_name.requestFocus()
            return false
        }
        return true
    }

}
