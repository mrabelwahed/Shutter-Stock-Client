package com.garhy.shutterstock.view.details

import android.os.Bundle
import android.view.View
import com.garhy.shutterstock.R
import com.garhy.shutterstock.view.BaseFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : BaseFragment() {

    companion object {
        private const val BUNDLE_KEY_IMAGE_URL = "IMAGE_URL"

        fun newInstance(url: String?): DetailsFragment {
            val args = Bundle()
            args.apply {
                putString(BUNDLE_KEY_IMAGE_URL, url)
            }
            val detailsFragment = DetailsFragment()
            detailsFragment.arguments = args
            return detailsFragment

        }
    }

    override fun handleRetryAction() {}

    override fun getLayoutId(): Int {
        return R.layout.fragment_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        details_motion_layout.transitionToEnd()
        val imageURl = arguments?.getString(BUNDLE_KEY_IMAGE_URL)

        Picasso.get().load(imageURl)
            .error(R.drawable.broken_image).placeholder(R.mipmap.ic_launcher)
            .into(details_image_view, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                }

            })

    }
}