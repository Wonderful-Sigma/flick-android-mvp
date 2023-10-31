package com.sigma.flick.feature.collection.screen.sell

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentCollectionSettingSellBinding
import com.sigma.flick.feature.collection.viewmodel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CollectionSettingSellFragment : BaseFragment<FragmentCollectionSettingSellBinding, CollectionViewModel>(R.layout.fragment_collection_setting_sell) {

    override val viewModel: CollectionViewModel by activityViewModels()

    override fun start() {
        val bottomNavigationBar = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        bottomNavigationBar?.visibility = View.GONE

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnOkay.setOnClickListener {
            val etSellItemPrice = binding.etItemPrice.text.toString()

            if (etSellItemPrice.toLong() != 0L && etSellItemPrice.isNotEmpty()) {
                viewModel.setProductPrice(etSellItemPrice.toLong())

                val action = CollectionSettingSellFragmentDirections.toCollectionSellFragment()
                findNavController().navigate(action)
            }
        }
    }

//    private fun String.isNumber(): Boolean {
//        return try {
//            Integer.parseInt(this)
//            true
//        } catch (ex: NumberFormatException) {
//            Toast.makeText(context, "가격을 숫자로 입력해주세요!", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "isNumber: $ex")
//            false
//        }
//    }

    companion object {
        private const val TAG = "CollectionSettingSellFragment"
    }

}