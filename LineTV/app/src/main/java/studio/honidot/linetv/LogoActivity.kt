package studio.honidot.linetv

import android.os.Bundle
import android.os.Handler
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import studio.honidot.linetv.databinding.ActivityLogoBinding

class LogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoBinding
    private val duration = 1000L
    private val await = 2500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_logo)

        Handler().postDelayed({
            startLogoAnimation4Particle()
        }, await)
    }

    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    private fun startLogoAnimation4Particle() {

        val scaleAnimation = ScaleAnimation(
            1f, 0f,
            1f, 0f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = this@LogoActivity.duration
            fillAfter = true
        }

        val rotateAnimation = RotateAnimation(
            0f, 1800f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = this@LogoActivity.duration
            fillAfter = true
        }

        binding.imageLogo.startAnimation(AnimationSet(false).apply {
            addAnimation(rotateAnimation)
            addAnimation(scaleAnimation)
            fillAfter = true
        })

        val alphaAnimation = AlphaAnimation(1f, 0f).apply {
            duration = this@LogoActivity.duration
            fillAfter = true
        }

        binding.backgroundLogo.startAnimation(AnimationSet(false).apply {
            addAnimation(alphaAnimation)
            fillAfter = true
        })
    }

}