package com.kimi.shop

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nick_name.*
import kotlinx.android.synthetic.main.content_main.*

class NickNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nick_name)

        done.setOnClickListener {
            setNickname(nickname.text.toString())
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
