package com.sigma.flick.feature.send.screen

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sigma.flick.R
import com.sigma.flick.base.BaseFragment
import com.sigma.flick.databinding.FragmentSendPointBinding
import com.sigma.flick.feature.send.viewmodel.SendViewModel
import com.sigma.flick.feature.user.viewmodel.UserViewModel
import com.sigma.flick.utils.setDeleteBottomNav
import com.sigma.flick.utils.setPopBackStack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@AndroidEntryPoint
class SendPointFragment : BaseFragment<FragmentSendPointBinding, SendViewModel>(R.layout.fragment_send_point) {

    override val viewModel: SendViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var context: Context

    private var myLeftCoin by Delegates.notNull<Long>()

    override fun start() {
        setDeleteBottomNav(activity)
        binding.toolbar.setPopBackStack()

        viewModel.getAccount(viewModel.depositAccountNumber.value.toString())
        viewModel.setCoin("")

        myLeftCoin = userViewModel.myInfo.value!!.account[0].money
        binding.tvMyLeftPoint.text = "잔액 " + myLeftCoin.toString() + "코인"

        context = requireContext()
        viewModel.setSendCoinIsFilledToFalse(false)

        lifecycleScope.launch {
            with(viewModel) {
                accountNumberState.collect {
                    if (it.isSuccess) {
                        depositAccountName.observe(this@SendPointFragment) { depositAccountName ->
                            binding.tvToSendAccountName.text = depositAccountName
                            binding.tvToSendAccountNumber.text = depositAccountNumber.value.toString()
                        }
                    }
//                    if (it.error.isNotEmpty()) {
//                        Toast.makeText(context, "계좌번호를 찾지 못했어요", Toast.LENGTH_SHORT).show()
//                        findNavController().popBackStack()
//                    }
                }
            }
        }

        setOnClickButtons()
        observingSendCoin()

        binding.btnDecide.setOnClickListener {
            val action = SendPointFragmentDirections.actionSendPointFragmentToSendCheckFragment()
            findNavController().navigate(action)
        }
    }

    private fun setOnClickButtons() {
        binding.apply {
            viewModel.apply {
                btnOne.setOnClickListener { plusCoin("1") }
                btnTwo.setOnClickListener { plusCoin("2") }
                btnThree.setOnClickListener { plusCoin("3") }
                btnFour.setOnClickListener { plusCoin("4") }
                btnFive.setOnClickListener { plusCoin("5") }
                btnSix.setOnClickListener { plusCoin("6") }
                btnSeven.setOnClickListener { plusCoin("7") }
                btnEight.setOnClickListener { plusCoin("8") }
                btnNine.setOnClickListener { plusCoin("9") }
                btnDoubleZero.setOnClickListener {
                    if ((sendCoin.value!!.isNotEmpty()) && (sendCoin.value.toString()[0] != '0'))     {
                        plusCoin("00")
                    }
                }
                btnZero.setOnClickListener {
                    if ((sendCoin.value!!.isNotEmpty()) && (sendCoin.value.toString()[0] != '0'))     {
                        plusCoin("0")
                    }
                }
                btnBackSpace.setOnClickListener { backSpaceCoin() }
            }
        }
    }
    private fun observingSendCoin() {
        viewModel.apply {
            binding.apply {
                sendCoin.observe(viewLifecycleOwner) { sendCoin ->
                    tvSendCoin.text = sendCoin
                    val sendCoinToInt: Int

                    if (sendCoin.isNotEmpty()) {
                        sendCoinToInt = Integer.parseInt(sendCoin)
                        ifIsNotFilledSendCoin(context, tvCoin, tvHowMuchSend, btnDecide)
                    } else {
                        sendCoinToInt = 0
                        ifIsFilledSendCoin(context, tvCoin, tvHowMuchSend, btnDecide)
                    }

                    controlSendCoin(sendCoinToInt)
                }
            }
        }
    }

    private fun controlSendCoin(sendCoinToInt: Int) {
        if (sendCoinToInt > myLeftCoin) {
            binding.btnDecide.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_100))
            binding.tvWarning.text = "잔액이 " + myLeftCoin + "코인이에요."
            binding.tvWarning.visibility = View.VISIBLE
            setEnabledAllButtons(false)
        }
        else {
            binding.btnDecide.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.blue_400))
            binding.tvWarning.visibility = View.INVISIBLE
            setEnabledAllButtons(true)
        }
    }

    private fun setEnabledAllButtons(boolean: Boolean) {
        binding.btnDecide.isEnabled = boolean
        binding.btnOne.isEnabled = boolean
        binding.btnTwo.isEnabled = boolean
        binding.btnThree.isEnabled = boolean
        binding.btnFour.isEnabled = boolean
        binding.btnFive.isEnabled = boolean
        binding.btnSix.isEnabled = boolean
        binding.btnSeven.isEnabled = boolean
        binding.btnEight.isEnabled = boolean
        binding.btnNine.isEnabled = boolean
        binding.btnDoubleZero.isEnabled = boolean
        binding.btnZero.isEnabled = boolean
    }

    companion object {
        const val TAG = "SendPointFragment"
    }
}