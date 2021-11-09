package com.example.softlogistica.ui.qr

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.BarCodeFragmentBinding
import com.google.zxing.Result
import org.jetbrains.anko.support.v4.runOnUiThread


class BarCodeFragment : Fragment() {
    private lateinit var codeScanner: CodeScanner
    private val RC_PERMISSION = 10
    private var mCodeScanner: CodeScanner? = null
    private var mPermissionGranted = false

    private lateinit var binding : BarCodeFragmentBinding
    private lateinit var barCodeViewModel : BarCodeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(this.activity).application
        val dataSource =  ApplicationDatabase.getInstance(application).productDatabaseDao
        val barCodeRepository =  BarCodeRepository(ApplicationDatabase.getInstance(application))
        val viewModelFactory = BarCodeViewModelFactory(dataSource, application, barCodeRepository)

        binding =  BarCodeFragmentBinding.inflate(inflater, container, false)
        barCodeViewModel = ViewModelProvider(this, viewModelFactory).get(BarCodeViewModel::class.java)

        observers()

        return binding.root
    }

    private fun observers() {

        barCodeViewModel.products.observe(viewLifecycleOwner, Observer { product ->
            if (product != null){
                product.let { it ->
                    this.findNavController().navigate(
                        BarCodeFragmentDirections.actionNavQrCodeToProductDetailFragment(it))
                    barCodeViewModel.onDetailProductNavigated()
                }
            }else{
                mCodeScanner?.startPreview()
            }
        })

        barCodeViewModel.message.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                it.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                    barCodeViewModel.onMessageShowed()
                }
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCodeScanner = CodeScanner(requireContext(), activity?.findViewById(R.id.scanner_view)!!)
        mCodeScanner?.setDecodeCallback(DecodeCallback { result: Result? ->
            runOnUiThread {
                if(result.toString().isDigitsOnly()){
                    barCodeViewModel.barCodeScannerProduct(result.toString().toLong())
                }else{
                    Toast.makeText(requireContext(), "Codigo QR incorrecto", Toast.LENGTH_LONG).show()
                    mCodeScanner?.startPreview()

                }
            }
        })
        mCodeScanner?.setErrorCallback(ErrorCallback { error: Exception? ->
            runOnUiThread {
                Toast.makeText(requireContext(), "Error al escanear el codigo", Toast.LENGTH_LONG).show()
            }
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false
                requestPermissions(arrayOf(Manifest.permission.CAMERA), RC_PERMISSION)
            } else {
                mPermissionGranted = true
            }
        } else {
            mPermissionGranted = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (mPermissionGranted) {
            mCodeScanner!!.startPreview()
        }
    }

    override fun onPause() {
        mCodeScanner!!.releaseResources()
        super.onPause()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == RC_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = true
                mCodeScanner!!.startPreview()
            } else {
                mPermissionGranted = false
            }
        }
    }

}



