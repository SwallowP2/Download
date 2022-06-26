package com.example.updown

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.updown.databinding.ActivityMainBinding
import com.tvapp.pdfloadandshare.MainActivityViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDownload.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE
            initViewModel()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, object: ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(fileDir = filesDir) as T
            }
        }).get(MainActivityViewModel::class.java)

        viewModel.isFileReadyObserver.observe(this, Observer <Boolean>{
            binding.progressBar.visibility = View.GONE

            if(!it) {
                Toast.makeText(this@MainActivity, "Failed to download PDF", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@MainActivity, "PDF Downloaded successfully", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.downloadPdfFile("")
    }
}