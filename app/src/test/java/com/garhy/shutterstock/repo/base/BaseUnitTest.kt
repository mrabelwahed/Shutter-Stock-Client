package com.garhy.shutterstock.repo.base


import org.junit.After
import org.junit.Before

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.net.URISyntaxException

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

abstract class BaseUnitTest {

    open lateinit var mockWebServer: MockWebServer

    abstract var isMockWebServerEnabled : Boolean


    @Before
    @Throws(IOException::class)
    fun setup() {
        if (isMockWebServerEnabled) {
            mockWebServer = MockWebServer()
            mockWebServer.start()
        }
    }


    /**
     *
     *
     * learn more about mock web server ...
     * https://android.jlelse.eu/unit-test-api-calls-with-mockwebserver-d4fab11de847
     *
     * @param filename
     * @param responseCode
     */
    open fun mockHttpResponse(filename: String, responseCode: Int) {
        try {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(responseCode)
                    .setBody(getJson(filename))
            )
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

    }


    @Throws(URISyntaxException::class)
    fun getJson(path: String): String {
        //        URI uri = this.getClass().getClassLoader().getResource(path).toURI();
        /*readFile(new File("C:\\hard\\workspace\\Task\\app\\" +
                "build\\intermediates\\classes\\test\\debug\\resources\\" +
                "get_articles_successful_response"));
*/
        //        readFile(new File(this.getClass().getClassLoader().getResource("get_articles_successful_response").toURI()))
        val file = File(this.javaClass.classLoader!!.getResource(path).toURI())


        return readFile(file)

    }

    private fun readFile(file: File): String {
        val text = StringBuilder()
        try {
            val br = BufferedReader(FileReader(file))
           for ( line : String in br.lines()){
               text.append(line)
               text.append('\n')
            }
            br.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            return text.toString()
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        if (isMockWebServerEnabled)
            mockWebServer.shutdown()
    }
}
