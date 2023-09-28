package com.example.osm

import android.annotation.SuppressLint
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.osm.Classes.Create_A_Matrix
import com.example.osm.Classes.Create_Vertexes
import com.example.osm.Classes.MarkerOverlay
import com.example.osm.Classes.Progress
import com.example.osm.Classes.Read_Json_File
import com.example.osm.Interfaces.ProgressDismissListener
import com.example.osm.MarkersList.BestWay
import com.example.osm.MarkersList.BestWay_Polyline
import com.example.osm.MarkersList.condition
import com.example.osm.MarkersList.from_x_start_filtered
import com.example.osm.MarkersList.from_y_start_filtered
import com.example.osm.MarkersList.map
import com.example.osm.MarkersList.progressDialog
import com.example.osm.MarkersList.x_center
import com.example.osm.MarkersList.y_center
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import org.geotools.mbstyle.expression.MBExpression.math
import org.json.JSONArray
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.Polyline
import kotlin.math.abs
import kotlin.math.sqrt


class OSM_Activity : AppCompatActivity(), ProgressDismissListener{


    private var mapController: IMapController? = null
    private val TAG = "OsmActivity"
    var markeroverlay = MarkerOverlay(this)
    var editor: Editor? = null
    var dialog: BottomSheetDialog? = null
    val create_A_matrix = Create_A_Matrix()
    var navigationbtn: ImageView? = null
    var centericon: ImageView? = null
    var choosestartpoint: LinearLayout? = null
    var chooseendpoint: LinearLayout? = null
    var choosestartpoint_btn: Button? = null
    var chooseendpoint_btn: Button? = null
    var xstart: Double? = null
    var ystart: Double? = null
    var xend: Double? = null
    var yend: Double? = null
    var creatvertexes = Create_Vertexes()
    var readjsonfile = Read_Json_File()
    var progress = Progress(this)


//    var centericon = CenterIcon(this)


