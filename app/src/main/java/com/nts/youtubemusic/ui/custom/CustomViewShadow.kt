package com.nts.youtubemusic.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.nts.youtubemusic.utils.Utils

class  CustomViewShadow : View {
    private val SIZE_4 = Utils.dpToPx(4)
    private var paint: Paint? = null

    constructor(context: Context?) : super(context) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initData()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initData()
    }

    private fun initData() {
        paint = Paint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
            color = Color.WHITE
            setLayerType(LAYER_TYPE_HARDWARE, paint)
            setShadowLayer(SIZE_4.toFloat(), 0f, 0f, Color.parseColor("#BDBDBD"))
        }

    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat() - 5f, paint!!)
        super.onDraw(canvas)
    }
}