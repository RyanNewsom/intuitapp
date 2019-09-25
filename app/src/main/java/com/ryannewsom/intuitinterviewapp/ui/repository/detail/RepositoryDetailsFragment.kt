package com.ryannewsom.intuitinterviewapp.ui.repository.detail

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.ui.ErrorHandler
import kotlinx.android.synthetic.main.repository_details_fragment.*
import java.lang.IllegalStateException

class RepositoryDetailsFragment : Fragment() {
    private lateinit var viewModel: RepositoryDetailsViewModel
    private var errorHandler: ErrorHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repository_details_fragment, container, false)
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
        viewModel = ViewModelProviders.of(
            this,
            RepositoryDetailsViewModelFactory(
                repoId = RepositoryDetailsFragmentArgs.fromBundle(
                    requireArguments()
                ).repoId
            )
        ).get(RepositoryDetailsViewModel::class.java)

        viewModel.repository.observe(viewLifecycleOwner, Observer {
            (activity as? AppCompatActivity)?.supportActionBar?.title = it.name
            repository_details_about_content_tv.text = it.description
            repository_details_stars_tv.text = it.stargazersCount.toString()
            repository_details_watchers_tv.text = it.watchersCount.toString()
            repository_details_forks_tv.text = it.forksCount.toString()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                errorHandler?.showError(it) {
                    viewModel.retry()
                }
            }
        })

        view_issues_button.setOnClickListener {
            val action =
                RepositoryDetailsFragmentDirections.actionRepositoryDetailsFragmentToIssuesListFragment()
            action.repoId = viewModel.repoId
            findNavController().navigate(action)
        }
    }

}
