package com.google.ticketo.ui.profile.my_profile.intents.intents_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.ticketo.R
import com.google.ticketo.ui.ViewModelFactory
import kotlinx.android.synthetic.main.intent_list_fragment.*

class IntentSellView : Fragment(), IntentAdapter.IntentCallback {

    companion object {
        fun newInstance() =
            IntentBuyView()
    }

    private lateinit var viewModel: IntentShareViewModel
    private lateinit var adapter: IntentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.intent_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, ViewModelFactory(context!!)).get(
            IntentShareViewModel::class.java)

        initView()
        observers()
    }

    private fun initView() {
        intent_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = IntentAdapter(context!!, this)
        intent_list.adapter = adapter
    }

    fun observers(){
        viewModel.sellEvents.observe(this, Observer {
            adapter.events = it
            adapter.notifyDataSetChanged()
            intent_list_empty_info.isVisible = it.isEmpty()
        })
    }

    override fun goToDetails(eventId: String) {
        val bundle = bundleOf(
            "eventId" to eventId
        )
        view!!.findNavController()
            .navigate(R.id.action_intentsView_to_eventDetailsView, bundle, null, null)
    }

    override fun onFavouriteClick(eventId: String, state: Boolean) {
        viewModel.updateFavourites(eventId, state)
    }

}
