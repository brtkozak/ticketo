package com.google.ticketo.ui.event_details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.ticketo.databinding.EventDetailsFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.event_details_fragment.*
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.google.ticketo.R
import com.google.ticketo.utils.Const.BUYERS
import com.google.ticketo.utils.Const.BUY_INTENT
import com.google.ticketo.utils.Const.SELLERS
import com.google.ticketo.utils.Const.SELL_INTENT
import java.text.SimpleDateFormat

class EventDetailsView : Fragment() {

    companion object {
        fun newInstance() = EventDetailsView()
    }

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var binding: EventDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.event_details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val eventId = arguments?.get("eventId") as String

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(context!!)
        ).get(
            EventDetailsViewModel::class.java
        )
        viewModel.eventId = eventId
        viewModel.setEvent()
        setObservers()
        onClicks()
    }


    @SuppressLint("SimpleDateFormat")
    private fun setObservers() {
        viewModel.event.observe(this, Observer {
            binding.event = it
            val dateFormat = SimpleDateFormat("E dd.MM.yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            event_details_date.text = dateFormat.format(it.startDate)
            event_details_time.text = timeFormat.format(it.startDate)
            setFavouriteHeart(it.favourite)
        })

        viewModel.getBuyers().observe(this, Observer {
            event_details_buyers.text = it.size.toString()
        })

        viewModel.getSellers().observe(this, Observer {
            event_details_sellers.text = it.size.toString()
        })

        viewModel.initState.observe(this, Observer {
            if (it == BUY_INTENT) {
                event_details_buy.isSelected = true
            } else if (it == SELL_INTENT) {
                event_details_sell.isSelected = true
            }
        })

        viewModel.buyLock.observe(this, Observer {
            if (it == 0) {
                unlockBuy()
            } else {
                lockBuy()
            }
        })

        viewModel.sellLock.observe(this, Observer {
            if (it == 0) {
                unlockSell()
            } else {
                lockSell()
            }
        })

        viewModel.layoutReady.observe(this, Observer { // TODO USE TO LOAD REST OF EVENT DATA
        })
    }

    private fun onClicks() {
        event_details_buy.setOnClickListener {
            onBuyClick()
            setSelectedState(it, event_details_sell)
        }

        event_details_sell.setOnClickListener {
            onSellClick()
            setSelectedState(it, event_details_buy)
        }

        event_details_back.setOnClickListener {
            view!!.findNavController().popBackStack()
        }

        event_details_favourite.setOnClickListener {
            if (it.isSelected)
                viewModel.removeFromFavourites()
            else
                viewModel.addToFavourites()
            setSelectedState(it)
        }
    }

    private fun onBuyClick() {
        if (!event_details_buy.isSelected && !event_details_sell.isSelected) {
            viewModel._buyLock.value = 1
            viewModel.addToBuyers()
        } else if (!event_details_buy.isSelected && event_details_sell.isSelected) {
            viewModel._buyLock.value = 2
            viewModel.addToBuyers()
            viewModel.removeFromSellers(BUYERS)
        } else {
            viewModel._buyLock.value = 1
            viewModel.removeFromBuyers(BUYERS)
        }
    }

    private fun onSellClick() {
        if (!event_details_sell.isSelected && !event_details_buy.isSelected) {
            viewModel._sellLock.value = 1
            viewModel.addToSellers()
        } else if (!event_details_sell.isSelected && event_details_buy.isSelected) {
            viewModel._sellLock.value = 2
            viewModel.addToSellers()
            viewModel.removeFromBuyers(SELLERS)
        } else {
            viewModel._sellLock.value = 1
            viewModel.removeFromSellers(SELLERS)
        }
    }

    private fun setSelectedState(it: View?, toUnCheck: View? = null) {
        it?.isSelected = !it?.isSelected!!

        if (toUnCheck != null && it.isSelected && toUnCheck.isSelected) {
            toUnCheck.isSelected = false
        }
    }

    private fun lockBuy() {
        event_details_buy.isVisible = false
        event_details_progress_buy.isVisible = true
        event_details_sell.isClickable = false
    }

    private fun unlockBuy() {
        event_details_buy.isVisible = true
        event_details_progress_buy.isVisible = false
        event_details_sell.isClickable = true
    }

    private fun lockSell() {
        event_details_sell.isVisible = false
        event_details_progress_sell.isVisible = true
        event_details_buy.isClickable = false
    }

    private fun unlockSell() {
        event_details_sell.isVisible = true
        event_details_progress_sell.isVisible = false
        event_details_buy.isClickable = true
    }

    private fun setFavouriteHeart(favouriteState: Boolean) {
        event_details_favourite_progressbar.isVisible = false
        event_details_favourite.isVisible = true
        event_details_favourite.isSelected = favouriteState

    }
}