    @SuppressLint("CommitPrefEdits", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        setContentView(R.layout.activity_main)




        readjsonfile.readJsonFile(assets,"Street_Region12_FeaturesToJS2_WGS84.json")




        navigationbtn = findViewById(R.id.navigation_id)
        centericon = findViewById(R.id.centericon_id)
        choosestartpoint = findViewById(R.id.choosestartpoint_id)
        choosestartpoint_btn = findViewById(R.id.choosestartpoint_btn_id)
        chooseendpoint = findViewById(R.id.chooseendpoint_id)
        chooseendpoint_btn = findViewById(R.id.chooseendpoint_btn_id)

        dialog = BottomSheetDialog(this@OSM_Activity)

        if (Build.VERSION.SDK_INT >= 23) {
            isStoragePermissionGranted()
        }


        map = findViewById(R.id.mapView)
        map!!.setTileSource(TileSourceFactory.MAPNIK)
        map!!.setBuiltInZoomControls(true)
        map!!.setMultiTouchControls(true)
        mapController = map?.controller
        mapController!!.setZoom(17.0)
        map!!.overlays.add(markeroverlay)
        val startPoint = GeoPoint(MarkersList.y_start[22], MarkersList.x_start[22])
        mapController!!.setCenter(startPoint)




        MarkersList.sharedPreferences = applicationContext.getSharedPreferences("markers", MODE_PRIVATE)
        if (MarkersList.sharedPreferences!!.getString("Markers", "") != "") {

            val json = JSONArray(MarkersList.sharedPreferences!!.getString("Markers", ""))
            for (i in 0 until json.length()) {
                val jsonobject = json.getJSONObject(i)
                val jsongeopoint = jsonobject.getJSONObject("mGeoPoint")
                val jsonlatitude = jsongeopoint.getDouble("mLatitude")
                val jsonlongitude = jsongeopoint.getDouble("mLongitude")
                val geoPoint = GeoPoint(jsonlatitude, jsonlongitude)
                val jsontitle = jsonobject.getString("mTitle")
                val marker = OverlayItem(jsontitle, "", geoPoint)
                MarkersList.markers.add(marker)
            }
        }





        creatvertexes.create_vertexes()





        navigationbtn!!.setOnClickListener {

            if (BestWay_Polyline.points.size > 0){
                BestWay_Polyline.actualPoints.clear()
                map!!.overlays.remove(BestWay_Polyline)
            }
            map!!.isClickable = false
            navigationbtn!!.isVisible = false
            centericon!!.isVisible = true

            choosestartpoint!!.isVisible = true
        }




        choosestartpoint_btn!!.setOnClickListener {

            val get_ystart_center = map!!.mapCenter.latitude
            val get_xstart_center = map!!.mapCenter.longitude



            val x_nearArray = ArrayList<Double>()
            val y_nearArray = ArrayList<Double>()
            val sqrt_xy_nearArray = ArrayList<Double>()

            from_x_start_filtered.forEachIndexed { index, v ->
                x_nearArray.add(Math.pow((v - get_xstart_center),2.0))
            }

            from_y_start_filtered.forEachIndexed { index, v ->
                y_nearArray.add(Math.pow((v - get_ystart_center),2.0))
            }

            val xy_nearArray = ArrayList(x_nearArray.zip(y_nearArray).map { (a,b) -> a+b })

            xy_nearArray.forEachIndexed { index, v ->
                sqrt_xy_nearArray.add(sqrt(v))
            }

            val sqrt_xy_nearArray_min = sqrt_xy_nearArray.min()
            x_center = from_x_start_filtered[sqrt_xy_nearArray.indexOf(sqrt_xy_nearArray_min)]
            y_center = from_y_start_filtered[from_x_start_filtered.indexOf(from_x_start_filtered[sqrt_xy_nearArray.indexOf(sqrt_xy_nearArray_min)])]
            xstart = x_center



            choosestartpoint!!.isVisible = false
            chooseendpoint!!.isVisible = true

        }


        chooseendpoint_btn!!.setOnClickListener {

            progress.showProgressDialog()

            progressDialog!!.setOnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {

                    condition = false
                    choosestartpoint!!.isVisible = false
                    navigationbtn!!.isVisible = true
                    centericon!!.isVisible = false
                    map!!.isClickable = true
                    progress.dismissProgressDialog()
                    Toast.makeText(this,"عملیات مسیریابی لغو شد!",Toast.LENGTH_SHORT).show()

                    true
                } else {
                    false
                }
            }

            val get_yend_center = map!!.mapCenter.latitude
            val get_xend_center = map!!.mapCenter.longitude

            val x_nearArray = ArrayList<Double>()
            val y_nearArray = ArrayList<Double>()
            val sqrt_xy_nearArray = ArrayList<Double>()

            from_x_start_filtered.forEachIndexed { index, v ->
                x_nearArray.add(Math.pow((v - get_xend_center),2.0))
            }

            from_y_start_filtered.forEachIndexed { index, v ->
                y_nearArray.add(Math.pow((v - get_yend_center),2.0))
            }

            val xy_nearArray = ArrayList(x_nearArray.zip(y_nearArray).map { (a,b) -> a+b })
            xy_nearArray.forEachIndexed { index, v ->
                sqrt_xy_nearArray.add(sqrt(v))
            }
            val sqrt_xy_nearArray_min = sqrt_xy_nearArray.min()
            xend = from_x_start_filtered[sqrt_xy_nearArray.indexOf(sqrt_xy_nearArray_min)]


            chooseendpoint!!.isVisible = false

            create_A_matrix.setProgressDialogDismissListener(this)
            create_A_matrix.Create_A_Matrix_Thread(xstart!!,xend!!).start()


        }


    }




    override fun onResume() {

            MarkersList.itemizediconoverlay = ItemizedIconOverlay(
                this,
                MarkersList.markers,
                object : OnItemGestureListener<OverlayItem> {
                    @SuppressLint("InflateParams", "MissingInflatedId")
                    override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {

                        val view = layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)
                        dialog!!.setContentView(view)
                        dialog!!.setCancelable(true)
                        dialog!!.show()

                        val title = view.findViewById<TextView>(R.id.textview1)

                        val btndelete= view.findViewById<Button>(R.id.deletebtn_id)
                        val btnedit = view.findViewById<Button>(R.id.editmarkerbtn_id)

                        title.text = MarkersList.markers[index].title.toString()


                        btndelete.setOnClickListener {

                            runOnUiThread{
                                MarkersList.itemizediconoverlay!!.removeItem(item)
                                map!!.overlays.clear()
                                map!!.overlays.add(markeroverlay)
                                map!!.overlays.add(MarkersList.itemizediconoverlay)
                                map!!.overlays.add(BestWay_Polyline)

                                val jsonrefresh = Gson().toJson(MarkersList.markers)
                                editor = MarkersList.sharedPreferences!!.edit()
                                editor!!.putString("Markers",jsonrefresh)
                                editor!!.apply()
                                dialog!!.dismiss()
                            }


                        }

                        btnedit.setOnClickListener {


                        }


                        return true
                    }

                    override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                        return false
                    }
                })

            map!!.overlays.add(MarkersList.itemizediconoverlay)


        super.onResume()

    }



    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        if (progressDialog != null && progressDialog!!.isShowing){
            progress.dismissProgressDialog()
        }

        if (navigationbtn!!.isVisible) {
            super.onBackPressed()
        }
        else{
            if (chooseendpoint!!.isVisible){
                chooseendpoint!!.isVisible = false
                choosestartpoint!!.isVisible = true
            }
            else if (choosestartpoint!!.isVisible){
                choosestartpoint!!.isVisible = false
                navigationbtn!!.isVisible = true
                centericon!!.isVisible = false
                map!!.isClickable = true
            }

        }
    }




    fun isStoragePermissionGranted(): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

                Log.v(TAG, "Permission is granted")
                return true
            }
            else {

                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(this,
                    arrayOf(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1)
                return false
            }

        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
            return true
        }

    }




    override fun dismissprogresslistener() {
        progress.dismissProgressDialog()

        runOnUiThread {
            centericon!!.isVisible = false
            navigationbtn!!.isVisible = true
            map!!.isClickable = true

//            mapController!!.setZoom(17.0)
//            mapController!!.animateTo(GeoPoint(y_center!!, x_center!!))
        }

    }


}