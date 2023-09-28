package com.example.osm

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.OverlayItem

class Add_Attribute() : AppCompatActivity() {

    var x: TextView ?= null
    var y: TextView ?= null

    var incidenttype: EditText? = null
    var num_of_loss: EditText? = null
    var num_of_ulcerouses: EditText? = null

    var demolition_rate: EditText? = null
    var economic_injury: EditText? = null

    var AddBtn : Button? = null
    var CancelBtn : Button? = null


    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_attribute)

        x = findViewById(R.id.textView1)
        y = findViewById(R.id.textView3)
        incidenttype = findViewById(R.id.editTextText1)
        num_of_loss = findViewById(R.id.editTextText2)
        num_of_ulcerouses = findViewById(R.id.editTextText3)
        demolition_rate = findViewById(R.id.editTextText4)
        economic_injury = findViewById(R.id.editTextText5)
        AddBtn = findViewById(R.id.addmarkerbtn_id)
        CancelBtn = findViewById(R.id.cancelbtn_id)

        x!!.text = intent.getStringExtra("X")
        y!!.text = intent.getStringExtra("Y")

        MarkersList.sharedPreferences = applicationContext.getSharedPreferences("markers", MODE_PRIVATE)
        val editor = MarkersList.sharedPreferences!!.edit()



        AddBtn!!.setOnClickListener {

            if (num_of_loss!!.text.toString() == ""){
                num_of_loss!!.setText("0")
            }
            if (num_of_ulcerouses!!.text.toString() == ""){
                num_of_ulcerouses!!.setText("0")
            }
            if (economic_injury!!.text.toString() == ""){
                economic_injury!!.setText("0")
            }
            if (demolition_rate!!.text.toString() == ""){
                demolition_rate!!.setText("0")
            }

            val geopoint = GeoPoint(intent.getStringExtra("Y")!!.toDouble(),intent.getStringExtra("X")!!.toDouble())
            val marker = OverlayItem("طول جغرافیایی: " + x!!.text + "\n" + "عرض جغرافیایی: " + y!!.text + "\n" +
                    "نوع حادثه: " + incidenttype!!.text + "\n" + "تعداد کشته ها: " + num_of_loss!!.text + "\n" +
                    "تعداد مجروحان: " + num_of_ulcerouses!!.text + "\n" + "درصد تخریب: " + "%" + demolition_rate!!.text + "\n" +
                    "میزان خسارت اقتصادی: " + economic_injury!!.text + " تومان", "_", geopoint)
//            val myicon = this.resources.getDrawable(R.drawable.location)
//            marker.setMarker(myicon)

            MarkersList.markers.add(marker)
//            MarkersList.markersforjson.add(marker)

            val json = Gson().toJson(MarkersList.markers)
            editor.putString("Markers",json)
            editor.apply()

            MarkersList.SHAREDPREFERENCES_IS_CREAT = 1

            this.finish()

        }

        CancelBtn!!.setOnClickListener {
            this.finish()
        }


    }
}