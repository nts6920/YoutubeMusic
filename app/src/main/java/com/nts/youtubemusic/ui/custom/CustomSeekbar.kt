package com.nts.youtubemusic.ui.custom

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.pierfrancescosoffritti.androidyoutubeplayer.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.utils.TimeUtilities

class CustomSeekbar(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs),
    SeekBar.OnSeekBarChangeListener,
    YouTubePlayerListener {
    private var seekBarTouchStarted = false

    // I need this variable because onCurrentSecond gets called every 100 mils, so without the proper checks on this variable in onCurrentSeconds the seek bar glitches when touched.
    private var newSeekBarProgress = -1

    private var isPlaying = false

    var showBufferingProgress = true
    var youtubePlayerSeekBarListener: YouTubePlayerSeekBarListener? = null


    val seekBar = SeekBar(context)

    init {
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.YouTubePlayerSeekBar, 0, 0)

        val color = typedArray.getColor(
            R.styleable.YouTubePlayerSeekBar_color,
            ContextCompat.getColor(context, R.color.ayp_red)
        )
//        typedArray.recycle()
        setColor(color)
        addView(seekBar, LayoutParams(0, 10, 1f))
        gravity = Gravity.CENTER_VERTICAL
        seekBar.setPadding(0, 0, 0, 0);
        seekBar.setBackgroundColor(Color.TRANSPARENT)
        seekBar.setOnSeekBarChangeListener(this)


    }


    fun setColor(@ColorInt color: Int) {
        DrawableCompat.setTint(seekBar.thumb, color)
        DrawableCompat.setTint(seekBar.progressDrawable, color)
    }

    fun setThumb(thumb: Drawable) {
        seekBar.setThumb(thumb)
    }

    private fun updateState(state: PlayerConstants.PlayerState) {
        when (state) {
            PlayerConstants.PlayerState.ENDED -> isPlaying = false
            PlayerConstants.PlayerState.PAUSED -> isPlaying = false
            PlayerConstants.PlayerState.PLAYING -> isPlaying = true
            PlayerConstants.PlayerState.UNSTARTED -> resetUi()
            else -> {
            }
        }
    }

    private fun resetUi() {
        seekBar.progress = 0
        seekBar.max = 0
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        seekBarTouchStarted = true
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
        if (isPlaying)
            newSeekBarProgress = seekBar.progress

        youtubePlayerSeekBarListener?.seekTo(seekBar.progress.toFloat())
        seekBarTouchStarted = false
    }

    // YouTubePlayerListener

    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
        newSeekBarProgress = -1
        updateState(state)
    }

    override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
        // ignore if the user is currently moving the SeekBar
        if (seekBarTouchStarted)
            return
        // ignore if the current time is older than what the user selected with the SeekBar
        if (newSeekBarProgress > 0 && TimeUtilities.formatTime(second) != TimeUtilities.formatTime(
                newSeekBarProgress.toFloat()
            )
        )
            return

        newSeekBarProgress = -1

        seekBar.progress = second.toInt()
    }

    override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
        seekBar.max = duration.toInt()
    }

    override fun onVideoLoadedFraction(youTubePlayer: YouTubePlayer, loadedFraction: Float) {
        if (showBufferingProgress)
            seekBar.secondaryProgress = (loadedFraction * seekBar.max).toInt()
        else
            seekBar.secondaryProgress = 0
    }

    override fun onReady(youTubePlayer: YouTubePlayer) {}
    override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}
    override fun onApiChange(youTubePlayer: YouTubePlayer) {}
    override fun onPlaybackQualityChange(
        youTubePlayer: YouTubePlayer,
        playbackQuality: PlayerConstants.PlaybackQuality
    ) {
    }

    override fun onPlaybackRateChange(
        youTubePlayer: YouTubePlayer,
        playbackRate: PlayerConstants.PlaybackRate
    ) {
    }

    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {}
}

interface YouTubePlayerSeekBarListener {
    fun seekTo(time: Float)
}