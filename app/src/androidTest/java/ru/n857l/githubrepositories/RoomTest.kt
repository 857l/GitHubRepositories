package ru.n857l.githubrepositories

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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

    private val repo = RepositoriesCache(
        name = "Archive",
        owner = "John Titor",
        htmlUrl = "https://github.com/Daviex/Steins-Gate-Archive-Decompiler",
        description = "...",
        language = "SERN",
        license = "IBN 5100",
        forks = 10,
        stars = 2,
        watchers = 34,
        readme = "El Psy Congroo"
    )

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
    fun testRoomDao() = runBlocking {
        dao.saveAll(listOf(repo))

        var actualList = dao.getAll()
        assertEquals(actualList, listOf(repo))

        var actualRepo = dao.getRepositoryByName("Unknown")
        assertNull(actualRepo)

        actualRepo = dao.getRepositoryByName("Archive")
        assertEquals(repo, actualRepo)

        var actualOwner = dao.getOwnerByName("Unknown")
        assertNull(actualOwner)

        actualOwner = dao.getOwnerByName("Archive")
        assertEquals("John Titor", actualOwner)

        var actualReadme = dao.getReadmeByName("Unknown")
        assertNull(actualReadme)

        actualReadme = dao.getReadmeByName("Archive")
        assertEquals("El Psy Congroo", actualReadme)

        dao.updateReadme("Archive", "UPDATED_README")
        actualReadme = dao.getReadmeByName("Archive")
        assertEquals("UPDATED_README", actualReadme)

        dao.clear()
        actualList = dao.getAll()
        assertTrue(actualList.isEmpty())
    }
}