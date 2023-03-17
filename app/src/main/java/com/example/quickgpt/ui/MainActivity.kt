package com.example.quickgpt.ui




import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.quickgpt.R
import com.example.quickgpt.classes.*
import com.example.quickgpt.ui.dialogs.preferences.ModelsDialogFragment
import com.example.quickgpt.ui.dialogs.preferences.PrefsDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


const val REQUEST_CODE_SPEECH_INPUT = 11;



class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //force dark mode
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)


        chipContext.isChecked =
            ! prefWriter[PrefKey.CHAT_CONTEXT].isNullOrEmpty()



        chipContext.setOnClickListener { v ->
            if((v as Chip).isChecked){
                // (the chip got checked)

                //show dialog
                this.contextDialogTextPrompt(
                    "Provide Context",
                    "Here we can provide 'context' for the conversation - data or instructions that the model will remember.").apply {

                }

            }else { //not checked
                Unit // (do nothing; chip just gets unchecked)
            }
        }

        btnSend.setOnClickListener { v ->
            val inputStr = txtEntry.text.toString()
            txtEntry.setText("");

            if(inputStr.trim().isNotEmpty())
                txtConversation.appendTitleAndContent(
                    "You:",
                    inputStr
                )
            else
                txtConversation.append("\n\n")


            val convo = CompletionConversation(this)
            convo.apply {
                send(inputStr) { resp ->

                    val choice0 = JSONObject(resp).getJSONArray("choices").get(0).toString()
                    val choice0text = JSONObject(choice0).get("text").toString()


                    context.txtConversation.appendTitleAndContent(
                        "Chat:",
                        choice0text
                    )
                }   
            }
        }//end click


        chipModels.setOnClickListener { v ->
            ModelsDialogFragment().show(supportFragmentManager, "ModelsDialogFragment")
        }


        chipPreferences.setOnClickListener { v ->
            PrefsDialogFragment().show(supportFragmentManager, "PrefsDialogFragment");
        }

    }

/*
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                /*
                tv_Speech_to_text.setText(
                    Objects.requireNonNull(result).get(0)
                )
                 */

            }
        }
    }

 */


}