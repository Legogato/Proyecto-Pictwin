/*
 * Copyright (c) 2024. Desarrollo de Soluciones Moviles, DISC.
 */

package cl.ucn.disc.dsm.pictwin.data.repository

import androidx.core.app.RemoteInput.EditChoicesBeforeSending
import cl.ucn.disc.dsm.pictwin.data.network.NetworkModule
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class RepositoryTest {

    private var log = LoggerFactory.getLogger(Repository::class.java)
    private lateinit var repository: Repository

    @Before
    fun setup(){
        val gson = NetworkModule.provideGson()
        val api = NetworkModule.provideApiService(gson)

    }

    @Test
    fun testAutheticate(){
        log.debug("Testing autheticate")

        val result = repository.authenticate("durrutia@ucn.cl", "durrutia123")
        result.onSuccess { log.debug("successful", it) }
            .onFailure { log.error("failed", it) }
    }
}