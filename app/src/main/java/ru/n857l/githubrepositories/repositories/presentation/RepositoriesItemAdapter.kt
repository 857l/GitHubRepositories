package ru.n857l.githubrepositories.repositories.presentation

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.json.JSONObject
import ru.n857l.githubrepositories.databinding.ItemRepositoryBinding
import java.nio.charset.Charset

class RepositoriesItemAdapter(
    private val colorProvider: LanguageColorProvider
) : RecyclerView.Adapter<RepositoriesItemViewHolder>() {

    private val itemList = ArrayList<RepositoryItem>()

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
        holder.bind(itemList[position], colorProvider)
    }

    fun update(newList: List<RepositoryItem>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
}

class RepositoriesItemViewHolder(private val binding: ItemRepositoryBinding) : ViewHolder(binding.root) {

    fun bind(repositoryItem: RepositoryItem, colorProvider: LanguageColorProvider) = with(binding) {
        repositoryName.text = repositoryItem.repositoryName
        programmingLanguage.text = repositoryItem.programmingLanguage
        programmingLanguage.setTextColor(
            colorProvider.getColor(repositoryItem.programmingLanguage)
        )
        repositoryDescription.text = repositoryItem.repositoryDescription
    }
}

class LanguageColorProvider(context: Context) {

    private val colorMap: Map<String, Int>

    init {
        val jsonString = context.assets.open("colors.json")
            .bufferedReader(Charset.forName("UTF-8"))
            .use { it.readText() }

        val jsonObject = JSONObject(jsonString)
        val tempMap = mutableMapOf<String, Int>()
        jsonObject.keys().forEach { key ->
            tempMap[key] = Color.parseColor(jsonObject.getString(key))
        }
        colorMap = tempMap
    }

    fun getColor(language: String): Int {
        return colorMap[language] ?: Color.WHITE
    }
}

//TODO DIffUtil
//TODO SaveStateScroll