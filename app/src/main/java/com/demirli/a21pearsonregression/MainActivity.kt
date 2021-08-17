package com.demirli.a21pearsonregression

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    private var xList= arrayListOf<Int>()
    private var yList= arrayListOf<Int>()
    private var xAdapter: ArrayAdapter<Int>? = null
    private var yAdapter: ArrayAdapter<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapters()

        add_btn.setOnClickListener {
            addCoordinates()
        }

        calculate_btn.setOnClickListener {
            calculate()
        }
    }

    fun setAdapters(){
        xAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,xList)
        yAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,yList)
        main_x_listView.adapter = xAdapter
        main_y_listView.adapter = yAdapter
    }

    fun addCoordinates(){
        if (x_et.text.toString() != "" && y_et.text.toString() == ""){
            //xList.add(x_et.text.toString().toInt())
            Toast.makeText(this,"y coordinate is empty!",Toast.LENGTH_SHORT).show()
        }else if(x_et.text.toString() == "" && y_et.text.toString() != ""){
            //yList.add(y_et.text.toString().toInt())
            Toast.makeText(this,"x coordinate is empty!",Toast.LENGTH_SHORT).show()
        }else if(x_et.text.toString() != "" && y_et.text.toString() != ""){
            xList.add(x_et.text.toString().toInt())
            yList.add(y_et.text.toString().toInt())
        }else{
            Toast.makeText(this,"No value",Toast.LENGTH_SHORT).show()
        }

        xAdapter!!.notifyDataSetChanged()
        yAdapter!!.notifyDataSetChanged()
    }

    fun calculate(){
        if (xList.size != yList.size){
            Toast.makeText(this,"No same data size (x:${xList.size}, y:${yList.size})",Toast.LENGTH_SHORT).show()
        }else{
            var xy = arrayListOf<Int>()
            var x2 = arrayListOf<Int>()
            var y2 = arrayListOf<Int>()

            for(i in 0 until xList.size){
                xy.add(xList[i] * yList[i])
                x2.add(xList[i] * xList[i])
                y2.add(yList[i] * yList[i])
            }

            var sum_x = 0
            var sum_y = 0
            var sum_xy = 0
            var sum_x2 = 0
            var sum_y2 = 0

            for (i in 0 until xList.size){
                sum_x += xList[i]
                sum_y += yList[i]
                sum_xy += xy[i]
                sum_x2 += x2[i]
                sum_y2 += y2[i]
            }

            var step1 = ((xList.size * sum_xy) - (sum_x * sum_y)).toDouble()
            var step2 = ((xList.size * sum_x2) - (sum_x * sum_x)).toDouble()
            var step3 = ((xList.size* sum_y2) - (sum_y * sum_y)).toDouble()
            var step4 = Math.sqrt(step2 * step3);
            var result = step1 / step4
            var resultString = ""

            if(result == 0.0){
                resultString = "No Correlation"
            }else if(result == 1.0 || result == -1.0){
                resultString = "Full Correlation"
            }
            result_tv.setText("r=" + result.toString() + "  (" + resultString + ")")
        }
    }
}
