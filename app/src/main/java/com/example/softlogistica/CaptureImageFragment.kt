package com.example.softlogistica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.CaptureImageFragmentBinding
import com.example.softlogistica.ui.new_product.NewProductViewModel
import com.example.softlogistica.ui.new_product.NewProductViewModelFactory
import com.example.softlogistica.utils.GetConstant

class CaptureImageFragment : Fragment() {

    companion object {
        fun newInstance() = CaptureImageFragment()
    }

    private lateinit var viewModel: NewProductViewModel
    private lateinit var binding: CaptureImageFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val nameImage =   arguments?.getString(GetConstant.NAME_IMAGE)
        val savedUri = arguments?.getString(GetConstant.URI)?.toUri()

        binding = CaptureImageFragmentBinding.inflate(inflater, container, false)
        binding.imageView.setImageURI(savedUri)

        binding.repeat.setOnClickListener {
            findNavController().navigate(R.id.action_captureImageFragment_to_cameraXFragment)
        }

        binding.save.setOnClickListener {
            val bundle = bundleOf(Pair(GetConstant.NAME_IMAGE,nameImage), Pair(GetConstant.URI, savedUri.toString()))
            saveImage(bundle)
            findNavController().navigate(R.id.action_captureImageFragment_to_fragmentNewProduct)
        }
        return binding.root
    }

    private fun saveImage(bundle: Bundle) {
        val nameIamge = bundle.getString(GetConstant.NAME_IMAGE)
//        if(nameIamge.equals(GetConstant.DRIVING)){
//            viewModel.setImageDrivingLicence(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }else if(nameIamge.equals(GetConstant.TECHNICAL)){
//            viewModel.setImageTechnicalSheet(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }else if(nameIamge.equals(GetConstant.TRANSPORTATION)){
//            viewModel.setImageTransportationCard(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }else if(nameIamge.equals(GetConstant.PRODUCT)){
//            viewModel.setImageProduct(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }else if(nameIamge.equals(GetConstant.iNVOICE)){
//            viewModel.setImageInvoiceBuy(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }else if(nameIamge.equals(GetConstant.HOMOLOGATION)){
//            viewModel.setImageHomologation(MutableLiveData(bundle.getString(GetConstant.URI)))
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val application = requireNotNull(this.activity).application
        val dataSource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = NewProductViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(NewProductViewModel::class.java)


    }

}