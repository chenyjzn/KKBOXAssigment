package com.yuchen.kkbox

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.yuchen.kkbox.databinding.ActivityAuthBinding
import com.yuchen.kkbox.ext.getVmFactory
import com.yuchen.kkbox.network.LoadApiStatus

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    val viewModel: AuthViewModel by viewModels{ getVmFactory() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)
        viewModel.auth.observe(this, Observer {
            it?.let {
                val bundle = Bundle()
                bundle.putParcelable("Auth", it)
                val intent = Intent(baseContext, MainActivity::class.java)
                intent.putExtra("AuthBundle",bundle)
                startActivity(intent)
                this.finish()
            }
        })
        viewModel.loadApiStatus.observe(this, Observer {
            it?.let {
                when(it){
                    is LoadApiStatus.LOADING -> {
                        binding.authProgress.visibility = View.VISIBLE
                        binding.authText.text = resources.getString(R.string.checking_auth)
                    }
                    is LoadApiStatus.DONE -> {
                        binding.authProgress.visibility = View.INVISIBLE
                        binding.authText.text = resources.getString(R.string.auth_success)
                    }
                    is LoadApiStatus.ERROR -> {
                        showErrorMessage(it.message)
                        binding.authText.text = resources.getString(R.string.auth_fail)
                    }
                }
            }
        })
    }

    private fun showErrorMessage(message: String){
        Snackbar
            .make(binding.authConstraint, message, Snackbar.LENGTH_LONG)
            .show()
    }
}