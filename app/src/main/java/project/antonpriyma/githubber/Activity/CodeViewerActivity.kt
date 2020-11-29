

package com.antonpriyma.githubber.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.activity_code_viewer.*
import com.antonpriyma.githubber.R

class CodeViewerActivity : AppCompatActivity() {

    private lateinit var url: String
    private lateinit var html_url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_viewer)

        if (intent.hasExtra("url"))
            url = intent.getStringExtra("url")

        if (intent.hasExtra("html_url"))
            html_url = intent.getStringExtra("html_url")

        webView.loadUrl(url)


        webView.isVerticalScrollBarEnabled = true
        webView.settings.javaScriptEnabled = true;
        webView.settings.domStorageEnabled = true
        webView.settings.setAppCacheEnabled(true);
        webView.settings.loadsImagesAutomatically = true;
        webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        webView.settings.layoutAlgorithm =
            WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.settings.setSupportZoom(true)
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = false
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true

    }

    fun shareIntent() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT, "${html_url}\n\nShared via *githubber App*\n " +
                        "")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_share -> {
                shareIntent()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }

        }


    }
}
