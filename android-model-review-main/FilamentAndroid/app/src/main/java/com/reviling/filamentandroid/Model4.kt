package com.reviling.filamentandroid

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Model4 : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView
    private val customViewer: CustomViewer = CustomViewer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request full-screen mode and portrait orientation before setting the content view
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.model_3d_4)

        // Hide the Action Bar
        supportActionBar?.hide()

        // Initialize views
        surfaceView = findViewById(R.id.surface_view)
        val backButton: ImageView = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            finish() // Finish the activity when back button is clicked
        }

        enableImmersiveMode()
        initializeViewer()
    }

    private fun enableImmersiveMode() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun initializeViewer() {
        customViewer.run {
            loadEntity()
            setSurfaceView(surfaceView)
            loadGlb(this@Model4, "sample", "planetary_atom")
            loadIndirectLight(this@Model4, "venetian_crossroads_2k")
        }
    }

    override fun onResume() {
        super.onResume()
        enableImmersiveMode()
        customViewer.onResume()
    }

    override fun onPause() {
        customViewer.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        customViewer.onDestroy()
        super.onDestroy()
    }
}
