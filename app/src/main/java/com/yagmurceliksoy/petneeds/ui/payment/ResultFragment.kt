package com.yagmurceliksoy.petneeds.ui.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yagmurceliksoy.petneeds.R
import com.yagmurceliksoy.petneeds.common.viewBinding
import com.yagmurceliksoy.petneeds.databinding.FragmentResultBinding

class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        val gifImg = pl.droidsonroids.gif.GifDrawable(resources, R.drawable.pet)

        with(binding) {
            ivPet.setImageDrawable(gifImg)

            btnHome.setOnClickListener {
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHomeFragment())
            }
        }
    }
}