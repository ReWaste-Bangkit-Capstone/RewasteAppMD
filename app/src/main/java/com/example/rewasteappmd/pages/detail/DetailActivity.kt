package com.example.rewasteappmd.pages.detail

import android.content.AbstractThreadedSyncAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.rewasteappmd.adapter.BaseAdapter
import com.example.rewasteappmd.databinding.ActivityDetailKerajinanBinding
import com.example.rewasteappmd.model.HandicraftDetail
import com.example.rewasteappmd.pages.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: BaseActivity<ActivityDetailKerajinanBinding>() {

    private val viewModel: DetailActivityViewModel by viewModels()

    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailKerajinanBinding {
        return ActivityDetailKerajinanBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        setupAppBar()

        val handicraftId = intent.getStringExtra(EXTRA_ID) ?: ""

        viewModel.getHandicraftDetail(handicraftId)
        viewModel.handicraftDetail.observe(this) { handicraftsDetail ->
            if (handicraftsDetail.id.isNotEmpty()) {
                binding.tvDetailKerajinan.text = handicraftsDetail.name
                Glide.with(this).load(handicraftsDetail.thumbnail).into(binding.imgItemKerajinan)
                binding.tvLangkahKerajinan.text = handicraftsDetail.steps.toString()
                binding.tvDeskripsiKerajinan.text = handicraftsDetail.description
            }
        }
    }


    private fun setupAppBar() {
        binding.toolbar.title = "Item Detail"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}