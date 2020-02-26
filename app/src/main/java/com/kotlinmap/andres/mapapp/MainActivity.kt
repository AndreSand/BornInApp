package com.kotlinmap.andres.mapapp

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        Log.v("borninapp", "MainActivity: getAppStore: " + getAppStore(this))

        // Login if is from Google Play Store
        // create class to show getAppStore details
        // android:visibility="gone" below button is to test getAppStore()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Work in Progress", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            toast("borninapp" + "getAppStore: " + getAppStore(this))
            Log.v("borninapp", "MainActivity: getAppStore: " + getAppStore(this))
        }

        btGoToMap.setOnClickListener {
            //Get location1
            val locationName = edLocationName.text.toString()
            val coordinates = getLocationFromAddress(this, locationName)

//            toast("andres " + "lat " + coordinates!!.latitude + ", long :" + coordinates!!.longitude)
//            if (locationName1 == "") {
//                toast("setLat(): " + locationName1)
//            }

            // check if App it's from unknown market; kill app. No app sideload allowed.
            // toast("borninapp" + "getAppStore: " + getAppStore(this))
            Log.v("borninapp", "MainActivity: getAppStore: " + getAppStore(this))


            //Get location2
            val locationName2 = edLocationName2.text.toString()
            val coordinates2 = getLocationFromAddress(this, locationName2)

            //Get location2
            val locationName3 = edLocationName3.text.toString()
            val coordinates3 = getLocationFromAddress(this, locationName3)

            // Start map activity
            val i = Intent(this@MainActivity, MapsActivity::class.java)
            // i.putExtra("lat", 37.773972)
            i.putExtra("name", locationName)
            i.putExtra("lat", coordinates?.latitude.toString())
            i.putExtra("long", coordinates?.longitude.toString())

            //location 2
            i.putExtra("name2", locationName2)
            i.putExtra("lat2", coordinates2?.latitude.toString())
            i.putExtra("long2", coordinates2?.longitude.toString())

            //location 3
            i.putExtra("name3", locationName3)
            i.putExtra("lat3", coordinates3?.latitude.toString())
            i.putExtra("long3", coordinates3?.longitude.toString())

//            if (locationName == null|| locationName2 == null || locationName3 ==null) {
            if (!checkTextLength(edLocationName) || !checkTextLength(edLocationName2) || !checkTextLength(
                    edLocationName3
                )
            )
                toast("Fields cannot be empty!")
            else
                startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun checkTextLength(editText: EditText): Boolean {
        var length = editText.length()
        return length > 0
    }

    fun getLocationFromAddress(context: Context, inputtedAddress: String): LatLng? {

        val coder = Geocoder(context)
        val address: List<Address>?
        var resLatLng: LatLng? = null

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5)
            if (address == null) {
                return null
            }

            if (address.isEmpty()) {
                return null
            }

            val location = address[0]
            location.latitude
            location.longitude
//            toast("location " + location.getLatitude() + location.getLongitude())

            resLatLng = LatLng(location.latitude, location.longitude)

        } catch (ex: IOException) {

            ex.printStackTrace()
            toast("error")
        }
        return resLatLng
    }

    /**
     * Get the name of App Store the App is installed from.
     */
    fun getAppStore(context: Context): String {
        val pName = BuildConfig.APPLICATION_ID

        val packageManager = context.packageManager
        val installPM = packageManager.getInstallerPackageName(pName)
        if ("com.android.vending" == installPM) {
            // Installed from the Google Play
            return "Google Play"
        } else if ("com.amazon.venezia" == installPM) {
            // Installed from the Amazon Appstore
            return "Amazon Appstore"
        }
        return "unknown"
    }
}
