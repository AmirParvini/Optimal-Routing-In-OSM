package com.example.osm.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import com.example.osm.Add_Attribute
import com.example.osm.MarkersList
import com.example.osm.R
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay


class MarkerOverlay(val context: Context): Overlay() {

    var projection: Projection? = null
    private var geopoint: GeoPoint? = null


    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {


        projection = mapView!!.projection
        geopoint = projection!!.fromPixels(e!!.x.toInt(), e.y.toInt()) as GeoPoint

        val intent = Intent(context, Add_Attribute()::class.java)
        intent.putExtra("X", geopoint!!.longitude.toString())
        intent.putExtra("Y",geopoint!!.latitude.toString())
        if (MarkersList.map!!.isClickable) {
            context.startActivity(intent)
        }

//        mapView.invalidate()
//        addmarker(context,mapView, geopoint!!)

        return true
    }




    @SuppressLint("UseCompatLoadingForDrawables")
    fun addmarker(context: Context, map: MapView, position: GeoPoint): Marker{

        val marker = Marker(map)
        marker.position = position
//        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.icon = context.resources.getDrawable(R.drawable.location)
        marker.title = "Latitude: " + position.latitude + "\n" + "Longitude: " + position.longitude
//        marker.setPanToView(true)
        map.overlays.add(marker)
        map.invalidate()
        return marker
    }


}

