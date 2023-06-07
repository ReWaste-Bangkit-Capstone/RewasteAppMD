package com.example.rewasteappmd.pages

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun setupViewBinding(): (LayoutInflater) -> VB
    abstract fun setupViewInstance(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setupViewBinding().invoke(layoutInflater)
        setContentView(binding.root)
        setupViewInstance(savedInstanceState)

    }

    companion object {
        var TAG = this::class.java.simpleName
    }

}