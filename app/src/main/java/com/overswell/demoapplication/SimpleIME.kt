package com.overswell.demoapplication

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.view.View
import android.media.AudioManager.*
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.keyboard.view.*


/**
 * Created by ph on 26/01/18.
 */

class SimpleIME : InputMethodService() {

    private val audioManager by lazy {
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private fun playClick(keyCode: Int) {
        audioManager.playSoundEffect(
                when (keyCode) {
                    32 ->
                        FX_KEYPRESS_SPACEBAR
                    Keyboard.KEYCODE_DONE, 10 ->
                        FX_KEYPRESS_RETURN
                    Keyboard.KEYCODE_DELETE ->
                        FX_KEYPRESS_DELETE
                    else ->
                        FX_KEYPRESS_STANDARD
                }
        )
    }

    override fun onCreateInputView(): View {
        val keyboardView = layoutInflater.inflate(R.layout.keyboard, null)
        keyboardView.button1.setOnClickListener {
            playClick(32)
            Toast.makeText(applicationContext, "click", Toast.LENGTH_SHORT).show()
        }

        val keyboard = Keyboard(this, R.xml.qwerty)
//        keyboardView.keyboard = keyboard

//        keyboardView.setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
//            override fun swipeRight() {
//            }
//            override fun onPress(primaryCode: Int) {
//                playClick(primaryCode)
//                Toast.makeText(applicationContext, "$primaryCode", Toast.LENGTH_SHORT).show()
//            }
//            override fun onRelease(primaryCode: Int) {
//            }
//            override fun swipeLeft() {
//            }
//            override fun swipeUp() {
//            }
//            override fun swipeDown() {
//            }
//            override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
//            }
//            override fun onText(text: CharSequence?) {
//            }
//        })

        return keyboardView
    }
}
