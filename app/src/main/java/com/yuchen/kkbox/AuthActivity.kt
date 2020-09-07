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
import com.yuchen.kkbox.factory.ViewModelFactory
import com.yuchen.kkbox.network.LoadApiStatus


class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    val viewModel: AuthViewModel by viewModels{ ViewModelFactory() }
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
                    }
                    is LoadApiStatus.DONE -> {
                        binding.authProgress.visibility = View.INVISIBLE
                    }
                    is LoadApiStatus.ERROR -> {
                        showErrorMessage(it.message)
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