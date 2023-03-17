package com.example.quickgpt.ui.dialogs.preferences

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import com.example.quickgpt.R
import com.example.quickgpt.classes.PrefKey
import com.example.quickgpt.classes.Requester
import com.example.quickgpt.classes.models.gptmodels.GptModel
import com.example.quickgpt.classes.prefWriter
import com.example.quickgpt.ui.composables.Composables
import kotlinx.android.synthetic.main.dialogfragment_models.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class ModelsDialogFragment : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialogfragment_models, container, false)
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)

        Requester(context as Activity).getModels { resp ->

            val datalistJson = JSONObject(resp).getJSONArray("data")
            val modelDatalist =
                ((Json.decodeFromString<List<GptModel>>(datalistJson.toString()))
                    .sortedBy {   it.id[0]         }
                    .sortedBy {   it.createdDate   }
                    .sortedBy {   it.is_gpt3       }
                    .sortedBy {   it.is_gpt3_5     }
                    .filter   { ! it.is_filtered   }
                ).reversed()


            Toast.makeText(context, "${modelDatalist.size} models found", Toast.LENGTH_SHORT).show()
            modelDatalist

            //set models UI declaratively:

            //set bg
            gptModelsListComposable.setContent {
                Surface(
                    Modifier
                        .fillMaxSize(),
                    color = (Color(90,80,130).copy(0.2f))
                ) {
                    LazyColumn(
                        Modifier,
                    ) {

                        modelDatalist.forEach { mdl ->
                            item {
                                if(mdl.id == requireContext().prefWriter[PrefKey.MODEL_SELECTION])
                                    Composables.modelCard(mdl, this@ModelsDialogFragment, true)
                                else
                                    Composables.modelCard(mdl, this@ModelsDialogFragment)
                        }}
                    }//end recycler list
                }//end surface
            }














        }

    }


}