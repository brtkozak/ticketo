package com.google.ticketo.utils

import android.view.View
import androidx.navigation.findNavController

object NavigationUtils{

    fun backPress(view : View){
        view.findNavController().popBackStack()
    }

}