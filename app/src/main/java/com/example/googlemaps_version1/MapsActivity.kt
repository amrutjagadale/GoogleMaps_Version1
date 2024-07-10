package com.example.googlemaps_version1

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.googlemaps_version1.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PolygonOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var markerPune : Marker
    private lateinit var markerMumbai : Marker
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        mapFragment.getMapAsync{
            mMap -> this@MapsActivity.mMap = mMap
            initializaMapSettings()
            addMarkers()
            addListeners()
            customInfoWindow()
            addListeners1()
            addShapesToMap()
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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private  fun customInfoWindow(){
        mMap.setInfoWindowAdapter(MyInfoWindowAdapter())
    }
    inner class  MyInfoWindowAdapter : InfoWindowAdapter{
        override fun getInfoContents(p0: Marker): View? {
            var view = LayoutInflater.from(this@MapsActivity).inflate(R.layout.info_window,null)
            view.findViewById<ImageView>(R.id.imgDefault).setImageResource(R.drawable.ic_launcher_background)
            view.findViewById<TextView>(R.id.txtTitle).text = p0.title
            return view
        }

        override fun getInfoWindow(p0: Marker): View? {
            Log.e("tag","${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}" )
            return null
        }
    }
    private fun addShapesToMap(){
     var circleOptions = CircleOptions()
     circleOptions.center(markerPune.position)
         .fillColor(Color.MAGENTA)
         .radius(1000.0)
         .strokeColor(Color.RED)
         .strokeWidth(10.0f)

        mMap.addCircle(CircleOptions())

        var polygonOptions  = PolygonOptions()
        polygonOptions.strokeColor(Color.argb(50,100,0,100))
        polygonOptions.strokeWidth(10.0f)
        polygonOptions.add(LatLng(19.076,72.877))
        polygonOptions.add(LatLng(19.977,73.7878))
        polygonOptions.add(LatLng(19.7645,74.4762))
        polygonOptions.add(LatLng(18.5204,73.8567))
        polygonOptions.fillColor(Color.BLUE)

        mMap.addPolygon(polygonOptions)
    }
    private fun addListeners1(){
        mMap.setOnMapClickListener (
            object : GoogleMap.OnMapClickListener{
                override fun onMapClick(p0: LatLng) {
                    mMap.addMarker(MarkerOptions()
                        .position(markerPune.position)
                        .title("dummy marker")
                        .snippet("this is the marker on map")
                    )

                    var cameraPosition = CameraPosition.builder()
                        .bearing(60f)
                        .tilt(30f)
                        .zoom(20f)
                        .target(markerPune.position)
                        .build()

                    var cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
                    mMap.moveCamera(cameraUpdate)
                    mMap.animateCamera(cameraUpdate,
                        3000,
                        object :GoogleMap.CancelableCallback{
                            override fun onCancel() {
                                TODO("Not yet implemented")
                            }

                            override fun onFinish() {
                                TODO("Not yet implemented")
                            }
                        })

                }
            }
        )
        mMap.setOnInfoWindowClickListener(
            object : GoogleMap.OnInfoWindowClickListener{
                override fun onInfoWindowClick(p0: Marker) {
                    Log.e("tag","${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}" )
                }
            }
        )
        mMap.setOnMarkerDragListener(
            object : GoogleMap.OnMarkerDragListener{
                override fun onMarkerDragStart(p0: Marker) {
                    Log.e("tag","${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}" )
                }

                override fun onMarkerDrag(p0: Marker) {
                    Log.e("tag","${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}" )
                }

                override fun onMarkerDragEnd(p0: Marker) {
                    Log.e("tag","${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}" )
                }
            }
        )
    }
    private fun addListeners(){
        //way 2
        mMap.setOnMarkerClickListener (
            object : GoogleMap.OnMarkerClickListener {
                override fun onMarkerClick(p0: Marker): Boolean {
                    Log.e("tag", "${p0.id} -- ${p0.position.latitude} -- ${p0.position.longitude}")
                    return false
                }
            }
        )
        //way -1
//        mMap.setOnMarkerClickListener {MyMarkerClickListener()}
    }
    inner class MyMarkerClickListener : GoogleMap.OnMarkerClickListener{
        override fun onMarkerClick(p0: Marker): Boolean {
            Log.e("tag","${p0.id}--${p0.position.latitude}-- ${p0.position.longitude}")
            return false
        }
    }
    private fun addMarkers(){
//        var markerOptionsPune = MarkerOptions()
//        markerOptionsPune.position(LatLng(18.5204,73.8567))
//        markerOptionsPune.title("Pune")
//        markerOptionsPune.alpha(6.05F)
//        markerOptionsPune.rotation(30.0F)
//        markerOptionsPune.draggable(true)
//        markerOptionsPune.visible(true)

        var iconForPune = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)

         mMap.addMarker(
             MarkerOptions().position(LatLng(18.5024,73.8567))
                 .draggable(true)
                 .title("Pune")
                 .visible(true)
                 .alpha(3.0f)
                 .rotation(15.0f)
                 .icon(iconForPune)
         )

//        markerPune = mMap.addMarker(markerOptionsPune)!!

        var mumbaiIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
        mMap.addMarker(
            MarkerOptions().position(LatLng(19.076,72.877))
                .draggable(true)
                .icon(mumbaiIcon)
                .alpha(3.0f)
                .rotation(45.0f)
                .visible(true)
                .title("Mumbai")
        )

    }

    @SuppressLint("MissingPermission")
    private fun initializaMapSettings(){
        mMap.isMyLocationEnabled =true
        mMap.isTrafficEnabled = true
        mMap.isBuildingsEnabled = true

        var uiSettings = mMap.uiSettings
        uiSettings.isMapToolbarEnabled = true
        uiSettings.isCompassEnabled = true
        uiSettings.isIndoorLevelPickerEnabled = true
        uiSettings.isRotateGesturesEnabled = true
        uiSettings.isScrollGesturesEnabled  = true
        uiSettings.isMyLocationButtonEnabled = true
        uiSettings.isScrollGesturesEnabledDuringRotateOrZoom = true
        uiSettings.isTiltGesturesEnabled = true
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isZoomGesturesEnabled = true
    }
}