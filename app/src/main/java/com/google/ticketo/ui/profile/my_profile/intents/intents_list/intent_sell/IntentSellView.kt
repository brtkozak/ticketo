package com.google.ticketo.ui.profile.my_profile.intents.intents_list.intent_sell

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.ticketo.R

class IntentSellView : Fragment() {

    companion object {
        fun newInstance() = IntentSellView()
    }

    private lateinit var viewModel: IntentSellViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intent_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IntentSellViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
