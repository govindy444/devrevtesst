package com.example.devrevcustomnetworksdk

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


 public class CustomNetworkSDK {

     companion object {

         public fun get(url: String?, callback: NetworkCallback?) {
             if (url != null) {
                 if (callback != null) {
                     GetRequestAsyncTask(url, callback).execute()
                 }
             }
         }

         fun post(url: String?, paramss: Map<String, String>, callback: NetworkCallback?) {
             if (url != null) {
                 if (callback != null) {
                     PostRequestAsyncTask(url, paramss, callback).execute()
                 }
             }
         }
     }


    private class GetRequestAsyncTask(
        private val url: String,
        private val callback: NetworkCallback
    ) :
        AsyncTask<Void?, Void?, String?>() {
        private var exception: Exception? = null

        override fun onPostExecute(response: String?) {
            if (exception != null) {
                callback.onError(exception!!.message)
            } else {
                callback.onSuccess(response)
            }
        }

        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    response.toString()
                } else {
                    "HTTP Error: $responseCode"
                }
            } catch (e: IOException) {
                e.printStackTrace()
                exception = e
                null
            }
        }
    }


    private class PostRequestAsyncTask(
        private val url: String,
        private val paramss: Map<String, String>,
        private val callback: NetworkCallback
    ) :
        AsyncTask<Void?, Void?, String?>() {
        private var exception: java.lang.Exception? = null
        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                val outputStream = connection.outputStream
                val postData = java.lang.StringBuilder()
                for ((key, value) in paramss) {
                    if (postData.length != 0) {
                        postData.append('&')
                    }
                    postData.append(key)
                        .append('=')
                        .append(value)
                }
//                for ((key, value): Map.Entry<String, String> in params)  {
//                    if (postData.length != 0) postData.append('&')
//                    postData.append(key)
//                    postData.append('=')
//                    postData.append(value)
//                }
                val postDataBytes = postData.toString().toByteArray(charset("UTF-8"))
                outputStream.write(postDataBytes)
                outputStream.flush()
                outputStream.close()
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = java.lang.StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    response.toString()
                } else {
                    "HTTP Error: $responseCode"
                }
            } catch (e: IOException) {
                e.printStackTrace()
                exception = e
                null
            }
        }


        override fun onPostExecute(response: String?) {
            if (exception != null) {
                callback.onError(exception!!.message)
            } else {
                callback.onSuccess(response)
            }
        }

    }

}