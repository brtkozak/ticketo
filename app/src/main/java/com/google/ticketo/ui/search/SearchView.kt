package com.google.ticketo.ui.search

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.ticketo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment.*

class SearchView : Fragment() {

    companion object {
        fun newInstance() = SearchView()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var imgr: InputMethodManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
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
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        startTyping()
        listeners()
        onClicks()
    }

    private fun startTyping() {
        search_fragment_input.requestFocus()
        imgr.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun onClicks() {
        search_fragment_back.setOnClickListener {
            view!!.findNavController().popBackStack()
        }
        search_fragment_clear.setOnClickListener {
            search_fragment_input.text?.clear()
        }
    }

    private fun listeners() {
        search_fragment_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(e: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {  // sam nazywalem te pola wiec moze byc zle
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    search_fragment_clear.visibility = View.VISIBLE
                } else {
                    search_fragment_clear.visibility = View.GONE
                }
            }

        })
    }

}
