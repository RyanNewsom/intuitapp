package com.ryannewsom.intuitinterviewapp.ui.repository.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryannewsom.intuitinterviewapp.R
import com.ryannewsom.intuitinterviewapp.model.GithubRepository

class RepositoryListViewAdapter(
    private val viewDetailsListener: (repoId: Long) -> Unit
) : PagedListAdapter<GithubRepository, RepositoryListViewAdapter.RepoViewHolder>(diffCallback) {
    companion object {
        val diffCallback: DiffUtil.ItemCallback<GithubRepository> =
            object : DiffUtil.ItemCallback<GithubRepository>() {
                override fun areItemsTheSame(
                    oldItem: GithubRepository,
                    newItem: GithubRepository
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: GithubRepository,
                    newItem: GithubRepository
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_item_view, parent, false)

        return RepoViewHolder(
            rootView
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repository = getItem(position) ?: return
        holder.nameTv.text = repository.name
        holder.descriptionTv.text = repository.description
        holder.starsTv.text = repository.stargazersCount.toString()
        holder.watchersTv.text = repository.watchersCount.toString()
        holder.viewDetailsButton.setOnClickListener {
            viewDetailsListener.invoke(repository.id)
        }
    }

    class RepoViewHolder(
        rootView: View
    ) : RecyclerView.ViewHolder(rootView) {
        val nameTv: TextView = rootView.findViewById(R.id.repository_item_name_tv)
        val descriptionTv: TextView = rootView.findViewById(R.id.repository_item_description_tv)
        val starsTv: TextView = rootView.findViewById(R.id.repository_item_stars_tv)
        val watchersTv: TextView = rootView.findViewById(R.id.repository_item_watchers_tv)
        val viewDetailsButton: Button = rootView.findViewById(R.id.view_details_button)
    }
}