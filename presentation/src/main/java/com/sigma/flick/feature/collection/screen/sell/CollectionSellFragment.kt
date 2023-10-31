package com.sigma.flick.feature.collection.screen.sell

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionSellBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.utils.setPopBackStack
import com.sigma.main.model.account.RemitRequestModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionSellFragment :
    BaseFragment<FragmentCollectionSellBinding, CollectionViewModel>(R.layout.fragment_collection_sell) {

    override val viewModel: CollectionViewModel by activityViewModels()
    private val sendViewModel: SendViewModel by activityViewModels()

    private lateinit var codeScanner: CodeScanner
    private lateinit var context: Context

    override fun start() {
        context = requireContext()
        val bottomNavigationBar = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        bottomNavigationBar?.visibility = View.GONE
        binding.toolbar.setPopBackStack()

        permissionCamera()

        viewModel.clickProductPrice.observe(viewLifecycleOwner) { productPrice ->
            binding.tvSellItemPrice.text = productPrice.toString()
        }

        binding.linearReload.setOnClickListener {
            if (::codeScanner.isInitialized) {
                codeScanner.startPreview()
            }
        }
    }

    private fun permissionCamera() {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )
        } else {
            scan()
        }
    }

    private fun scan() {
        val context = requireContext()

        val scannerView = binding.codeScannerView

        codeScanner = CodeScanner(context, scannerView)

        /** setting */
        setCodeScanner()
    }

    private fun setCodeScanner() {
        codeScanner.apply {

            camera = CodeScanner.CAMERA_FRONT
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            /** success */
            decodeCallback = DecodeCallback {
                lifecycleScope.launch {
                    viewModel.decodingJwt(it.text)
                    lifecycleScope.launch {
                        viewModel.paymentState.collect {
                            if (it.isSuccess) {
                                val productPrice = viewModel.clickProductPrice.value!!

                                // todo . 로딩 만들어야 할 듯 너무 느려..

                                viewModel.remittanceAccount.observe(viewLifecycleOwner) { remittanceAccount ->
                                    sendViewModel.remit(
                                        RemitRequestModel(
                                            remittanceAccount,
                                            productPrice,
                                            34 // todo : jb.kjb.kjlb (임시)
                                        )
                                    )
                                }

                                sendViewModel.sendState.collect { sendState ->
                                    if (sendState.isSuccess) {
                                        Toast.makeText(context, "결제되었어요", Toast.LENGTH_SHORT).show()
                                    }
                                    if (sendState.error.isNotEmpty()) {
                                        Toast.makeText(context, sendState.error, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            if (it.error.isNotEmpty()) {
                                Toast.makeText(context, "결제에 실패했어요", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }

            /** failure */
            codeScanner.errorCallback = ErrorCallback {
                Toast.makeText(context, "다시 스캔해주세요!", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "scan: Error ${it.message}")
            }
        }
    }

    //권한요청 처리결과
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    context, "카메라 권한 부여됨",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context, "카메라 권한 거부됨",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
    }

    companion object {
        private const val TAG = "CollectionSellFragment"
    }

}