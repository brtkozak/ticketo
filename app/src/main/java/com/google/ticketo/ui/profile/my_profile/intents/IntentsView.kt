package com.google.ticketo.ui.profile.my_profile.intents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.ticketo.R
import com.google.ticketo.ui.FragmentAdapter
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentBuyView
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentSellView
import com.google.ticketo.utils.Const
import com.google.ticketo.utils.Const.SELL_INTENT
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.intents_fragment.*

class IntentsView : Fragment() {

    lateinit var fragmentAdapter: FragmentAdapter
    lateinit var intent: String

    companion object {
        fun newInstance() = IntentsView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intents_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        intent = arguments?.get("intent") as String
        super.onActivityCreated(savedInstanceState)

        initView()
        addFragments()
        onClicks()
    }

    private fun initView() {
        fragmentAdapter = FragmentAdapter(childFragmentManager)
        intents_view_pager.adapter = fragmentAdapter
        intents_tablayout.setupWithViewPager(intents_view_pager)
    }

    private fun addFragments() {
        fragmentAdapter.addFragment(IntentBuyView(), resources.getString(R.string.you_are_buying))
        fragmentAdapter.addFragment(IntentSellView(), resources.getString(R.string.you_are_selling))
        fragmentAdapter.notifyDataSetChanged()
        checkScroll()
    }

    private fun onClicks(){
        intents_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

    private fun checkScroll(){
        if(intent == SELL_INTENT){
            intents_view_pager.currentItem = 1
        }
    }
}
