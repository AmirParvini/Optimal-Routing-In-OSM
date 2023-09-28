package com.example.osm.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.osm.Interfaces.ProgressDismissListener
import com.example.osm.MarkersList
import com.example.osm.MarkersList.A_matrix
import com.example.osm.MarkersList.BestWay
import com.example.osm.MarkersList.BestWayLength
import com.example.osm.MarkersList.condition
import com.example.osm.MarkersList.length

class Create_A_Matrix {
    
    var Ways_Array_updated = ArrayList<ArrayList<Int>>()
    var Ways_Array = ArrayList<ArrayList<Int>>()
    var Streets_Array = ArrayList<ArrayList<Int>>()

    lateinit var showingbestway : Showing_Best_Way
    private var progressDialogDismissListener: ProgressDismissListener? = null

    var LOOP_IS_END = 1
    var Start: Double? = null
    var End: Double? = null


    fun setProgressDialogDismissListener(listener: ProgressDismissListener) {
        progressDialogDismissListener = listener
    }


    inner class Create_A_Matrix_Thread(val start: Double, val end: Double): Thread(){
        override fun run() {

            Start = start
            End = end


            if (A_matrix.isEmpty()) {


                A_matrix = Array(MarkersList.from_x_start_filtered.size) { Array(MarkersList.from_x_start_filtered.size) { 0 } }
                val from_x_start_filtered_index = ArrayList<Int>()
                val edge = ArrayList<Int>()
                var r = 0
                for (i in MarkersList.from_x_start_filtered) {

//                        Log.i("message", "from_x_start_filtered = $i")

                    MarkersList.x_start.forEachIndexed { index, value ->
                        if (value == i) {
                            edge.add(MarkersList.FID[index]) //خطوط متصل به گره iام
                        }
                    }
//                        Log.i("message", "edge = $edge")

                    for (j in edge) { // نشان میدهد که گره iام به کدام گره ها متصل است
                        from_x_start_filtered_index.add(
                            MarkersList.from_x_start_filtered.indexOf(
                                MarkersList.x_end[j]
                            )
                        )

                    }

//                        Log.i("message", "to_x_start_filtered_index = $to_x_start_filtered_index")

                    for (c in 0 until MarkersList.from_x_start_filtered.size) {
                        if (from_x_start_filtered_index.contains(c)) {
                            A_matrix[r][c] =
                                edge[from_x_start_filtered_index.indexOf(c)]

                        } else {
                            A_matrix[r][c] = -1
                        }
                    }



                    if (r < MarkersList.from_x_start_filtered.size - 1) {
                        r += 1
                    }
                    from_x_start_filtered_index.clear()
                    edge.clear()

                }


                Log.d("message", "A_Matrix = ${A_matrix[0]}")

            }

                FindBestWay_Thread().start()


            super.run()
        }
    }

//jugytdrt4wyolii

