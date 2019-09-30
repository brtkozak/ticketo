package com.google.ticketo.ui.profile.my_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras

import com.google.ticketo.R
import com.google.ticketo.databinding.MyProfileFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory
import com.google.ticketo.utils.Const
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.dashboard_fragment.*
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
        binding = DataBindingUtil.inflate(inflater, R.layout.my_profile_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, RepositoryViewModelFactory(context!!)).get(
            MyProfileViewModel::class.java
        )

        observers()
        onClicks()

    }

    private fun observers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.user = it
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (!it) {
                my_profile_progress_bar.visibility = View.GONE
                my_profile_container.visibility = View.VISIBLE
            }
        })

        viewModel.buyCounter.observe(viewLifecycleOwner, Observer {
            my_profile_buy_counter.text = it.toString()
        })

        viewModel.sellCounter.observe(viewLifecycleOwner, Observer {
            my_profile_sell_counter.text = it.toString()
        })

        viewModel.recommendations.observe(viewLifecycleOwner, Observer {
            my_profile_recommend.text = it.size.toString()
        })

        viewModel.warnings.observe(viewLifecycleOwner, Observer {
            my_profile_warn.text = it.size.toString()
        })
    }

    private fun onClicks() {
        my_profile_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }

        my_profile_buy_container.setOnClickListener {
            val bundle = bundleOf(
                "intent" to Const.BUY_INTENT
            )

            view!!.findNavController()
                .navigate(R.id.action_myProfileView_to_intentsView, bundle, null, null)
        }

        my_profile_sell_container.setOnClickListener {
            val bundle = bundleOf(
                "intent" to Const.SELL_INTENT
            )

            view!!.findNavController()
                .navigate(R.id.action_myProfileView_to_intentsView, bundle, null, null)
        }
    }
}
