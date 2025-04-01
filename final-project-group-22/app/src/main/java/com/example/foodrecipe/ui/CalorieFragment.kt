package com.example.foodrecipe.ui
import android.content.Context
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.foodrecipe.R

class CalorieFragment : Fragment(R.layout.calorie_analysis_fragment) {
    lateinit var printJob: PrintJob

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? )
            : View? {
        val rootView: View = inflater.inflate(R.layout.calorie_analysis_fragment, container, false)
        val url = "https://happyforks.com/analyzer"
        var savePdfBtn = rootView.findViewById(R.id.print_button) as Button
        val view = rootView.findViewById<View>(R.id.idWebView) as WebView
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)
        savePdfBtn.setOnClickListener {
            printWebPage(view)
        }
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun printWebPage(webView: WebView) {
        val printManager = activity?.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Calorie Analysis: " + webView.url
        val printAdapter = webView.createPrintDocumentAdapter(jobName)
        val printAttributes = PrintAttributes.Builder()
        printJob = printManager.print(
            jobName, printAdapter,
            printAttributes.build()
        )
    }

    override fun onResume() {
        super.onResume()
    }
}