    inner class FindBestWay_Thread: Thread(){
        @SuppressLint("SuspiciousIndentation")
        override fun run() {
//            try {
//
//                Log.i("message", "A_Matrix = " + A_matrix.size.toString())
//
//                A_matrix[MarkersList.from_x_start_filtered.indexOf(Start)].forEachIndexed { index, value ->
//
//                    if (value != -1) {
//                        Ways_Array_updated.add(arrayListOf(value))
//
//                    }
//                }
//
//                Ways_Array.addAll(Ways_Array_updated)
//                Streets_Array.addAll(Ways_Array)
//
//                condition = true
//
//                while (condition) {
//
//                    Ways_Array_updated.clear()
//                    var Streets_Array_r = 0
//
//                    Loop@ for (i in Streets_Array.toList()) {
//                        Log.i("message", "i = " + i.toString())
//
//                        if (LOOP_IS_END == 1) {
//                            Streets_Array.clear()
//                            LOOP_IS_END = 0
//                        }
//                        for (j in i) {
//
//                            if (!condition) {
//                                break@Loop
//                            }
//                            val array = ArrayList<Int>()
//                            var f = 0
//
//                            MarkersList.A_matrix[findColumn(
//                                MarkersList.A_matrix,
//                                j
//                            )].forEachIndexed { index, value ->
//                                if (value != -1) {
//                                    f += 1
//                                    array.add(value)
//                                    Log.i("message", "value = $value")
//
//                                    Ways_Array[Streets_Array_r].add(value)
//                                    Ways_Array_updated.add(ArrayList(Ways_Array[Streets_Array_r].toList()))
//                                    Ways_Array[Streets_Array_r].removeAt(Ways_Array[Streets_Array_r].lastIndex)
//                                    MarkersList.x_end.forEachIndexed { index1, value1 ->
//                                        if (value1 == End) {
//                                            if (index1 == value) {
//                                                MarkersList.BestWay.add(ArrayList(Ways_Array_updated[Ways_Array_updated.lastIndex].toList()))
//                                                condition = false
//
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
////                            Log.i("message", "f = $f")
//                            Log.i(
//                                "message",
//                                "Ways_Array[Streets_Array_r] = ${Ways_Array[Streets_Array_r]}"
//                            )
//                            Log.i("message", "Ways_Array_updated = $Ways_Array_updated")
//
//                            Streets_Array.add(ArrayList(array.toList()))
//                            Log.i("message", "Streets_Array = " + Streets_Array.toString())
//                            Streets_Array_r += 1
//                            array.clear()
//
//                        }
//                    }
//
//                    LOOP_IS_END = 1
//                    Ways_Array = ArrayList(Ways_Array_updated.toList())
//                    Log.i("message", "Ways_Array = $Ways_Array")
//
//
//                }
//
//            }catch (e:Exception){
//                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
//            }
//
//            Ways_Array.clear()
//            Ways_Array_updated.clear()
//            Streets_Array.clear()
//
//            if (MarkersList.BestWay.size > 0) {
//                Log.i("message", "BestWay = ${MarkersList.BestWay[0]}")
//
//                showingbestway = Showing_Best_Way(progressDialogDismissListener!!)
//                showingbestway.showingbestway(MarkersList.BestWay[0])
//            }




//            try {

                Log.i("message", "A_Matrix = " + A_matrix.size.toString())

                A_matrix[MarkersList.from_x_start_filtered.indexOf(Start)].forEachIndexed { index, value ->

                    if (value != -1) {
                        Ways_Array_updated.add(arrayListOf(value))

                    }
                }

                Ways_Array.addAll(Ways_Array_updated)
                Streets_Array.addAll(Ways_Array)

                condition = true

                while (condition) {

                    Ways_Array_updated.clear()
                    var Streets_Array_r = 0

                    Loop@ for (i in Streets_Array.toList()) {
                        Log.i("message", "i = " + i.toString())

                        if (LOOP_IS_END == 1) {
                            Streets_Array.clear()
                            LOOP_IS_END = 0
                        }
                        for (j in i) {

                            if (!condition) {
                                break@Loop
                            }
                            val array = ArrayList<Int>()
                            var f = 0

                            A_matrix[findColumn(A_matrix, j)].forEachIndexed { index, value ->
                                if (value != -1) {
                                    f += 1

                                    if (!Ways_Array[Streets_Array_r].contains(value)){
                                        array.add(value)
                                        Ways_Array[Streets_Array_r].add(value)
                                        Ways_Array_updated.add(ArrayList(Ways_Array[Streets_Array_r].toList()))
                                        Ways_Array[Streets_Array_r].removeAt(Ways_Array[Streets_Array_r].lastIndex)
                                    }



                                    if (BestWayLength > 0.0){
                                        var  Ways_Array_updated_lastindexed_length1 = 0.0
                                        if (Ways_Array_updated.size > 0 ) {
                                            Ways_Array_updated[Ways_Array_updated.size - 1].forEachIndexed { index, FID ->
                                                val Ways_Array_updated_lastindexed_length =
                                                    length[FID]
                                                Ways_Array_updated_lastindexed_length1 += Ways_Array_updated_lastindexed_length
                                            }
                                        }

                                        if (Ways_Array_updated_lastindexed_length1 > BestWayLength){
                                            if (array.size > 0 && Ways_Array_updated.size > 0) {
                                                array.removeAt(array.lastIndex)
                                                Ways_Array_updated.removeAt(Ways_Array_updated.lastIndex)
                                            }
                                        }

                                    }

                                    MarkersList.x_end.forEachIndexed { index1, value1 ->

                                            if (value1 == End && index1 == value) {
                                                if (array.size > 0 && Ways_Array_updated.size > 0){
                                                    BestWay.add(ArrayList(Ways_Array_updated[Ways_Array_updated.lastIndex].toList()))
                                                    array.removeAt(array.lastIndex)
                                                    Ways_Array_updated.removeAt(Ways_Array_updated.lastIndex)
                                                }

                                                var sumlength1 = 0.0
                                                BestWay[BestWay.size-1].forEachIndexed { index, FID ->
                                                    val sumlength = length[FID]
                                                    sumlength1 += sumlength
                                                }
                                                 BestWayLength = sumlength1

                                            }
                                    }
                                }
                            }

//                            Log.i("message", "f = $f")
                            Log.i("message", "Ways_Array[Streets_Array_r] = ${Ways_Array[Streets_Array_r]}")
                            Log.i("message", "Ways_Array_updated = $Ways_Array_updated")
                            Log.i("message", "Ways_Array_updatedSize = ${Ways_Array_updated.size}")


                            Streets_Array.add(ArrayList(array.toList()))
                            Log.i("message", "Streets_Array = " + Streets_Array.toString())
                            Streets_Array_r += 1
                            array.clear()

                        }
                    }

                    if (Ways_Array_updated.size == 0){
                        condition = false
                    }


                    LOOP_IS_END = 1
                    Ways_Array = ArrayList(Ways_Array_updated.toList())


                }

//            }catch (e:Exception){
//                Log.e("ExceptionError",e.message.toString())
//            }

            Log.i("message", "BestWay = " + BestWayLength.toString())
            Ways_Array.clear()
            Ways_Array_updated.clear()
            Streets_Array.clear()
            BestWayLength = 0.0

            if (BestWay.size > 0) {
                Log.i("message", "BestWay = $BestWay")
                showingbestway = Showing_Best_Way(progressDialogDismissListener!!)
                showingbestway.showingbestway(BestWay[BestWay.size-1])
            }




            super.run()
        }



    }





    @SuppressLint("SuspiciousIndentation")
    fun findColumn(matrix: Array<Array<Int>>, target: Int): Int {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size)
            if (target == matrix[i][j]) {
                return j
            }
        }
        return -1
    }


}

