package com.example.softlogistica

import android.Manifest
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.CameraXFragmentBinding
import com.example.softlogistica.ui.new_product.NewProductViewModel
import com.example.softlogistica.ui.new_product.NewProductViewModelFactory
import com.example.softlogistica.utils.GetConstant
import kotlinx.android.synthetic.main.camera_x_fragment.*
import java.io.File
import java.nio.ByteBuffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


typealias LumaListener = (luma: Double) -> Unit



class CameraXFragment : Fragment() {

    companion object {
        fun newInstance() = CameraXFragment()

        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private lateinit var binding: CameraXFragmentBinding
    private lateinit var viewModel: NewProductViewModel
    private var imageCapture: ImageCapture? = null
    private var savedUri: Uri? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    val displayMetrics = DisplayMetrics()

    var width = displayMetrics.widthPixels
    var height = displayMetrics.heightPixels


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = CameraXFragmentBinding.inflate(inflater, container, false)

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        setListener()
        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                (activity as MenuActivity), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setListener() {
        binding.cameraCaptureButton.setOnClickListener { takePhoto() }
    }


    @RequiresApi(Build.VERSION_CODES.N)
     fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: ImageCapture.Builder().build()

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    savedUri = Uri.fromFile(photoFile)
                    val nameImage = arguments?.getString(GetConstant.NAME_IMAGE)
                    val bundle = bundleOf(Pair(GetConstant.NAME_IMAGE, nameImage), Pair(GetConstant.URI, savedUri.toString()))
                    findNavController().navigate( R.id.action_cameraXFragment_to_captureImageFragment, bundle)
                }
            })

        val imageAnalyzer = ImageAnalysis.Builder()
            .setTargetResolution(Size(width.toInt(), height.toInt()))
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                    Log.d(TAG, "Average luminosity: $luma")
                })
            }
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalyzer)


                val factory = SurfaceOrientedMeteringPointFactory( 1920F, 1080F)
                val point = factory.createPoint(0F, 0F)
                val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
                    .addPoint(point, FocusMeteringAction.FLAG_AE) // could have many
                    // auto calling cancelFocusAndMetering in 5 seconds
                    .setAutoCancelDuration(5, TimeUnit.SECONDS)
                    .build()

                val future = camera.cameraControl.startFocusAndMetering(action)
                /*future.addListener( Runnable {
                    val result = future.get()
                    Log.d(TAG,"$result")
                } , cameraExecutor)*/


            } catch(exc: Exception) {
                Log.d(TAG, "Use case binding failed", exc)
            }


        }, ContextCompat.getMainExecutor(this.requireContext()))
    }




    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        (activity as MenuActivity).baseContext.checkSelfPermission(
            it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = (activity as MenuActivity).baseContext.externalMediaDirs.firstOrNull()?.let { it ->
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else (activity as MenuActivity).baseContext.filesDir
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = NewProductViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(NewProductViewModel::class.java)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                onDestroy()
            }
        }
    }


    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)
            image.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}


