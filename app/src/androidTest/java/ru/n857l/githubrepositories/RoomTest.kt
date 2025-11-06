package ru.n857l.githubrepositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesCache
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDao
import ru.n857l.githubrepositories.core.cache.repositories.RepositoriesDatabase

@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: RepositoriesDao
    private lateinit var database: RepositoriesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            RepositoriesDatabase::class.java
        ).build()
        dao = database.dao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun test() = runBlocking {
        dao.saveAll(
            listOf(
                RepositoriesCache(
                    id = 1,
                    name = "Archive",
                    htmlUrl = "https://github.com/Daviex/Steins-Gate-Archive-Decompiler",
                    description = "...",
                    language = "SERN",
                    license = "IBN 5100",
                    forks = 10,
                    stars = 2,
                    watchers = 34,
                )
            )
        )

        var actual: List<RepositoriesCache> = dao.getAll()
        var expected: List<RepositoriesCache> = listOf(
            RepositoriesCache(
                id = 1,
                name = "Archive",
                htmlUrl = "https://github.com/Daviex/Steins-Gate-Archive-Decompiler",
                description = "...",
                language = "SERN",
                license = "IBN 5100",
                forks = 10,
                stars = 2,
                watchers = 34,
            )
        )
        assertEquals(actual, expected)

        dao.clear()
        actual = dao.getAll()
        expected = listOf()
        assertEquals(actual, expected)
    }
}