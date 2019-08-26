package com.example.httpurlconnection

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btnHttp).setOnClickListener {
            val asyncTask = AsyncTaskExample()
            asyncTask.execute("https://www.google.com/")
            Toast.makeText(applicationContext,"Response code is : "+asyncTask.get()[0],Toast.LENGTH_SHORT).show()
            Log.i("response_code",asyncTask.get()[1])
            findViewById<TextView>(R.id.tv_body).text=asyncTask.get()[1]
            }
        }

    private inner class AsyncTaskExample : AsyncTask<String, String, Array<String>>() {
        var code = 0
        var body = ""
        override fun doInBackground(vararg strings: String): Array<String> {
            try {
                val url = URL(strings[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod="GET"
                try {
                    body = URL(strings[0]).readText()
                    code = urlConnection.responseCode

                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return arrayOf(code.toString(),body)
        }

    }
}
