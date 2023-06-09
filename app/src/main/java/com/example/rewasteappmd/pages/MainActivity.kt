package com.example.rewasteappmd.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.rewasteappmd.R
import com.example.rewasteappmd.databinding.ActivityMainBinding
import com.example.rewasteappmd.pages.recommendation.DetailListActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setupViewBinding(): (LayoutInflater) -> ActivityMainBinding {
        return ActivityMainBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        binding.camera.setOnClickListener {
            startActivity(Intent(this@MainActivity, DetailListActivity::class.java))
        }
    }

}