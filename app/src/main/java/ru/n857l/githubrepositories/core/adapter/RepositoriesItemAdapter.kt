package ru.n857l.githubrepositories.core.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.json.JSONObject
import ru.n857l.githubrepositories.databinding.ItemRepositoryBinding
import ru.n857l.githubrepositories.repositories.presentation.RepositoryItem
import java.nio.charset.Charset

class RepositoriesItemAdapter(
    private val colorProvider: LanguageColorProvider,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<RepositoriesItemViewHolder>() {

    private val itemList = ArrayList<RepositoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesItemViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepositoriesItemViewHolder(binding, colorProvider, clickListener)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: RepositoriesItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun update(newList: List<RepositoryItem>) {
        val diffCallback = DiffUtilCallback(itemList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        itemList.clear()
        itemList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class RepositoriesItemViewHolder(
    private val binding: ItemRepositoryBinding,
    private val colorProvider: LanguageColorProvider,
    private val clickListener: ClickListener
) : ViewHolder(binding.root) {

    fun bind(item: RepositoryItem) = with(binding) {
        repositoryName.text = item.repositoryName
        programmingLanguage.text = item.programmingLanguage
        programmingLanguage.setTextColor(
            colorProvider.getColor(item.programmingLanguage)
        )
        repositoryDescription.text = item.repositoryDescription

        root.setOnClickListener {
            clickListener.click(item)
        }
    }
}

class DiffUtilCallback(
    private val oldList: List<RepositoryItem>,
    private val newList: List<RepositoryItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].repositoryName == newList[newItemPosition].repositoryName

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}

fun interface ClickListener {
    fun click(item: RepositoryItem)
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