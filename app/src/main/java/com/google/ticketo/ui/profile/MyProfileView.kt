package com.google.ticketo.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.google.ticketo.R
import kotlinx.android.synthetic.main.my_profile_fragment.*

class MyProfileView : Fragment() {

    companion object {
        fun newInstance() = MyProfileView()
    }

    private lateinit var viewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        // TODO: Use the ViewModel


        Glide.with(this)
            .load("https://graph.facebook.com/2864734686933587/picture?height=300&width=300")
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_launcher_background)
            .override(350, 350)
            .into(my_profile_profile_pic)

    }

}
