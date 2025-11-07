package ru.n857l.githubrepositories.cloudDatasource

import com.google.gson.annotations.SerializedName

data class RepositoryCloud(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("license")
    val license: License?,
    @SerializedName("forks_count")
    val forks: Int,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("watchers_count")
    val watchers: Int,
    @SerializedName("default_branch")
    val branch: String
) {
    data class License(
        @SerializedName("name")
        val name: String
    )

    data class Owner(
        @SerializedName("login")
        val name: String
    )

    data class Readme(
        @SerializedName("content")
        val content: String
    )
}