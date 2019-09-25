package com.ryannewsom.intuitinterviewapp.ui.issues.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.model.GithubIssue
import kotlinx.android.synthetic.main.issue_item_view.view.*

class IssuesListViewAdapter(
    private val buttonClickListener: (issueId: Long) -> Unit
) : PagedListAdapter<GithubIssue, IssuesListViewAdapter.IssueViewHolder>(diffCallback) {

    class IssueViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val titleTv = rootView.findViewById<TextView>(R.id.issue_item_title_tv)
        val openOrClosedTv = rootView.findViewById<TextView>(R.id.issue_item_open_or_closed)
        val issueNumberTv = rootView.findViewById<TextView>(R.id.issue_item_number_tv)
        val viewMoreButton = rootView.findViewById<Button>(R.id.view_details_button)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        val issue = getItem(position) ?: return

        holder.titleTv.text = issue.title
        holder.openOrClosedTv.text = issue.state
        holder.issueNumberTv.text = issue.number.toString()
        holder.viewMoreButton.setOnClickListener {
            buttonClickListener.invoke(issue.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.issue_item_view, parent, false)

        return IssueViewHolder(
            rootView
        )
    }

    companion object {
        val diffCallback: DiffUtil.ItemCallback<GithubIssue> =
            object : DiffUtil.ItemCallback<GithubIssue>() {
                override fun areItemsTheSame(
                    oldItem: GithubIssue,
                    newItem: GithubIssue
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: GithubIssue,
                    newItem: GithubIssue
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}