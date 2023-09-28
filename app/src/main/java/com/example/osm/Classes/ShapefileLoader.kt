package com.example.osm.Classes

import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import org.osmdroid.api.IGeoPoint
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import java.io.IOException
import java.io.InputStream
import java.util.Scanner


class ShapefileLoader {

    private val TAG = "ShapefileLoader"

    fun loadAndDisplayShapefile(context: Context, mapView: MapView, shapefileName: String) {
        try {
            // Load the GeoJSON from the shapefile
            val geoJson = loadGeoJsonFromShapefile(context, shapefileName)

            // Extract the polyline coordinates from the GeoJSON
            val polyline = extractPolylineFromGeoJson(geoJson)

            // Add the polyline to the map view
            mapView.overlayManager.add(polyline)

            // Refresh the map view to update the display
            mapView.invalidate()
        } catch (e: IOException) {
            Log.e(TAG, "Error loading shapefile: " + e.message)
            e.printStackTrace()
        } catch (e: JSONException) {
            Log.e(TAG, "Error loading shapefile: " + e.message)
            e.printStackTrace()
        }
    }

    @Throws(IOException::class, JSONException::class)
    private fun loadGeoJsonFromShapefile(context: Context, shapefileName: String): JSONObject {
        // Read the shapefile as an InputStream
        val inputStream: InputStream = context.assets.open(shapefileName)

        // Convert the InputStream to a String
        val jsonString = convertStreamToString(inputStream)

        // Create a JSONObject from the GeoJSON string
        return JSONObject(jsonString)
    }

    @Throws(JSONException::class)
    private fun extractPolylineFromGeoJson(geoJson: JSONObject): Polyline {
        // Get the "features" array from the GeoJSON
        val features = geoJson.getJSONArray("features")

        // Assume there's only one feature in the GeoJSON
        val feature = features.getJSONObject(0)

        // Get the "geometry" object from the feature
        val geometry = feature.getJSONObject("geometry")

        // Get the "coordinates" array from the geometry
        val coordinates = geometry.getJSONArray("coordinates")

        // Create a Polyline object
        val polyline = Polyline()

        // Loop through the coordinates and add them to the polyline
        for (i in 0 until coordinates.length()) {
            val coordinate = coordinates.getJSONArray(i)
            val longitude = coordinate.getDouble(0)
            val latitude = coordinate.getDouble(1)
            val point: IGeoPoint = GeoPoint(latitude, longitude)
            polyline.addPoint(point as GeoPoint?)
        }
        return polyline
    }

    @Throws(IOException::class)
    private fun convertStreamToString(inputStream: InputStream): String {
        val scanner: Scanner = Scanner(inputStream).useDelimiter("\\A")
        return if (scanner.hasNext()) scanner.next() else ""
    }
}