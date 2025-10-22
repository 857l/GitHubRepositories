package ru.n857l.githubrepositories.cloud_datasource

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GitHubApiService {

    @GET("/user/repos")
    suspend fun fetchRepositories(
        @Header("Authorization") token: String,
        @Query("sort") sort: String = "updated",
        @Query("per_page") amount: Int = 10,
        @Query("affiliation") affiliation: String = "owner"
    ): List<RepositoryCloud>
}