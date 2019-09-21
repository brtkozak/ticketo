package com.google.ticketo.ui.profile.my_profile.intents

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.intent_buy.IntentBuyView
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.intent_sell.IntentSellView
import kotlinx.android.synthetic.main.intents_fragment.*
import kotlinx.android.synthetic.main.intents_fragment.view.*

class IntentsView : Fragment() {

    lateinit var intentAdapter: IntentAdapter

    companion object {
        fun newInstance() = IntentsView()
    }

    private lateinit var viewModel: IntentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intents_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IntentsViewModel::class.java)

        intentAdapter = IntentAdapter(fragmentManager!!)

        addFragments()
        initView()

    }

    private fun addFragments() {
        intentAdapter.addFragment(IntentBuyView(), resources.getString(R.string.you_are_buying))
        intentAdapter.addFragment(IntentSellView(), resources.getString(R.string.you_are_selling))
    }

    private fun initView() {
        intents_view_pager.adapter=intentAdapter
        intents_tablayout.setupWithViewPager(intents_view_pager)
    }

}
