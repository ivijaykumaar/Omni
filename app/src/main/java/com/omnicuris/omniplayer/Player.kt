package com.omnicuris.omniplayer

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.act_player.*

class Player : AppCompatActivity() {

    private var params: ConstraintLayout.LayoutParams? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_player)

        val trackSelectorDef: TrackSelector = DefaultTrackSelector()
        val absPlayerInternal =
            ExoPlayerFactory.newSimpleInstance(applicationContext, trackSelectorDef)
        val userAgent =
            Util.getUserAgent(applicationContext, "omini")
        val sourceFactory =
            DefaultDataSourceFactory(applicationContext, userAgent)
        val uriOfContentUrl: Uri =
            Uri.parse("https://www.rmp-streaming.com/media/bbb-360p.mp4")
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(sourceFactory).createMediaSource(uriOfContentUrl)

        absPlayerInternal.prepare(mediaSource)
        absPlayerInternal.playWhenReady = true
        params = lay_main.layoutParams as ConstraintLayout.LayoutParams
        params!!.height = 0
        params!!.matchConstraintPercentHeight = 0.35f
        lay_main.layoutParams = params
        viewPlayer.player = absPlayerInternal

        img_mode.setOnClickListener {
            val orientation = resources.configuration.orientation
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                img_mode.setImageResource(R.drawable.ic_exit_fullscreen)
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                img_mode.setImageResource(R.drawable.ic_fullscreen)
            }
        }

        val listOfVideos = listOf<String>(
            "Philosopher's Stone",
            "Chamber of Secrets",
            "Prisoner of Azkaban",
            "Goblet of Fire",
            "Order of the Phoenix",
            "Half-Blood Prince",
            "Deathly Hallows – Part 1",
            "Deathly Hallows – Part 2"
        )
        view_product?.apply {
            view_product?.layoutManager =
                LinearLayoutManager(this@Player)
            view_product?.adapter =
                PlaylistAdapter(
                    listOfVideos, this@Player
                )
        }
    }

    override fun onStop() {
        super.onStop()
        viewPlayer.player.stop()
        viewPlayer.player.release()
    }

    override fun onPause() {
        super.onPause()
        viewPlayer.player.release()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            img_mode.setImageResource(R.drawable.ic_exit_fullscreen)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            supportActionBar?.hide()
            params!!.height = ViewGroup.LayoutParams.MATCH_PARENT
            lay_main.layoutParams = params
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            img_mode.setImageResource(R.drawable.ic_fullscreen)
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            supportActionBar?.show()
            params!!.height = 0
            params!!.matchConstraintPercentHeight = 0.35f
            lay_main.layoutParams = params
        }
    }

}
