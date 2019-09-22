package com.google.ticketo.ui.profile.my_profile.intents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentBuyView
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentSellView
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.intents_fragment.*

class IntentsView : Fragment() {

    lateinit var eventAdapter: FragmentAdapter

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

        initView()
        addFragments()
        onClicks()
    }

    private fun initView() {
        eventAdapter = FragmentAdapter(childFragmentManager)
        intents_view_pager.adapter = eventAdapter
        intents_tablayout.setupWithViewPager(intents_view_pager)
    }

    private fun addFragments() {
        eventAdapter.addFragment(IntentBuyView(), resources.getString(R.string.you_are_buying))
        eventAdapter.addFragment(IntentSellView(), resources.getString(R.string.you_are_selling))
        eventAdapter.notifyDataSetChanged()
    }

    private fun onClicks(){
        intents_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

}
