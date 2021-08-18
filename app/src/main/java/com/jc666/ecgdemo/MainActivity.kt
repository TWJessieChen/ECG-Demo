package com.jc666.ecgdemo

import android.os.Bundle
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import com.jc666.waveecglib.utils.WaveEcgUtil
import com.jc666.waveecglib.view.WaveEcgView

class MainActivity : AppCompatActivity() {

    private var waveUtil1: WaveEcgUtil? = null
    private var waveUtil2: WaveEcgUtil? = null
    private var waveUtil3: WaveEcgUtil? = null
    private var waveUtil4: WaveEcgUtil? = null
    private var waveUtil5: WaveEcgUtil? = null
    private var waveUtil6: WaveEcgUtil? = null
    private var waveUtil7: WaveEcgUtil? = null
    private var waveUtil8: WaveEcgUtil? = null
    private var waveUtil9: WaveEcgUtil? = null
    private var waveUtil10: WaveEcgUtil? = null

    private var wave_view_1: WaveEcgView? = null

    private var wave_view_2: WaveEcgView? = null

    private var wave_view_3: WaveEcgView? = null

    private var wave_view_4: WaveEcgView? = null

    private var wave_view_5: WaveEcgView? = null

    private var wave_view_6: WaveEcgView? = null

    private var wave_view_7: WaveEcgView? = null

    private var wave_view_8: WaveEcgView? = null

    private var wave_view_9: WaveEcgView? = null

    private var wave_view_10: WaveEcgView? = null
//
//    private var seekBar: SeekBar? = null

    private var gridLayout: GridLayout? = null

    private val totalCount: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wave_view_1 = findViewById(R.id.wave_view1)
//        wave_view_1!!.setWaveLineWidth(10)

        wave_view_2 = findViewById(R.id.wave_view2)
//        wave_view_2!!.setWaveLineWidth(20)

        wave_view_3 = findViewById(R.id.wave_view3)
//        wave_view_3!!.setWaveLineWidth(30)

        wave_view_4 = findViewById(R.id.wave_view4)
//        wave_view_4!!.setWaveLineWidth(40)

        wave_view_5 = findViewById(R.id.wave_view5)
//        wave_view_5!!.setWaveLineWidth(50)

        wave_view_6 = findViewById(R.id.wave_view6)
//        wave_view_6!!.setWaveLineWidth(60)

        wave_view_7 = findViewById(R.id.wave_view7)
//        wave_view_7!!.setWaveLineWidth(70)

        wave_view_8 = findViewById(R.id.wave_view8)
//        wave_view_8!!.setWaveLineWidth(80)

        wave_view_9 = findViewById(R.id.wave_view9)
//        wave_view_9!!.setWaveLineWidth(90)

        wave_view_10 = findViewById(R.id.wave_view10)
//        wave_view_10!!.setWaveLineWidth(100)


        waveUtil1 = WaveEcgUtil()
        waveUtil1!!.showWaveData(wave_view_1!!)
        waveUtil2 = WaveEcgUtil()
        waveUtil2!!.showWaveData(wave_view_2!!)
        waveUtil3 = WaveEcgUtil()
        waveUtil3!!.showWaveData(wave_view_3!!)
        waveUtil4 = WaveEcgUtil()
        waveUtil4!!.showWaveData(wave_view_4!!)
        waveUtil5 = WaveEcgUtil()
        waveUtil5!!.showWaveData(wave_view_5!!)
        waveUtil6 = WaveEcgUtil()
        waveUtil6!!.showWaveData(wave_view_6!!)
        waveUtil7 = WaveEcgUtil()
        waveUtil7!!.showWaveData(wave_view_7!!)
        waveUtil8 = WaveEcgUtil()
        waveUtil8!!.showWaveData(wave_view_8!!)
        waveUtil9 = WaveEcgUtil()
        waveUtil9!!.showWaveData(wave_view_9!!)
        waveUtil10 = WaveEcgUtil()
        waveUtil10!!.showWaveData(wave_view_10!!)







    }










}


//        gridLayout = findViewById<View>(R.id.tableGrid) as GridLayout
//
//        val column: Int = 2
//        val row: Int = totalCount / column
//
//        gridLayout!!.setColumnCount(column);
//        gridLayout!!.setRowCount(row + 1);
//
//        var c = 0
//        var r = 0
//        for(i in 0.. totalCount) {
//            if(c == column) {
//                c = 0
//                r++
//            }
//
//            val wave_view_loop = WaveEcgView(this)
//            val param = GridLayout.LayoutParams()
//            param.height = GridLayout.LayoutParams.WRAP_CONTENT
//            param.width = GridLayout.LayoutParams.WRAP_CONTENT
//            param.rightMargin = 5
//            param.topMargin = 5
//            param.setGravity(Gravity.CENTER)
//            param.columnSpec = GridLayout.spec(c)
//            param.rowSpec = GridLayout.spec(r)
//            wave_view_loop.setLayoutParams(param)
//            gridLayout!!.addView(wave_view_loop)
//
//            c++
//        }