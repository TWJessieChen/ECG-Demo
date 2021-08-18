package com.jc666.waveecglib.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class WaveEcgView : View {
    private val NAMESPACE = "http://schemas.android.com/apk/res-auto"

    /**
     * 绘制模式
     */
    private var drawMode = 0

    /**
     * 宽高
     */
    private var mWidth = 0f
    private var mHeight = 0f

    /**
     * 网格画笔
     */
    private var mLinePaint: Paint? = null

    /**
     * 数据线画笔
     */
    private var mWavePaint: Paint? = null

    /**
     * 线条的路径
     */
    private var mPath: Path? = null

    /**
     * 保存已绘制的数据坐标
     */
    private lateinit var dataArray: FloatArray

    /**
     * 数据最大值，默认-20~20之间
     */
    private var MAX_VALUE = 20f

    /**
     * 线条粗细
     */
    private var WAVE_LINE_STROKE_WIDTH = 3f

    /**
     * 波形颜色
     */
    private var waveLineColor = Color.parseColor("#EE4000")

    /**
     * 当前的x，y坐标
     */
    private var nowX = 0f
    private var nowY = 0f
    private var startY = 0f

    /**
     * 线条的长度，可用于控制横坐标
     */
    private var WAVE_LINE_WIDTH = 10

    /**
     * 数据点的数量
     */
    private var row = 0
    private var draw_index = 0
    private var isRefresh = false

    /**
     * 网格是否可见
     */
    private var gridVisible = false

    /**
     * 网格的宽高
     */
    private val GRID_WIDTH = 50

    /**
     * 网格的横线和竖线的数量
     */
    private var gridHorizontalNum = 0
    private var gridVerticalNum = 0

    /**
     * 网格线条的粗细
     */
    private val GRID_LINE_WIDTH = 2

    /**
     * 网格颜色
     */
    private var gridLineColor = Color.parseColor("#1b4200")

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun init(attrs: AttributeSet?) {
        MAX_VALUE = attrs!!.getAttributeIntValue(NAMESPACE, "max_value", 20).toFloat()
        WAVE_LINE_WIDTH = attrs.getAttributeIntValue(NAMESPACE, "wave_line_width", 10)
        WAVE_LINE_STROKE_WIDTH =
            attrs.getAttributeIntValue(NAMESPACE, "wave_line_stroke_width", 3).toFloat()
        gridVisible = attrs.getAttributeBooleanValue(NAMESPACE, "grid_visible", true)
        drawMode = attrs.getAttributeIntValue(NAMESPACE, "draw_mode", NORMAL_MODE)
        val wave_line_color = attrs.getAttributeValue(NAMESPACE, "wave_line_color")
        if (wave_line_color != null && !wave_line_color.isEmpty()) {
            waveLineColor = Color.parseColor(wave_line_color)
        }
        val grid_line_color = attrs.getAttributeValue(NAMESPACE, "grid_line_color")
        if (grid_line_color != null && !grid_line_color.isEmpty()) {
            gridLineColor = Color.parseColor(grid_line_color)
        }
        val wave_background = attrs.getAttributeValue(NAMESPACE, "wave_background")
        if (wave_background != null && !wave_background.isEmpty()) {
            setBackgroundColor(Color.parseColor(wave_background))
        }
        mLinePaint = Paint()
        mLinePaint!!.style = Paint.Style.STROKE
        mLinePaint!!.strokeWidth = GRID_LINE_WIDTH.toFloat()
        /** 抗锯齿效果 */
        mLinePaint!!.isAntiAlias = true
        mWavePaint = Paint()
        mWavePaint!!.style = Paint.Style.STROKE
        mWavePaint!!.color = waveLineColor
        mWavePaint!!.strokeWidth = WAVE_LINE_STROKE_WIDTH
        /** 抗锯齿效果 */
        mWavePaint!!.isAntiAlias = true
        mPath = Path()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        /** 获取控件的宽高 */
        mWidth = measuredWidth.toFloat()
        mHeight = measuredHeight.toFloat()
        /** 根据网格的单位长宽，获取能绘制网格横线和竖线的数量 */
        gridHorizontalNum = (mHeight / GRID_WIDTH).toInt()
        gridVerticalNum = (mWidth / GRID_WIDTH).toInt()
        /** 根据线条长度，最多能绘制多少个数据点 */
        row = (mWidth / WAVE_LINE_WIDTH).toInt()
        dataArray = FloatArray(row)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /** 绘制网格 */
        if (gridVisible) {
            drawGrid(canvas)
        }
        when (drawMode) {
            0 -> drawWaveLineNormal(canvas)
            1 -> drawWaveLineLoop(canvas)
        }
        draw_index += 1
        if (draw_index >= row) {
            draw_index = 0
        }
    }

    /**
     * 常规模式绘制折线
     *
     * @param canvas
     */
    private fun drawWaveLineNormal(canvas: Canvas) {
        drawPathFromDatas(canvas, 0, row - 1)
        for (i in 0 until row - 1) {
            dataArray[i] = dataArray[i + 1]
        }
    }

    /**
     * 循环模式绘制折线
     *
     * @param canvas
     */
    private fun drawWaveLineLoop(canvas: Canvas) {
        drawPathFromDatas(
            canvas,
            if (row - 1 - draw_index > 8) 0 else 8 - (row - 1 - draw_index),
            draw_index
        )
        drawPathFromDatas(canvas, Math.min(draw_index + 8, row - 1), row - 1)
    }

    /**
     * 取数组中的指定一段数据来绘制折线
     *
     * @param start 起始数据位
     * @param end   结束数据位
     */
    private fun drawPathFromDatas(canvas: Canvas, start: Int, end: Int) {
        mPath!!.reset()
        startY = mHeight / 2 - dataArray[start] * (mHeight / (MAX_VALUE * 2))
        mPath!!.moveTo((start * WAVE_LINE_WIDTH).toFloat(), startY)
        for (i in start + 1 until end + 1) {
            if (isRefresh) {
                isRefresh = false
                return
            }
            nowX = (i * WAVE_LINE_WIDTH).toFloat()
            var dataValue = dataArray[i]
            /** 判断数据为正数还是负数  超过最大值的数据按最大值来绘制 */
            if (dataValue > 0) {
                if (dataValue > MAX_VALUE) {
                    dataValue = MAX_VALUE
                }
            } else {
                if (dataValue < -MAX_VALUE) {
                    dataValue = -MAX_VALUE
                }
            }
            nowY = mHeight / 2 - dataValue * (mHeight / (MAX_VALUE * 2))
            mPath!!.lineTo(nowX, nowY)
        }
        canvas.drawPath(mPath!!, mWavePaint!!)
    }

    /**
     * 绘制网格
     *
     * @param canvas
     */
    private fun drawGrid(canvas: Canvas) {
        /** 设置颜色 */
        mLinePaint!!.color = gridLineColor
        /** 绘制横线 */
        for (i in 0 until gridHorizontalNum + 1) {
            canvas.drawLine(
                0f, (i * GRID_WIDTH).toFloat(),
                mWidth, (i * GRID_WIDTH).toFloat(), mLinePaint!!
            )
        }
        /** 绘制竖线 */
        for (i in 0 until gridVerticalNum + 1) {
            canvas.drawLine(
                (i * GRID_WIDTH).toFloat(), 0f, (
                        i * GRID_WIDTH).toFloat(), mHeight, mLinePaint!!
            )
        }
    }

    /**
     * 添加新的数据
     */
    fun showLine(line: Float) {
        when (drawMode) {
            0 ->
                /** 常规模式数据添加至最后一位 */
                dataArray[row - 1] = line
            1 ->
                /** 循环模式数据添加至当前绘制的位 */
                dataArray[draw_index] = line
        }
        postInvalidate()
    }

    fun setMaxValue(max_value: Int): WaveEcgView {
        MAX_VALUE = max_value.toFloat()
        return this
    }

    fun setWaveLineWidth(width: Int): WaveEcgView {
        draw_index = 0
        WAVE_LINE_WIDTH = width
        row = (mWidth / WAVE_LINE_WIDTH).toInt()
        isRefresh = true
        dataArray = FloatArray(row)
        return this
    }

    fun setWaveLineStrokeWidth(width: Int): WaveEcgView {
        WAVE_LINE_WIDTH = width
        return this
    }

    fun setWaveLineColor(colorString: String?): WaveEcgView {
        waveLineColor = Color.parseColor(colorString)
        return this
    }

    fun setGridVisible(visible: Boolean): WaveEcgView {
        gridVisible = visible
        return this
    }

    fun setGridLineColor(colorString: String?): WaveEcgView {
        gridLineColor = Color.parseColor(colorString)
        return this
    }

    fun setWaveBackground(colorString: String?): WaveEcgView {
        setBackgroundColor(Color.parseColor(colorString))
        return this
    }

    fun setWaveDrawMode(draw_mode: Int): WaveEcgView {
        drawMode = draw_mode
        return this
    }

    companion object {
        /**
         * 常规绘制模式 不断往后推的方式
         */
        var NORMAL_MODE = 0

        /**
         * 循环绘制模式
         */
        var LOOP_MODE = 1
    }
}