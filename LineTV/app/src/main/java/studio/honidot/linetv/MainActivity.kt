package studio.honidot.linetv

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import studio.honidot.linetv.databinding.ActivityMainBinding
import studio.honidot.linetv.drama.DramaAdapter

class MainActivity : AppCompatActivity() {

//    /**
//     * Lazily initialize our [MainViewModel].
//     */
   // val viewModel by viewModels<MainViewModel> { getVmFactory() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        //binding.viewModel = viewModel
    }
}
