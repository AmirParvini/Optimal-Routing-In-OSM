package com.example.osm

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.SharedPreferences
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline

object MarkersList {

    var map : MapView? = null
    var markers = ArrayList<OverlayItem> ()
    var itemizediconoverlay : ItemizedIconOverlay<OverlayItem>? = null
    var sharedPreferences: SharedPreferences? = null
    var SHAREDPREFERENCES_IS_CREAT = 0

    var FID = ArrayList<Int>()
    var ID = ArrayList<Int>()
    var length = ArrayList<Double>()
    var x_end = ArrayList<Double>()
    var x_start = ArrayList<Double>()
    var from_x_start_filtered = ArrayList<Double>()
    var from_y_start_filtered = ArrayList<Double>()
    var y_end = ArrayList<Double>()
    var y_start = ArrayList<Double>()
    var geometry_pathpoints = ArrayList<ArrayList<ArrayList<Double>>>()
    var BestWay = ArrayList<ArrayList<Int>>()
    var BestWayLength : Double = 0.0
    var BestWay_Polyline = Polyline()
    var A_matrix = Array(0){Array(0){0} }
    var x_center: Double? = null
    var y_center: Double? = null
    var condition = true
    var progressDialog: ProgressDialog? = null



}