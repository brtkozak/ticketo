package com.google.ticketo.ui.search

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ticketo.R
import com.google.ticketo.ui.ViewModelFactory
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.search_fragment.*
import java.util.*

class SearchView : Fragment(), ResultAdapter.SearchCallback {

    companion object {
        fun newInstance() = SearchView()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var imgr: InputMethodManager
    private lateinit var adapter: ResultAdapter
    private var timer = Timer()
    private val DELAY: Long = 500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imgr = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(context!!)
        ).get(SearchViewModel::class.java)

        initView()
        observers()
        startTyping()
        listeners()
        onClicks()
    }

    private fun initView() {
        search_result.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = ResultAdapter(context!!, this)
        search_result.adapter = adapter
    }

    private fun observers() {
        viewModel.names.observe(this, Observer {
            adapter.nameResults = it
            adapter.notifyDataSetChanged()
        })

        viewModel.locations.observe(this, Observer {
            adapter.locationResults = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun startTyping() {
        search_fragment_input.requestFocus()
        imgr.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    private fun onClicks() {
        search_fragment_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
        search_fragment_clear.setOnClickListener {
            search_fragment_input.text?.clear()
        }
    }

    private fun listeners() {
        search_fragment_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        if (e.toString().isNotEmpty())
                            viewModel.search(e.toString())
                    }
                }, DELAY)
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    search_fragment_clear.visibility = View.VISIBLE
//                    viewModel.search(s.toString())
                } else {
                    search_fragment_clear.visibility = View.GONE
                    adapter.nameResults = null
                    adapter.locationResults = null
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }

    override fun goToDetails(eventId: String) {
        hideKeyboard()
        val bundle = bundleOf(
            "eventId" to eventId
        )
        view!!.findNavController()
            .navigate(R.id.action_searchView_to_eventDetailsView, bundle, null, null)
    }

    override fun searchByCity(city: String) {
        hideKeyboard()
        val bundle = bundleOf(
            "city" to city
        )
        view!!.findNavController()
            .navigate(R.id.action_searchView_to_eventsByCityView, bundle, null, null)
    }

    private fun hideKeyboard(){
        imgr.hideSoftInputFromWindow(view!!.windowToken,0 )
    }
}
