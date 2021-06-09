package com.mahantesh.happyplaces.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahantesh.happyplaces.R
import com.mahantesh.happyplaces.model.HappyPlaces
import kotlinx.android.synthetic.main.activity_happy_place_detail.*

class HappyPlaceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_place_detail)

        var happyPlaceDetail: HappyPlaces? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            happyPlaceDetail =
                intent.getParcelableExtra<HappyPlaces>(
                    MainActivity.EXTRA_PLACE_DETAILS) as HappyPlaces
        }

        if (happyPlaceDetail != null) {
            setSupportActionBar(tbPlaceDetail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = happyPlaceDetail.title

            tbPlaceDetail.setNavigationOnClickListener {
                onBackPressed()
            }

            ivPlaceImage.setImageURI(Uri.parse(happyPlaceDetail.image))
            tvDescription.text = happyPlaceDetail.description
            tvLocation.text = happyPlaceDetail.location

            btnViewOnMap.setOnClickListener {
                val intent = Intent(this, MapActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, happyPlaceDetail)
                startActivity(intent)
            }
        }
    }
}