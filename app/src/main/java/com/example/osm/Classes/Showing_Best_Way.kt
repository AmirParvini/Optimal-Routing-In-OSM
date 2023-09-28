package com.example.osm.Classes

import com.example.osm.Interfaces.ProgressDismissListener
import com.example.osm.MarkersList
import com.example.osm.MarkersList.BestWay
import com.example.osm.MarkersList.BestWay_Polyline
import com.example.osm.MarkersList.geometry_pathpoints
import com.example.osm.MarkersList.map
import org.osmdroid.util.GeoPoint


class Showing_Best_Way(var DismissProgressListener : ProgressDismissListener) {


    fun showingbestway(bestway: ArrayList<Int>){


        for (i in bestway){
            geometry_pathpoints[i].forEachIndexed { index, value:ArrayList<Double> ->

                val x = value[0]
                val y = value[1]

                BestWay_Polyline.addPoint(GeoPoint(y,x))
            }


        }
        BestWay.clear()


        map!!.overlays.add(BestWay_Polyline)
        DismissProgressListener.dismissprogresslistener()

    }

}
