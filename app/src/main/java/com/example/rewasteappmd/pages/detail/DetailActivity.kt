package com.example.rewasteappmd.pages.detail

import android.os.Bundle
import android.view.LayoutInflater
import com.example.rewasteappmd.databinding.ActivityDetailKerajinanBinding
import com.example.rewasteappmd.pages.BaseActivity

class DetailActivity: BaseActivity<ActivityDetailKerajinanBinding>() {
    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailKerajinanBinding {
        return ActivityDetailKerajinanBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {

    }
}