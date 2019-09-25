package com.ryannewsom.intuitinterviewapp.ui.issues.list

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.ui.ErrorHandler
import com.ryannewsom.intuitinterviewapp.ui.LoadingHandler
import kotlinx.android.synthetic.main.issues_list_fragment.*
import java.lang.IllegalStateException

class IssuesListFragment : Fragment() {
    private lateinit var viewModel: IssuesListViewModel
    private var loadingHandler: LoadingHandler? = null
    private var errorHandler: ErrorHandler? = null
    private lateinit var adapter: IssuesListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issues_list_fragment, container, false)
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
        val repoId = IssuesListFragmentArgs.fromBundle(requireArguments()).repoId
        viewModel = ViewModelProviders.of(this, IssuesListViewModelFactory(repoId = repoId))
            .get(IssuesListViewModel::class.java)

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

        viewModel.noIssues.observe(viewLifecycleOwner, Observer { noIssues ->
            if (noIssues) {
                issues_list_no_issues_tv.visibility = View.VISIBLE
                issues_list_rv.visibility = View.INVISIBLE
            }
        })

        issues_list_rv.layoutManager = LinearLayoutManager(context)
        adapter = IssuesListViewAdapter { issueId ->
            val action =
                IssuesListFragmentDirections.actionIssuesListFragmentToIssueDetailFragment()
            action.issueId = issueId
            action.repoId = viewModel.repoId
            findNavController().navigate(action)
        }
        issues_list_rv.adapter = adapter
        viewModel.issues.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onStop() {
        super.onStop()

        loadingHandler?.hideLoading()
    }
}
