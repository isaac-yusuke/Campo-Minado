package com.example.campominado

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Iniciar o servidor local
        val server = LocalWebServer(this)
        server.start()

        // Copiar o arquivo HTML e JSON para o armazenamento interno
        val htmlFile = File(filesDir, "localfile.html")
        assets.open("localfile.html").use { input ->
            htmlFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val jsonFile = File(filesDir, "graph_state.json")
        assets.open("graph_state.json").use { input ->
            jsonFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        // Configurar o WebView
        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()
        setContentView(webView)

        // Carregar o arquivo HTML via servidor local
        webView.loadUrl("http://localhost:8080/localfile.html")
    }
}

// Função Composable para Preview - nao funcionou
@Composable
fun WebViewComposablePreview() {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            loadUrl("http://localhost:8080/localfile.html")
        }
    })
}

// Função Preview para o Android Studio
@Preview(showBackground = true)
@Composable
fun PreviewWebView() {
    WebViewComposablePreview()
}
