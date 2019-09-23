package com.google.ticketo.ui.profile.custom_profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.databinding.CustomProfileFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory

class CustomProfileView : Fragment() {

    companion object {
        fun newInstance() = CustomProfileView()
    }

    private lateinit var viewModel: CustomProfileViewModel
    private lateinit var binding: CustomProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_profile_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val userId = arguments?.get("userId") as String
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, CustomProfileFactory(context!!, userId)).get(CustomProfileViewModel::class.java)

        observers()
    }

    fun observers(){
        viewModel.user.observe(this, Observer {
            binding.user=it
        })
    }

}
