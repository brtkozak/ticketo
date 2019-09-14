package com.google.ticketo.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.google.ticketo.R
import com.google.ticketo.databinding.MyProfileFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.my_profile_fragment.*

class MyProfileView : Fragment() {

    private lateinit var binding: MyProfileFragmentBinding

    companion object {
        fun newInstance() = MyProfileView()
    }

    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.my_profile_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(context!!)
        ).get(MyProfileViewModel::class.java)

        viewModel.user.observe(this, Observer {
            binding.user = it
        })

        viewModel.loading.observe(this, Observer {
            if (!it) {
                my_profile_progress_bar.visibility = View.GONE
                my_profile_container.visibility = View.VISIBLE
            }
        })

    }

}
