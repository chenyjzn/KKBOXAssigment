package com.yuchen.kkbox

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.yuchen.kkbox.databinding.ActivityMainBinding
import com.yuchen.kkbox.factory.ViewModelFactory

const val LIMIT = 10
const val LIMIT_500 = 500
const val OFFSET_0 = 0
const val TERRITORY = "TW"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels{ ViewModelFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val authBundle = intent.getBundleExtra("AuthBundle")
        viewModel
        this.findNavController(R.id.nav_host_fragment).setGraph(R.navigation.kkbox_navigation, authBundle)
    }
    fun showErrorMessage(message: String){
        Snackbar
            .make(binding.mainConstraint, message, Snackbar.LENGTH_LONG)
            .show()
    }
}
