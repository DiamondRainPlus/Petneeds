package com.yagmurceliksoy.petneeds.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.yagmurceliksoy.petneeds.R
import com.yagmurceliksoy.petneeds.common.viewBinding
import com.yagmurceliksoy.petneeds.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            btnPay.setOnClickListener {
                val address = etAddress.text.toString()
                val name = etNameSurname.text.toString()
                val cardNumber = etCardNumber.text.toString()
                val cardDate = etCardDate.text.toString()
                val cvc = etCvc.text.toString()

                if (cardNumber.length == 16 && cardDate.isNotEmpty() && cvc.isNotEmpty() && address.isNotEmpty() && name.isNotEmpty()) {
                    findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToResultFragment())
                } else {
                    Snackbar.make(requireView(), "Fill all blanks and ensure card number is 16 characters long", Snackbar.LENGTH_SHORT).show()
                }
            }

        }
    }

}