package com.nts.youtubemusic.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.nts.youtubemusic.R
import com.nts.youtubemusic.common.Constant
import com.nts.youtubemusic.common.MessageEvent
import com.nts.youtubemusic.ui.main.MainActivity
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.inflate
import com.tuanhav95.drag.utils.reWidth
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_play_bottom.view.*
import kotlinx.android.synthetic.main.layout_top.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DragSourceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : DragView(context, attrs, defStyleAttr) {
    var mWidthWhenMax = 0
    var mWidthWhenMiddle = 0
    var mWidthWhenMin = 0

    init {
        getFrameFirst().addView(inflate(R.layout.layout_top))
        getFrameSecond().addView(inflate(R.layout.layout_bottom))
    }

    override fun initFrame() {

        mWidthWhenMax = width
        mWidthWhenMiddle = (width - mPercentWhenMiddle * mMarginEdgeWhenMin).toInt()
        mWidthWhenMin = mHeightWhenMinDefault * 17 / 9
        super.initFrame()
    }

    override fun refreshFrameFirst() {
        super.refreshFrameFirst()
        val width = if (mCurrentPercent < mPercentWhenMiddle) {
            (context as MainActivity).hideNavigation()
            (context as MainActivity).isCheckMaxScreen = true
            (mWidthWhenMax - (mWidthWhenMax - mWidthWhenMiddle) * mCurrentPercent)

        } else {
            (context as MainActivity).showNavigation()
            (context as MainActivity).isCheckMaxScreen = false
            (mWidthWhenMiddle - (mWidthWhenMiddle - mWidthWhenMin) * (mCurrentPercent - mPercentWhenMiddle) / (1 - mPercentWhenMiddle))
        }


        frameTop.reWidth(width.toInt())
        ivClose.setOnClickListener(OnClickListener {
            dragView.close()
            (context as MainActivity).exitVideo()
        })


        ivPause.setOnClickListener(OnClickListener {
            (context as MainActivity).pauseVideo()
            ivPause.visibility = View.GONE
            ivPlay.visibility = View.VISIBLE
        })
        ivPlay.setOnClickListener(OnClickListener {
            (context as MainActivity).playVideo()
            ivPause.visibility = View.VISIBLE
            ivPlay.visibility = View.GONE
        })
        tvTitle.text = (context as MainActivity).titleVideo
        tvChannel.text = (context as MainActivity).channelVideo

    }


}