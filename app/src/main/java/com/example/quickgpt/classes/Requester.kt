package com.example.quickgpt.classes
import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class Requester(
    val context : Activity,
    val apiKey :String? = PrefWriter(context)[PrefKey.API_KEY]!! ,
){


    val queue by lazy { Volley.newRequestQueue(context) }


    fun getCompletion(conv:Conversation, model :String = context.prefWriter[PrefKey.MODEL_SELECTION] ?: "ada", callback:(resp:String)->Unit) {

        val url = "https://api.openai.com/v1/completions"

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            { response ->

                Log.d("requester", "\nHttpResponse Received:\n\t${response}")
                //execute the provided callback function, with the obtained network response
                callback(response);

            },
            { error ->
                Toast.makeText(context, "Error:\n$error", Toast.LENGTH_LONG).show()
                Log.e("requester", "Error:\n$error" )

            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${PrefWriter(context)[PrefKey.API_KEY]}"
                headers["Content-Type"] = "application/json"
                return headers
            }

            override fun getBody(): ByteArray {

                val completionContents = (conv as CompletionConversation).completionContents

                val promptWithContext =
                    if((context.chipContext as Chip).isChecked )
                        "\n## Context/Instructions: ${context.prefWriter[PrefKey.CHAT_CONTEXT]} \n$completionContents \n\n---\n\n"
                    else {
                        completionContents
                    }



                return JSONObject("{\n" +
                        "  \"model\": \"${model}\",\n" +
                        "  \"prompt\": " +
                        "\"" +
                        promptWithContext +
                        "\"," +
                        "\n" +
                        "  \"max_tokens\": 55,\n" +
                        "  \"temperature\": 0.85,\n" +
                        """ "echo": false, """ +
                        "  \"n\": 1\n" +
                        "}").toString().toByteArray();
            }
        }


        queue.add(stringRequest)

    }

    fun getModels(callback:(resp:String)->Unit){
        val url = "https://api.openai.com/v1/models"

        val stringRequest = object : StringRequest(
            Method.GET,
            url,
            { response ->
                Log.d("requester", "\nHttpResponse Received:\n\t${response}")
                //execute the provided callback function, with the obtained network response
                callback(response);

            },
            { error ->
                Toast.makeText(context, "Error:\n$error", Toast.LENGTH_LONG).show()
                Log.e("requester", "Error:\n$error" )

            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer ${PrefWriter(context)[PrefKey.API_KEY]}"
                headers["Content-Type"] = "application/json"
                return headers
            }

        }


        queue.add(stringRequest)
    }

}