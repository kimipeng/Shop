package com.kimi.shop

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nick_name.*


class NickNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nick_name)

        done.setOnClickListener {
            setNickname(nick.text.toString())

            val uid = FirebaseAuth.getInstance().currentUser!!.uid

            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("nickname")
                .setValue(nick.text.toString())

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
