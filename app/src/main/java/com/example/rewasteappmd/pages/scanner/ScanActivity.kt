package com.example.rewasteappmd.pages.scanner

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.rewasteappmd.R
import com.example.rewasteappmd.databinding.ActivityDetailKerajinanBinding
import com.example.rewasteappmd.databinding.ActivityScanBinding
import com.example.rewasteappmd.ml.Model
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.math.min


class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private lateinit var takeItemPhoto: FloatingActionButton
    private lateinit var imgItemPhoto: ImageView
    private lateinit var label: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        label = findViewById(R.id.tv_jenis_sampah)
        takeItemPhoto = findViewById(R.id.btn_photo)
        imgItemPhoto = findViewById(R.id.img_item_scan)

        val onCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                var image: Bitmap = result.data?.extras?.get("data") as Bitmap
                val dimension = min(image.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imgItemPhoto.setImageBitmap(image)

                image = Bitmap.createScaledBitmap(image, IMAGE_SIZE, IMAGE_SIZE, false)
                //classifyImage(image)
            }

        }

    }

    private fun classifyImage(image: Bitmap?) {
        val model = Model.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * IMAGE_SIZE * IMAGE_SIZE * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(IMAGE_SIZE * IMAGE_SIZE)
        image!!.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

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

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classes = arrayOf("Banana", "Orange", "Pen", "Sticky Notes")
        label.text = classes[maxPos]


    model.close()

    }

    companion object {
        const val IMAGE_SIZE = 224;
    }

}