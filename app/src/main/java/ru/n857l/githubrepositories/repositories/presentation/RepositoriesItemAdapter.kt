package ru.n857l.githubrepositories.repositories.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.n857l.githubrepositories.databinding.ItemRepositoryBinding

class RepositoriesItemAdapter : RecyclerView.Adapter<RepositoriesItemViewHolder>() {

    private val itemList = ArrayList<RepositoryItemUiState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesItemViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoriesItemViewHolder(binding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: RepositoriesItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun update(newList: List<RepositoryItemUiState>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}

class RepositoriesItemViewHolder(private val binding: ItemRepositoryBinding) : ViewHolder(binding.root) {

    fun bind(repositoryItemUiState: RepositoryItemUiState) {
        binding.repositoryName.text = repositoryItemUiState.repositoryName
        binding.programmingLanguage.text = repositoryItemUiState.programmingLanguage
        binding.repositoryDescription.text = repositoryItemUiState.repositoryDescription
    }
}

//TODO DIffUtil
//TODO SaveState