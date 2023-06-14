package com.example.rewasteappmd.pages.scanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.rewasteappmd.databinding.ActivityScanBinding
import com.example.rewasteappmd.ml.GarbageXception
import com.example.rewasteappmd.model.MachineLearningModel
import com.example.rewasteappmd.pages.BaseActivity
import com.example.rewasteappmd.pages.recommendation.DetailListActivity
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


@AndroidEntryPoint
class ScanActivity : BaseActivity<ActivityScanBinding>() {

    private val viewModel: ScanActivityViewModel by viewModels()

    override fun setupViewBinding(): (LayoutInflater) -> ActivityScanBinding {
        return ActivityScanBinding::inflate
    }

    override fun setupViewInstance(savedInstanceState: Bundle?) {
        setupAppBar()

        viewModel.detectionLabel.postValue(null)

        val onCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                var image: Bitmap = result.data?.extras?.get("data") as Bitmap
                val dimension = min(image.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                binding.preview.setImageBitmap(image)

                image = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, false)
                classifyImage(image)
            }

        }

        openCamera(onCameraResult)

        binding.retake.setOnClickListener {
            viewModel.detectionLabel.postValue(null)
            openCamera(onCameraResult)
        }

        viewModel.detectionLabel.observe(this) { label ->
            Log.d("ScanActivity", "label: $label")
            if (!label.isNullOrEmpty()) {
                Log.d("ScanActivity", "label: $label")
                binding.search.setOnClickListener {
                    val toRecommendation = Intent(this, DetailListActivity::class.java).apply {
                        putExtra(DetailListActivity.EXTRA_LABEL, label)
                    }
                    startActivity(toRecommendation)
                }
            }
        }
    }

    private fun setupAppBar() {
        binding.toolbar.title = "Predicted Result"
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

    private fun openCamera(onCameraResult: ActivityResultLauncher<Intent>) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            onCameraResult.launch(cameraIntent)
        } else {
            // Request camera permission
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }
    }

    private fun classifyImage(image: Bitmap?) {
        val model = GarbageXception.newInstance(this)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        // get 1D array of 224 * 224 pixels in image
        val intValues = IntArray(IMAGE_SIZE * IMAGE_SIZE)
        image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until IMAGE_SIZE) {
            for (j in 0 until IMAGE_SIZE) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        // find the index of the class with the biggest confidence.
        val maxPos = confidences.indices.maxByOrNull { confidences[it] } ?: 0

        val classes = MachineLearningModel.getClasses()

        try {
            val predicted = classes[maxPos].lowercase()
            binding.search.text = predicted
            viewModel.detectionLabel.postValue(predicted)
        } catch (e: ArrayIndexOutOfBoundsException) {
            Log.d("ScanActivity", "arrayIndexOutOfBound: ${e.message}")
            viewModel.detectionLabel.postValue(null)
        }

        model.close()

    }

    companion object {
        const val IMAGE_SIZE = 256
    }

}