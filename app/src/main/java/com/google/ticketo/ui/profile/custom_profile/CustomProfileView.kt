package com.google.ticketo.ui.profile.custom_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.databinding.CustomProfileFragmentBinding
import com.google.ticketo.model.Review
import com.google.ticketo.utils.Const
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.custom_profile_fragment.*
import kotlinx.android.synthetic.main.event_details_fragment.*

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
        onClicks()
    }

    private fun observers(){
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.user=it
        })

        viewModel.recommendations.observe(viewLifecycleOwner, Observer {
            custom_profile_recommend_counter.text = it.size.toString()
            setButtonState(it, custom_profile_recommend_button)
        })

        viewModel.warns.observe(viewLifecycleOwner, Observer {
            custom_profile_warn_counter.text = it.size.toString()
            setButtonState(it, custom_profile_warn_button)
        })
    }

    private fun onClicks(){
        custom_profile_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }

        custom_profile_recommend_button.setOnClickListener {
            onRecommendationClick()
        }

        custom_profile_warn_button.setOnClickListener {
            onWarnClick()
        }
    }

    private fun setButtonState(reviews : List<Review>, button : ImageButton) {
        button.isSelected=reviews.any { review -> review.userId == viewModel.currentUser  }
    }

    private fun onRecommendationClick(){
        if(!custom_profile_recommend_button.isSelected && !custom_profile_warn_button.isSelected){
            viewModel._recommendationLock.value=1
            viewModel.addReview(Const.RECOMMEND)
        }
        else if(!custom_profile_recommend_button.isSelected && custom_profile_warn_button.isSelected){
            viewModel._recommendationLock.value=2
            viewModel.addReview(Const.RECOMMEND)
            viewModel.removeReview(Const.WARN, Const.RECOMMEND)
        }
        else{
            viewModel._recommendationLock.value=1
            viewModel.removeReview(Const.RECOMMEND, Const.RECOMMEND)
        }
    }
    private fun onWarnClick(){
        if (!custom_profile_warn_button.isSelected && !custom_profile_recommend_button.isSelected) {
            viewModel._warnLock.value = 1
            viewModel.addReview(Const.WARN)
        } else if (!custom_profile_warn_button.isSelected && custom_profile_recommend_button.isSelected) {
            viewModel._warnLock.value = 2
            viewModel.addReview(Const.WARN)
            viewModel.removeReview(Const.RECOMMEND, Const.SELLERS)
        } else {
            viewModel._warnLock.value = 1
            viewModel.removeReview(Const.SELLERS, Const.SELLERS)
        }
    }

}
