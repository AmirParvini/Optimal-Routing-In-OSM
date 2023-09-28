package com.example.osm.Classes

import com.example.osm.MarkersList
import com.example.osm.MarkersList.from_x_start_filtered
import com.example.osm.MarkersList.from_y_start_filtered
import com.example.osm.MarkersList.x_start
import com.example.osm.MarkersList.y_start

class Create_Vertexes {
    
    fun create_vertexes() {

        val indexesrepeated = ArrayList<Int>()

        val xy_start = x_start.zip(y_start)
        val xy_start_filtered = ArrayList<Pair<Double,Double>>()

        for (i in xy_start) {
            xy_start_filtered.add(i)
            xy_start_filtered.forEachIndexed { index, value ->
                if (value == i) {
                    indexesrepeated.add(index)
                }
            }

            if (indexesrepeated.size > 1) {
                xy_start_filtered.removeAt(xy_start_filtered.size - 1)
            }
            indexesrepeated.clear()
        }




        xy_start_filtered.forEachIndexed { index, pair ->
            from_x_start_filtered.add(pair.first)
            from_y_start_filtered.add(pair.second)
        }


    }
}