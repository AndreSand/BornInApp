package com.kotlinmap.andres.mapapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Add toolbar back soon, removing because xml layout issue
        // setSupportActionBar(toolbar)
        title = "Born In Map"
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        // 2019 fix https://www.codesd.com/item/kotlin-with-map-in-android.html add ?
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        bShare.setOnClickListener {
            // toast("share")
            val momBornIn = intent.getStringExtra("name")
            val dadBornIn = intent.getStringExtra("name2")
            val iWasBornIn = intent.getStringExtra("name3")

            //remove empty space
            val urlMomBornIn = momBornIn.replace(" ", "_")
            val urlDadBornIn = dadBornIn.replace(" ", "_")
            val urlIWasBornIn = iWasBornIn.replace(" ", "_")

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing born locations")
            shareIntent.putExtra(
                android.content.Intent.EXTRA_TEXT,
                "Mom born in " + momBornIn + " https://en.wikipedia.org/wiki/" + urlMomBornIn + " ,"
                        + " Dad born in " + dadBornIn + " https://en.wikipedia.org/wiki/" + urlDadBornIn + " ,"
                        + " I was born in " + iWasBornIn + " https://en.wikipedia.org/wiki/" + urlIWasBornIn + " ,"
                        + "\n"
                        + "\n"
                        + "Download the BornIn app here: "
                        + "https://play.google.com/store/apps/details?id=com.bornin.androidapp"
            )
            startActivity(Intent.createChooser(shareIntent, "share via"))
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //location 1
        //default "Mountain view CA"
        val name = intent.getStringExtra("name")
        val lat = intent.getStringExtra("lat").toDouble()
        val long = intent.getStringExtra("long").toDouble()

        //location 2
        //default "San Jose CA"
        val name2 = intent.getStringExtra("name2")
        val lat2 = intent.getStringExtra("lat2").toDouble()
        val long2 = intent.getStringExtra("long2").toDouble()


        //location 3
        //default San Francisco CA
        val name3 = intent.getStringExtra("name3")
        val lat3 = intent.getStringExtra("lat3").toDouble()
        val long3 = intent.getStringExtra("long3").toDouble()

        // Add a marker in SF and move the camera
//        val sF = LatLng(37.773972, -122.431297)
        val location1 = LatLng(lat, long)
        val location2 = LatLng(lat2, long2)
        val location3 = LatLng(lat3, long3)


//        mMap.addMarker(MarkerOptions().position(sF).title(name))
        mMap.addMarker(MarkerOptions().position(location1).title(name))
        mMap.addMarker(MarkerOptions().position(location2).title(name2))
        mMap.addMarker(MarkerOptions().position(location3).title(name3))

        // toast("andres name $name, name2 :$name2")

        // on marker onClick opens new activity
        mMap.setOnMarkerClickListener { marker ->
            //                val i = Intent(this@MapsActivity, FillinForm::class.java)
            //                startActivity(i)
            //                return false //true don't show  marker title
            val uri = Uri.parse("https://en.wikipedia.org/wiki/" + marker.title)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            false
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location1))
    }


}
