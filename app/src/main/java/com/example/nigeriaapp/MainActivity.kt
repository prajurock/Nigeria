package com.example.nigeriaapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    //edittext and spinner
    private var editTextArtistName: EditText? = null
    private var editTextphoneName: EditText? = null
    private var Problem: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting it from xml
        editTextArtistName = findViewById(R.id.editTextArtistName) as EditText
        editTextphoneName=findViewById(R.id.editTextphoneName) as EditText
        Problem=findViewById(R.id.Problem) as EditText

        //adding a click listener to button
        findViewById<Button>(R.id.buttonAddArtist).setOnClickListener { addArtist() }

        //in the second button click
        //opening the activity to display all the artist
        //it will give error as we dont have this activity so remove this part for now to run the app
    }

    //adding a new record to database
    private fun addArtist() {
        //getting the record values
        val name = editTextArtistName?.text.toString()
        val mobile=editTextphoneName?.text.toString()
        val problem=Problem?.text.toString()
        Log.d("hhh",name);

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_ADD_PAITENT,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("YourName", name)
                params.put("AadharNo", mobile)
                params.put("MobileNo", problem)
                return params
            }
        }
        Toast.makeText(applicationContext, "Complain Registered", Toast.LENGTH_LONG).show()
        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}