package android.countrinfo

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private var adr: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setSupportActionBar(toolbar)
        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_maps_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemGoToList) {
            val intent = Intent(this, InfoListActivity::class.java)
            intent.putExtra("fromMarker", false)
            startActivity(intent)
        }
        if (item.itemId == R.id.itemExitApp) {
            finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
        myMap.setOnMapClickListener {
            try {
                myMap.clear()
                val gc = Geocoder(this, Locale.ENGLISH)
                val addrs: List<Address>? = gc.getFromLocation(it.latitude, it.longitude, 1)
                adr = addrs?.get(0)
                val marker =
                    myMap.addMarker(MarkerOptions().position(it).title(adr?.locality).snippet(adr?.countryName))
                marker.isDraggable = false
                marker.showInfoWindow()
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Map could not load, check your internet connection.",
                    Toast.LENGTH_LONG
                ).show()

            }
            myMap.setOnInfoWindowClickListener {
                val popupMapFragment = PopupMapFragment()
                val bundle = Bundle()
                bundle.putString("countryName", adr?.countryName)
                popupMapFragment.arguments = bundle
                popupMapFragment.show(supportFragmentManager, "TAG")
            }
        }
    }
}