package com.example.quickgpt.classes

import android.content.Context
import android.content.SharedPreferences

enum class PrefKey(val realkey :String) {
    API_KEY("API_KEY"),
    MODEL_SELECTION("MODEL_SELECTION"),
    //MODEL_LIST("MODEL_LIST"),
    CHAT_CONTEXT("CHAT_CONTEXT");

    override fun toString() = realkey

}

class PrefWriter(
    context :Context
) {

    val prefsStore: SharedPreferences by lazy {
        context.getSharedPreferences("myAppPrefKey", Context.MODE_PRIVATE)
    }

    // operator to access prefs  -  eg: PrefWriter['prefkey']

    operator fun get(prefKey: PrefKey) :String? =
        getPref(prefKey)
    operator fun set(prefKey: PrefKey, nVal:String) =
        setPref(prefKey, nVal)

    private fun getPref(prefKey :PrefKey) : String? =
        prefsStore.getString(prefKey.toString(), null)
    private fun setPref(prefKey: PrefKey, prefVal:String) =
        prefsStore.edit().putString(prefKey.toString(), prefVal).commit()





}