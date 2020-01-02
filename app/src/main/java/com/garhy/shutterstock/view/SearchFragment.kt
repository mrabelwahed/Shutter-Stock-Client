package com.garhy.shutterstock.view




import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.garhy.shutterstock.R
import com.garhy.shutterstock.di.search.DaggerSearchFragmentComponent
import com.garhy.shutterstock.model.ApiResponse
import com.garhy.shutterstock.network.ApiResource
import com.garhy.shutterstock.utils.hideKeyboard
import com.garhy.shutterstock.view.adapter.ImagesRecyclerViewAdapter
import com.garhy.shutterstock.view.details.DetailsFragment
import com.garhy.shutterstock.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


private const val INITIAL_QUERY = "Germany"

class SearchFragment : BaseFragment() {
    var lastQuery: String? = null
    var resetData: Boolean = false
    var nextPage = 1

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var imagesRecyclerViewAdapter: ImagesRecyclerViewAdapter


    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (component as DaggerSearchFragmentComponent).inject(this)
        addListeners()
        initUi()
        startAppFlow()
    }

    private fun addListeners() {
        addOnQueryTextListener()
        addOnScrollListener()
    }

    private fun initUi() {
        images_rv.adapter = imagesRecyclerViewAdapter
        imagesRecyclerViewAdapter.images = ArrayList()
        imagesRecyclerViewAdapter.onItemClickListener = (object : OnItemClickListener {
            override fun onItemClick(url: String?) {
                hideKeyboard()
                (activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(DetailsFragment.javaClass.name)
                    ?.add(R.id.fragment_container_view, DetailsFragment.newInstance(url))?.commit())
            }
        })
        viewModel.imagesLiveData.removeObservers(this@SearchFragment as LifecycleOwner)
        viewModel.imagesLiveData.observe(viewLifecycleOwner, Observer {
            onLiveDataChanged(it)
        })

    }


    private fun startAppFlow() {
        // submit initial query to fetch images
        search_view.setQuery(INITIAL_QUERY, true)
    }

    // handle query changed
    private fun addOnQueryTextListener() {
        search_view.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // check if the user search using another query .. so we need to reset the recycler view and adapter
                resetData = lastQuery != query
                if (resetData) {
                    fetchData()
                    lastQuery = query
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    // ask view model to get the data
    private fun fetchData() {
        if (resetData) {
            resetViews()
        }
        hideKeyboard()
        viewModel.fetchImages(search_view.query.toString(), nextPage)
    }

    private fun resetViews() {
        // reset the pagination
        nextPage = 1
        // reset recycler view
        imagesRecyclerViewAdapter.images = ArrayList()
        imagesRecyclerViewAdapter.notifyDataSetChanged()
    }


    private fun onLiveDataChanged(apiResource: ApiResource<ApiResponse>) {
        when (apiResource.status) {
            ApiResource.Status.LOADING -> {
                // handle loading status
                showHideLoading(loading_view, true)
                incrementDecrementIdleResource(true)
            }
            ApiResource.Status.LOADED_SUCCESSFULLY -> {
                // handle data loaded
                handleDataLoaded(apiResource)
                incrementDecrementIdleResource(false)
            }
            ApiResource.Status.ERROR_OCCURRED -> {
                apiResource.exception?.let {
                    showHideLoading(loading_view, false)
                    // handle errors
                    handleErrorState(it)
                }
                incrementDecrementIdleResource(false)

            }
        }
    }


    override fun handleRetryAction() {
        fetchData()
    }

    private fun handleDataLoaded(apiResponse: ApiResource<ApiResponse>) {
        showHideLoading(loading_view, false)
        nextPage += 1
        (apiResponse.data as ApiResponse).imageModels?.let { it ->
            if (it.isEmpty() && imagesRecyclerViewAdapter.images.isEmpty()) {
                showEmptyView(true)
            } else {
                showEmptyView(false)
                imagesRecyclerViewAdapter.images.addAll(it)
            }
        }
        imagesRecyclerViewAdapter.notifyDataSetChanged()

    }

    private fun showEmptyView(showEmptyView: Boolean) {
        empty_view_group.visibility = if (showEmptyView) VISIBLE else GONE
    }

    private fun addOnScrollListener() {
        images_rv.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (imagesRecyclerViewAdapter.isLastItemDisplaying(recyclerView) &&
                    loading_view.visibility == GONE
                ) {
                    viewModel.fetchImages(search_view.query.toString(), nextPage)
                }
            }
        })
    }
}

interface OnItemClickListener {
    fun onItemClick(url: String?)
}
