package com.example.quickgpt.ui.composables

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import com.example.quickgpt.R
import com.example.quickgpt.classes.PrefKey
import com.example.quickgpt.classes.formattedString
import com.example.quickgpt.classes.models.gptmodels.GptModel
import com.example.quickgpt.classes.prefWriter


object Composables {





    @Composable
    fun modelCard(model: GptModel, parentFragRef :DialogFragment, active:Boolean = false) =
        Card(
            Modifier
                .padding(
                    if (active) PaddingValues(4.5.dp,2.5.dp)
                    else        PaddingValues(8.dp,3.3.dp)  )
                .border(
                    if(active)  BorderStroke(4.5.dp, Color(135,143,222).copy(0.92f))
                    else        BorderStroke(0.8.dp, Color(44,44,8).copy(0.99f))
                )
                .shadow(
                    if(active)
                        3.dp
                    else
                        1.5.dp
                )

                .fillMaxWidth()
                .clickable {

                    val prefWriter = parentFragRef.requireContext().prefWriter
                    prefWriter[PrefKey.MODEL_SELECTION] = model.id
                    Toast.makeText(parentFragRef.context, "Model set to:\n ${prefWriter[PrefKey.MODEL_SELECTION].toString()}", Toast.LENGTH_SHORT).show()
                    parentFragRef.dismiss()
                }

            ,
            elevation = 1.5.dp,
            backgroundColor =
                let {

                    //base color
                    var col = Color(50,43,40).copy(0.2f)



                    if( model.is_gpt3)
                        col = col
                            .copy(alpha = col.alpha+.085f)
                            .copy(green = col.green+.23f)
                            .copy(red = col.red+.02f)
                    if( model.is_gpt3_5)
                        col = col
                            .copy(alpha = col.alpha+.2f)
                            .copy(green = col.green+.45f)
                            .copy(red = col.red+.065f)
                    if(active)
                        col = col
                            .copy(alpha = col.alpha+0.21f)
                            .copy(green = col.green + .04f)
                            .copy(red = col.red + .065f)
                            .copy(blue = col.blue + .555f)

                    //return:
                    col
                }
    ) {




        @Composable
        fun lhs_modelCard() =
            Column(
                Modifier

                    .padding(2.dp, 4.dp)
                    .fillMaxWidth(0.23f)


            ) {

                Image(
                    painterResource(R.drawable.ic_launcher_background),
                    "",
                    Modifier
                        .padding(5.dp, 5.dp)
                        .scale(0.8f)

                )
            }


        @Composable
        fun rhs_modelCard() =
            Column(
                Modifier
                    .padding(2.dp, 9.dp, 2.dp, 4.dp)
                    .fillMaxWidth(0.77f)
            ) {

                Column {
                    Text(
                        "${model.id}",
                        modifier = Modifier.padding(0.dp, 2.dp),
                        color = Color(233, 233, 255),
                        fontSize = 17.sp
                    )


                    Text("Created: ${model.createdDate.formattedString}",
                        modifier = Modifier.padding(0.dp,3.dp,0.dp,0.dp),
                        color = Color(233, 233, 255),
                        fontSize = 15.sp
                    )

                }

            }


        //each column
        Row {
            lhs_modelCard()
            rhs_modelCard()
        }




    }

}
