package com.example.softlogistica.ui.new_product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentFormNewProductBinding


class FragmentNewProduct : Fragment() {

    companion object {
        fun newInstance() = FragmentNewProduct()
    }
    private lateinit var viewModel: NewProductViewModel
    private lateinit var binding: FragmentFormNewProductBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


                binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form_new_product, container, false)
                val application = requireNotNull(this.activity).application
                val dataSource = ApplicationDatabase.getInstance(application).productDatabaseDao
                val viewModelFactory = NewProductViewModelFactory(dataSource, application)
                viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(NewProductViewModel::class.java)
                binding.viewmodelNewProduct = viewModel
                binding.lifecycleOwner = this


                setButtonListeners()
                databindingViewModelImage()
                familyFormControl()


        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.items_menu_text,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                binding.spinnerNewProductFamily.adapter = adapter
//                binding.spinnerNewProductFamily.setSelection(binding?.viewmodelNewProduct?.family?.value?.toInt() ?: 1)
            }
        }
        return binding.root
    }


    private fun databindingViewModelImage() {

        //Spinner

//        if(binding.viewmodelNewProduct?.imageDrivingLicence?.value != null) {
//            binding.imageViewNewProductDrivingLicencePicture.setImageURI(binding.viewmodelNewProduct?.imageDrivingLicence?.value.toString().toUri())
//        }
//        if(binding.viewmodelNewProduct?.imageHomologation?.value != null){
//            binding.imageViewNewProductHomologationPicture.setImageURI(binding.viewmodelNewProduct?.imageHomologation?.value.toString().toUri())
//        }
//        if(binding.viewmodelNewProduct?.imageHomologation?.value != null) {
//            binding.imageViewNewProductTransportationCardPicture.setImageURI(binding.viewmodelNewProduct?.imageTransportationCard?.value?.toUri())
//        }
//        if(binding.viewmodelNewProduct?.imageTechnicalSheet?.value != null){
//            binding.imageViewNewProductTechnicalSheetPicture.setImageURI(binding.viewmodelNewProduct?.imageTechnicalSheet?.value.toString().toUri())
//        }
//        if(binding.viewmodelNewProduct?.imageProduct?.value != null){
//            binding.imageViewNewProductPictureProduct.setImageURI(binding.viewmodelNewProduct?.imageProduct?.value.toString().toUri())
//        }
//        if (binding.viewmodelNewProduct?.imageInvoiceBuy?.value != null){
//            binding.imageViewNewProductInvoiceBuyPicture.setImageURI(binding.viewmodelNewProduct?.imageInvoiceBuy?.value.toString().toUri())
//        }

    }






    private fun familyFormControl() {
//        binding.spinnerNewProductFamily.onItemSelectedListener {
//            this.onItemSelected { adapterView, view, i, l ->
//                binding.viewmodelNewProduct?.setSpinnerPosition(MutableLiveData(i))
//               when(i){
//                   0->{
//                       binding.viewmodelNewProduct?.family?.value = 0
//                       machineryEllementsForm()
//                   }
//                   1->{
//                       binding.viewmodelNewProduct?.family?.value = 1
//                       auxiliaryMeansElementsForm()
//                   }
//                   2->{
//                       binding.viewmodelNewProduct?.family?.value = 2
//                        transportElementsForm()
//                   }
//               }
//            }
        }
    }


    private fun auxiliaryMeansElementsForm() {
        showCommonElementsForm()
    }

    private fun transportElementsForm() {
        showCommonElementsForm()
    }

    private fun machineryEllementsForm() {
        //Driving Licence
//        binding.imageViewNewProductDrivingLicencePicture.visibility = View.GONE
//        binding.textViewNewProductDrivingLicence.visibility = View.GONE
//
//        //Itv
//        binding.editTextTextNewProductItv.visibility = View.GONE
//        binding.textViewNewProductItv.visibility = View.GONE
//
//        //Transport Card
//        binding.imageViewNewProductTransportationCardPicture.visibility = View.GONE
//        binding.textViewNewProductTransportationCard.visibility = View.GONE
//
//        //Homlogation
//        binding.imageViewNewProductHomologationPicture.visibility = View.GONE
//        binding.textViewNewProductHomologation.visibility = View.GONE
//
//        //Tare
//        binding.editTextNewProductTare.visibility = View.GONE
//        binding.textViewNewProductTare.visibility = View.GONE
//
//        //Maximum Authorized
//        binding.editTextNewProductMaximumAuthorizedMass.visibility = View.GONE
//        binding.textViewNewProductMaximumAuthorizedMass.visibility = View.GONE
//
//        //Insurance
//        binding.editTextNewProductInsurance.visibility = View.GONE
//        binding.textViewNewProductInsurance.visibility = View.GONE
    }



    private fun showCommonElementsForm(){
//        //Driving Licence
//        binding.imageViewNewProductDrivingLicencePicture.visibility = View.VISIBLE
//        binding.textViewNewProductDrivingLicence.visibility = View.VISIBLE
//
//        //Itv
//        binding.editTextTextNewProductItv.visibility = View.GONE
//        binding.textViewNewProductItv.visibility = View.GONE
//
//        //Transport Card
//        binding.imageViewNewProductTransportationCardPicture.visibility = View.VISIBLE
//        binding.textViewNewProductTransportationCard.visibility = View.VISIBLE
//
//        //Homlogation
//        binding.imageViewNewProductHomologationPicture.visibility = View.VISIBLE
//        binding.textViewNewProductHomologation.visibility = View.VISIBLE
//
//        //Tare
//        binding.editTextNewProductTare.visibility = View.VISIBLE
//        binding.textViewNewProductTare.visibility = View.VISIBLE
//
//        //Maximum Authorized
//        binding.editTextNewProductMaximumAuthorizedMass.visibility = View.VISIBLE
//        binding.textViewNewProductMaximumAuthorizedMass.visibility = View.VISIBLE
//
//        //Insurance
//        binding.editTextNewProductInsurance.visibility = View.VISIBLE
//        binding.textViewNewProductInsurance.visibility = View.VISIBLE
    }



    @SuppressLint("ServiceCast")
    fun hideKeyboard(view: View) {
//        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun setButtonListeners() {

//        binding.imageViewNewProductDrivingLicencePicture.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.DRIVING)))
//        }
//        binding.imageViewNewProductHomologationPicture.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.HOMOLOGATION)))
//        }
//        binding.imageViewNewProductInvoiceBuyPicture.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.iNVOICE)))
//        }
//        binding.imageViewNewProductPictureProduct.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.PRODUCT)))
//        }
//        binding.imageViewNewProductTransportationCardPicture.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.TRANSPORTATION)))
//        }
//        binding.imageViewNewProductTechnicalSheetPicture.setOnClickListener {
//            hideKeyboard(binding.root)
//            findNavController().navigate(R.id.action_fragmentNewProduct_to_cameraXFragment, bundleOf(Pair(GetConstant.NAME_IMAGE, GetConstant.TECHNICAL)))
//        }
    }



