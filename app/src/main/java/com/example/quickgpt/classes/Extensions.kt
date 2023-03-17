package com.example.quickgpt.classes

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*


fun String.toItalicSpan() = SpannableString(this).apply {
    setSpan(StyleSpan(Typeface.ITALIC), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}


fun String.toH2Span() = SpannableString(" $this\t\t").apply {
    setSpan(
        StyleSpan(Typeface.BOLD), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    setSpan(
        UnderlineSpan(), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

fun EditText.appendTitleAndContent(title:String, content:String) = this.apply {
    append("\n")
    append((" $title    ").toH2Span())
    append("\n")
    append(content.toItalicSpan())
    append("\n")
}

val Context.prefWriter :PrefWriter get() = PrefWriter(this)


fun Context.contextDialogTextPrompt(title: String, prompt: String) :String {

    val inputView = EditText(this)
    inputView.minLines = 4;
    inputView.scaleX = 0.88f;
    inputView.setText( prefWriter[PrefKey.CHAT_CONTEXT]  ?:  "" )


    var strout :String? = null

    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(prompt)
        .setView(inputView)
        .setPositiveButton("Ok") { dialog, whichButton ->

            strout = inputView.text.toString()

            //save dialog result to prefs and alert user:
            prefWriter[PrefKey.CHAT_CONTEXT] = inputView.text.toString()
            Toast.makeText(this, "Context set:\n ${prefWriter[PrefKey.CHAT_CONTEXT]}", Toast.LENGTH_SHORT).show()
            dialog.dismiss()

        }
        .setNegativeButton("Cancel") { dialog, whichButton ->
            dialog.dismiss()
        }
        .show()

return ""
}



val Date.formattedString :String
    get() = SimpleDateFormat("MMM d, yyyy").format(this)