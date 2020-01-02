package com.garhy.shutterstock.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search.*

fun Fragment.hideKeyboard() {
    val imm =
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(search_view.windowToken, 0)
}