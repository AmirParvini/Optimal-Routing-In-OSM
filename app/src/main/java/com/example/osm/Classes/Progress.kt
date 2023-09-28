package com.example.osm.Classes

import android.app.ProgressDialog
import android.content.Context
import android.view.KeyEvent
import android.widget.ProgressBar
import com.example.osm.MarkersList.progressDialog

class Progress(val context: Context) {

    fun showProgressDialog(): Boolean {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.isIndeterminate = true
            progressDialog!!.setCancelable(false)
        }
        progressDialog!!.setMessage("در حال اسکن مسیر بهینه...")
        progressDialog!!.show()

        return true

    }


    fun dismissProgressDialog() {
        if (progressDialog != null ) {
            progressDialog!!.dismiss()
        }
    }
}