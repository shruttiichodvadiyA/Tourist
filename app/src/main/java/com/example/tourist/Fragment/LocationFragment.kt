package com.example.tourist.Fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.AvoidType
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.model.Route
import com.akexorcist.googledirection.util.DirectionConverter
import com.example.tourist.R
import com.example.tourist.databinding.FragmentLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import java.util.*


class LocationFragment : Fragment() {
    lateinit var mMap: GoogleMap
    lateinit var binding: FragmentLocationBinding
    lateinit var myLocation: LatLng
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var polyline: Polyline? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentLocationBinding.inflate(layoutInflater)

        return binding.root
    }

      fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        binding.btnsearch.setOnClickListener {
            latitude = binding.edtLocation.text.toString().toDouble()

            onMapReady(mMap)

            val sydney = LatLng(latitude, longitude)

            val addresses: List<Address>?
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            )

            addresses!![0].getAddressLine(0)
            val knownName: String = addresses!![0].featureName

            mMap.addMarker(MarkerOptions().position(sydney).title(knownName))
            val zoomLevel = 15.0f
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel))

//            findLocation()


        }
        myLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(myLocation).title("sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))

        mMap.setOnMapClickListener {

            drawMarker(it, false)
            drawline(it.latitude, it.longitude, TransportMode.DRIVING)

        }
    }

    private fun drawMarker(latLng: LatLng, flag: Boolean) {
        if (mMap != null) {
            mMap!!.clear()
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
//                        markerOptions.title(title);
            if (flag) {
//                markerOptions.icon(BitmapFromVector(this, R.drawable.ic_map_event));
                markerOptions.icon(BitmapFromVector(context, R.drawable.download))
            } else {
                Log.e("TAG", "drawMarker: mark")
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            }
            mMap!!.addMarker(markerOptions)
            //            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))


        }
    }

    private fun BitmapFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun drawline(lat: Double, longt: Double, mode: String) {
        try {
//            drawMarker(LatLng(lat, longt), false)
            GoogleDirection.withServerKey("AIzaSyBv6cUUv3hbIEDcG69F297b37KqrTjepSg")
                .from(LatLng(myLocation!!.latitude, myLocation!!.longitude)).to(LatLng(lat, longt))
                .avoid(AvoidType.FERRIES).avoid(AvoidType.HIGHWAYS).transportMode(mode)
                .execute(object : DirectionCallback {
                    override fun onDirectionSuccess(direction: Direction?) {
                        directionsuccess(direction)
                    }

                    override fun onDirectionFailure(t: Throwable) {}
                })
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "drawline:exce " + e.message)
        }
    }

    private fun directionsuccess(direction: Direction?) {
        try {
            if (direction!!.isOK) {
                val route = direction.routeList[0]
                if (route != null && !route.legList.isEmpty()) {
                    val distance = route.legList[0].distance
                    val duration = route.legList[0].duration
                    val directionPositionList = route.legList[0].directionPoint
                    if (!directionPositionList.isEmpty()) {
                        if (polyline != null) {
                            polyline!!.remove()
                        }
                        polyline = mMap!!.addPolyline(
                            DirectionConverter.createPolyline(requireContext(), directionPositionList, 9, ContextCompat.getColor(
                                requireContext(), R.color.purple_700)))
                        setCameraWithCoordinationBounds(route)
                    } else {
//                        Toast.makeText(this, "no route available", Toast.LENGTH_SHORT).show()
                    }
                } else {
//                    Toast.makeText(this, "no route available", Toast.LENGTH_SHORT).show()
                }
            } else {
//                Toast.makeText(this, "no route available", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setCameraWithCoordinationBounds(route: Route) {
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}

