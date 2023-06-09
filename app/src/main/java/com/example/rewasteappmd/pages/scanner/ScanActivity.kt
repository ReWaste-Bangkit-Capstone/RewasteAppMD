package com.example.rewasteappmd.pages.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import com.example.rewasteappmd.R

private const val CAMERA_REQUEST_CODE = 101

class ScanActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        //codeScanner()
    }

//    private fun codeScanner() {
//        codeScanner = CodeScanner(this, item_scan)
//        codeScanner.apply {
//            camera = CodeScanner.CAMERA_BACK
//            formats = CodeScanner.ALL_FORMATS
//
//            autoFocusMode = AutoFocusMode.SAFE
//            scanMode = ScanMode.CONTINUOUS
//            isAutoFocusEnabled = true
//            isFlashEnabled = false
//        }

    }
}