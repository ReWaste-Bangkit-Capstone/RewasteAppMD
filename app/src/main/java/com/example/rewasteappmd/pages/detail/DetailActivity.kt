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

class DetailActivity: BaseActivity<ActivityDetailKerajinanBinding>() {

    private val viewModel: DetailActivityViewModel by viewModels()
    private lateinit var handicraftsDetailAdapter: BaseAdapter<ActivityDetailKerajinanBinding, HandicraftDetail>
    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailKerajinanBinding {
        return ActivityDetailKerajinanBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        handicraftsDetailAdapter = BaseAdapter(ActivityDetailKerajinanBinding::inflate) { handicraftDetail, layout ->
            layout.tvDetailKerajinan.text = handicraftDetail.name
            layout.tvDeskripsiKerajinan.text = handicraftDetail.description
            layout.tvLangkahKerajinan.text = handicraftDetail.steps.toString()
            Glide.with(this).load(handicraftDetail.thumbnail).into(layout.imgItemKerajinan)

        }
        viewModel.getHandicraftDetail()
        viewModel.handicraftDetail.observe(this) { handicraftsDetail ->
            if (handicraftsDetail.isNotEmpty()) {
                handicraftsDetailAdapter.setData(handicraftsDetail)
            }
        }
    }
}