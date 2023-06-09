package com.example.rewasteappmd.pages.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.rewasteappmd.adapter.BaseAdapter
import com.example.rewasteappmd.databinding.ActivityDetailListBinding
import com.example.rewasteappmd.databinding.ItemsRowListBinding
import com.example.rewasteappmd.model.Handicraft
import com.example.rewasteappmd.pages.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailListActivity: BaseActivity<ActivityDetailListBinding>() {

    private val viewModel: DetailListActivityViewModel by viewModels()
    private lateinit var handicraftsAdapter: BaseAdapter<ItemsRowListBinding, Handicraft>
    override fun setupViewBinding(): (LayoutInflater) -> ActivityDetailListBinding {
        return ActivityDetailListBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        setupAppBar()

        handicraftsAdapter = BaseAdapter(ItemsRowListBinding::inflate) { handicraft, layout ->
            layout.tvAlbumName.text = handicraft.name
            layout.tvAlbumRelease.text = handicraft.description

            Glide.with(this).load(handicraft.thumbnail).into(layout.imgItemPhoto)
        }
        binding.rvDetailKerajinan.layoutManager = LinearLayoutManager(this)
        binding.rvDetailKerajinan.adapter = handicraftsAdapter

        viewModel.getHandicrafts()
        viewModel.handicrafts.observe(this) { handicrafts ->
            if (handicrafts.isNotEmpty()) {
                handicraftsAdapter.setData(handicrafts)
            }
        }
    }

    private fun setupAppBar() {
        binding.toolbar.title = "Recycle Recommendation"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> false
        }
    }
}