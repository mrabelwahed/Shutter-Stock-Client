package com.garhy.shutterstock.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.garhy.shutterstock.ApplicationClass
import com.garhy.shutterstock.di.search.DaggerSearchFragmentComponent
import com.garhy.shutterstock.exceptions.AppException
import com.garhy.shutterstock.utils.ShutterLogger

abstract class BaseFragment : Fragment() {
    private var idleCountingIdlingResource: CountingIdlingResource? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    lateinit var component: Any
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component = DaggerSearchFragmentComponent.builder()
            .appComponent((activity?.application as ApplicationClass).appComponent).build()
        getIdlingResource()
    }


    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (idleCountingIdlingResource == null)
            idleCountingIdlingResource = CountingIdlingResource("idlingResource")
        return idleCountingIdlingResource

    }

    open fun showHideLoading(loadingView: View, show: Boolean) {
        loadingView.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun incrementDecrementIdleResource(increment: Boolean) {
        if (increment)
            idleCountingIdlingResource?.increment()
        else
            idleCountingIdlingResource?.decrement()
    }



    open fun handleErrorState(exception: AppException) {
        ShutterLogger.log(exception)
        context?.let {
            val alertDialogBuilder = AlertDialog.Builder(it)
            alertDialogBuilder.setMessage(AppException.getErrorMessage(exception))
            alertDialogBuilder.setPositiveButton(if (exception.enableRetryProcess) "Retry" else "OK") {
                    dialog: DialogInterface?, which:Int ->

                    dialog?.dismiss()
                    if (exception.enableRetryProcess)
                        handleRetryAction()

            }
            alertDialogBuilder.setCancelable(true)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    abstract fun handleRetryAction()


}