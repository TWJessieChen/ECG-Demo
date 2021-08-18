package com.jc666.waveecglib.utils

import com.jc666.waveecglib.view.WaveEcgView
import java.util.*

class WaveEcgUtil {
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    var data = 0f

    /**
     * 模拟数据
     */
    fun showWaveData(waveShowView: WaveEcgView) {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                data = Random().nextFloat() * 20f - 10f
                waveShowView.showLine(data) //取得是-10到10间的浮点数
            }
        }
        //500表示调用schedule方法后等待500ms后调用run方法，50表示以后调用run方法的时间间隔
        timer!!.schedule(timerTask, 500, 50)
    }

    /**
     * 停止绘制
     */
    fun stop() {
        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
            timer = null
        }
        if (null != timerTask) {
            timerTask!!.cancel()
            timerTask = null
        }
    }
}