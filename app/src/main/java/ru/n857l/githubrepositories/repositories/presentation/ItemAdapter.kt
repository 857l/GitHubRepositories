package ru.n857l.githubrepositories.repositories.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.n857l.githubrepositories.databinding.ItemRepositoryBinding

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val itemList = ArrayList<RepositoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun update(newList: List<RepositoryItem>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}

class ItemViewHolder(private val binding: ItemRepositoryBinding) : ViewHolder(binding.root) {

    fun bind(repositoryItem: RepositoryItem) {
        binding.repositoryName.text = repositoryItem.repositoryName
        binding.programmingLanguage.text = repositoryItem.programmingLanguage
        binding.repositoryDescription.text = repositoryItem.repositoryDescription
    }
}