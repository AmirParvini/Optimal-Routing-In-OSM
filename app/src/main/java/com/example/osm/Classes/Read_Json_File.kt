package com.example.osm.Classes

import android.content.res.AssetManager
import com.example.osm.MarkersList
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat

class Read_Json_File {

    fun readJsonFile(assetManager: AssetManager, fileName: String) {
//        try {

        val inputStream = assetManager.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var line: String? = reader.readLine()


        while (line != null) {
            stringBuilder.append(line)
            line = reader.readLine()

        }
        reader.close()
        inputStream.close()
        val jsonString = stringBuilder.toString()

        val jsonObject = JSONObject(jsonString)
        val featuresArray = jsonObject.getJSONArray("features")

        val df = DecimalFormat("#.##############")
        for (i in 0 until featuresArray.length()) {
            val featureObject = featuresArray.getJSONObject(i)

            val attributes = featureObject.getJSONObject("attributes")

            val geometry = featureObject.getJSONObject("geometry")
            val paths = geometry.getJSONArray("paths")
            val path = paths.getJSONArray(0)

            val pathpoints = ArrayList<ArrayList<Double>>()
            for (j in 0 until path.length()){
                val x = path.getJSONArray(j).getDouble(0)
                val y = path.getJSONArray(j).getDouble(1)

                pathpoints.add(arrayListOf(x,y))
            }

            MarkersList.geometry_pathpoints.add(pathpoints)
            MarkersList.FID.add(attributes.getInt("FID"))
            MarkersList.ID.add(attributes.getInt("ID"))
            MarkersList.length.add(df.format(attributes.getDouble("length")).toDouble())
            MarkersList.x_end.add(df.format(attributes.getDouble("x_end")).toDouble())
            MarkersList.x_start.add(df.format(attributes.getDouble("x_start")).toDouble())
            MarkersList.y_end.add(df.format(attributes.getDouble("y_end")).toDouble())
            MarkersList.y_start.add(df.format(attributes.getDouble("y_start")).toDouble())


        }
//        }catch (e:Exception){
//             Toast.makeText(this@OSM_Activity,e.message,Toast.LENGTH_LONG).show()
//            }
    }

}