package com.example.softlogistica.ui.edit_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.R
import com.example.softlogistica.database.ApplicationDatabase
import com.example.softlogistica.databinding.FragmentFormEditProductBinding

class EditProductFragment : Fragment() {

    companion object {
        fun newInstance() = EditProductFragment()
    }

    private lateinit var viewModel: EditProductViewModel
    private lateinit var binding : FragmentFormEditProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val editProduct = EditProductFragmentArgs.fromBundle(requireArguments()).product

        binding = FragmentFormEditProductBinding.inflate(layoutInflater, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ApplicationDatabase.getInstance(application).productDatabaseDao
        val viewmodelFactory = EditProductViewModelFactory(editProduct, dataSource, application, SavedStateHandle())

        viewModel = ViewModelProvider(this,viewmodelFactory).get(EditProductViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.failUpdateProduct.observe(viewLifecycleOwner, Observer { message ->

            if(message != null){
                Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                viewModel.onFailProductUpdated()
            }
        })

        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.items_menu_text,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerFamilyValueEditProduct.adapter = adapter
            }
        }

        viewModel.onProductUpdated.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }

}