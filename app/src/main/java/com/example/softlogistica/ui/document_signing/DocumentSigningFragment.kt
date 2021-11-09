package com.example.softlogistica.ui.document_signing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.softlogistica.R
import com.example.softlogistica.databinding.DocumentSigningFragmentBinding
import com.github.gcacace.signaturepad.views.SignaturePad

class DocumentSigningFragment : Fragment() {

    private lateinit var binding : DocumentSigningFragmentBinding
    private lateinit var viewModel: DocumentSigningViewModel
    private lateinit var canvasView: CanvasView
    private lateinit var mSignaturePad : SignaturePad


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DocumentSigningFragmentBinding.inflate(inflater,container,false)

        binding.signatureButtonCorrection.setOnClickListener {
            mSignaturePad.clear()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        mSignaturePad = activity?.findViewById(R.id.signature_pad)!!
        mSignaturePad.setOnClickListener {

        }
    }


}