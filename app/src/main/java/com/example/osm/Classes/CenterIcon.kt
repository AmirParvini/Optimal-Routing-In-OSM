package com.example.osm.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.osm.R
import com.fasterxml.jackson.databind.util.ClassUtil.getPackageName
import org.osmdroid.views.MapView
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.Overlay
import java.security.AccessControlContext

class CenterIcon(private val context: Context):Overlay() {

    @SuppressLint("DiscouragedApi")
    private val centerIcon = context.resources.getIdentifier("location.png","drawable",context.packageName)
    override fun draw(canvas: Canvas?, mapView: MapView?, pShadow: Boolean) {

        val projection = mapView!!.projection
        val centerPoint = projection.toPixels(mapView.mapCenter, null)
//        val iconWidth = centerIcon.intrinsicWidth
//        val iconHeight = centerIcon.intrinsicHeight
//        val left = centerPoint.x - iconWidth / 2
//        val top = centerPoint.y - iconHeight / 2
//        centerIcon.setBounds(left, top, left + iconWidth, top + iconHeight)
//        centerIcon.draw(canvas!!)
    }
}