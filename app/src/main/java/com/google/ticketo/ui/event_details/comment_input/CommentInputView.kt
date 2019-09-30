package com.google.ticketo.ui.event_details.comment_input

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.comment_input_fragment.*

class CommentInputView : Fragment() {

    companion object {
        fun newInstance() = CommentInputView()
    }

    private lateinit var viewModel: CommentInputViewModel
    private lateinit var imgr: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imgr = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        return inflater.inflate(R.layout.comment_input_fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val eventId = arguments?.get("eventId") as String
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, CommentInputFactory(context!!, eventId)).get(CommentInputViewModel::class.java)

        initView()
        onClicks()
        textListener()
    }

    private fun initView(){
        startTyping()
    }

    private fun onClicks(){
        comment_input_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

    private fun textListener(){
        comment_input_edit_text.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s : CharSequence?, p1: Int, p2: Int, p3: Int) {
                comment_input_send.isVisible = s?.length!=0
            }

        })
    }

    private fun startTyping(){
        comment_input_edit_text.requestFocus()
        imgr.toggleSoftInput(
            InputMethodManager.SHOW_IMPLICIT,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}
