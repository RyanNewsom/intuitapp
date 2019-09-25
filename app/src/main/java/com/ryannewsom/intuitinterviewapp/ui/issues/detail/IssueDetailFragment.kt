package com.ryannewsom.intuitinterviewapp.ui.issues.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.ui.ErrorHandler
import kotlinx.android.synthetic.main.issue_detail_fragment.*
import java.lang.IllegalStateException

class IssueDetailFragment : Fragment() {
    private lateinit var viewModel: IssueDetailViewModel
    private var errorHandler: ErrorHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issue_detail_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ErrorHandler) {
            errorHandler = context
        } else {
            throw IllegalStateException("Activities containing this fragment must implement ErrorHandler")
        }
    }

    override fun onDetach() {
        super.onDetach()

        errorHandler = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = IssueDetailFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProviders.of(
            this, IssueDetailViewModelFactory(
                repoId = args.repoId,
                issueId = args.issueId
            )
        ).get(IssueDetailViewModel::class.java)

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                errorHandler?.showError(it) {
                    viewModel.retry()
                }
            }
        })

        viewModel.issue.observe(viewLifecycleOwner, Observer {
            (activity as? AppCompatActivity)?.supportActionBar?.title =
                getString(R.string.issue_number, it.number)
            issue_detail_title_tv.text = it.title
            issue_detail_body_tv.text = it.body
            issue_detail_status_tv.text = it.state
        })
    }

}
