package com.isaacyyanagui.campominado

import android.content.Context
import fi.iki.elonen.NanoHTTPD
import java.io.InputStream

class LocalWebServer(private val context: Context) : NanoHTTPD(8080) {

    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri.removePrefix("/")
        return try {
            val inputStream: InputStream = context.assets.open(uri)
            newFixedLengthResponse(
                Response.Status.OK,
                getMimeType(uri),
                inputStream,
                inputStream.available().toLong()
            )
        } catch (e: Exception) {
            newFixedLengthResponse(
                Response.Status.NOT_FOUND,
                "text/plain",
                "File not found"
            )
        }
    }

    private fun getMimeType(uri: String): String {
        return when {
            uri.endsWith(".html") -> "text/html"
            uri.endsWith(".json") -> "application/json"
            else -> "text/plain"
        }
    }
}
