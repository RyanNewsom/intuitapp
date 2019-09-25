package com.ryannewsom.intuitinterviewapp.ui.repository.list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.ui.ErrorHandler
import com.ryannewsom.intuitinterviewapp.ui.LoadingHandler
import kotlinx.android.synthetic.main.repositories_list_fragment.*
import timber.log.Timber
import java.lang.IllegalStateException

class RepositoryListFragment : Fragment() {
    private lateinit var viewModel: RepositoryListViewModel
    private var loadingHandler: LoadingHandler? = null
    private var errorHandler: ErrorHandler? = null
    private lateinit var adapter: RepositoryListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.repositories_list_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LoadingHandler && context is ErrorHandler) {
            loadingHandler = context
            errorHandler = context
        } else {
            throw IllegalStateException("Activities containing this fragment must implement LoadingHandler, ErrorHandler")
        }
    }

    override fun onDetach() {
        super.onDetach()

        loadingHandler = null
        errorHandler = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            RepositoryListViewModelFactory()
        ).get(RepositoryListViewModel::class.java)
        repositories_list_rv.layoutManager = LinearLayoutManager(context)
        adapter = RepositoryListViewAdapter { repoId ->
            val action =
                RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment()
            action.repoId = repoId
            findNavController().navigate(action)
        }
        repositories_list_rv.adapter = adapter

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                loadingHandler?.showLoading()
            } else {
                loadingHandler?.hideLoading()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                errorHandler?.showError(it) {
                    viewModel.retry()
                }
            }
        })

        viewModel.repositories.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onStop() {
        super.onStop()

        loadingHandler?.hideLoading()
    }
}
