package com.msid.livetabapay.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.msid.livetabapay.R
import com.msid.livetabapay.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var btnEnterSite: Button
    private lateinit var  viewModel: MainViewModel

    private var smallData: String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set up Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "TabaPay Security Logs"

        btnEnterSite = findViewById(R.id.btnEnterSite)


        Thread {
            try {
                val url = URL("https://demos.sandbox.tabapay.net/small.txt")
                val uc: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                val br = BufferedReader(InputStreamReader(uc.getInputStream()))
                var line: String?
                val lin2 = StringBuilder()
                while (br.readLine().also { line = it } != null) {
                    lin2.append("\n",line)
                }
                Log.d("The Text", "$lin2")
                smallData=lin2.toString()
            } catch (e: IOException) {
                Log.d("texts", "onClick: " + e.getLocalizedMessage())
                e.printStackTrace()
            }
        }.start()





        btnEnterSite.setOnClickListener {
            val intent = Intent(this@MainActivity,EmployeeActivity::class.java)
            intent.putExtra("lin2",smallData)
            startActivity(intent)
        }

}}