package com.example.quickgpt.classes

import android.app.Activity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*


interface Chats{
    fun send(strInput: String, resultListener : (resp:String)->Unit )
}

abstract class Conversation(
    val context : Activity, //android context
    val conversationContext :String? = null,
    val model: String = PrefWriter(context)[PrefKey.MODEL_SELECTION] ?: "ada"
)  :Chats {

    private var _contextEnabled :Boolean = false;
    val contextEnabled :Boolean
        get(){
            if(conversationContext.isNullOrEmpty())
                return false;
            if(conversationContext.trim().replace("Context:", "").isEmpty())
                return false;
            if( ! (context.chipContext as Chip).isChecked)
                return false;

            return true;
        }
}


class CompletionConversation(
    context: Activity,
    conversationContext: String? = null,
    model: String = PrefWriter(context)[PrefKey.MODEL_SELECTION] ?: "ada"
)  :Conversation(context, conversationContext, model) {

    var  promptContents     : String? = null
    var  completionContents : String? = null


    override fun send(strInput: String, resultListener :(resp:String)->Unit ) {

        if(promptContents == null)

        //TODO("dependency?")
        Requester(context).getCompletion(this, model) {

            // add stuff to the callback stack
            completionContents = it.toString()

            // (carry callback through the stack)
            resultListener(it)

        }

        promptContents = strInput
    }
}





class Chat


