package com.test.samplecollection.kotlin

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.test.samplecollection.R

open class Login : AppCompatActivity() {
    private var mBottomSheetBehavior: BottomSheetBehavior<View>? = null
    private val iconAndName: LinearLayout by lazy {
        findViewById(R.id.Icon_and_Name)
    }
    private val otherLoginIcons: LinearLayout by lazy {
        findViewById(R.id.Other_login_icons)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val DRAWER_PEEK_HEIGHT = 300
        val mBottomSheet = findViewById<View>(R.id.login_bottomsheet)
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet)
        mBottomSheetBehavior!!.isHideable = false
        mBottomSheetBehavior!!.peekHeight = DRAWER_PEEK_HEIGHT
        val bottomSheet = findViewById<View>(R.id.login_bottomsheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val handle = findViewById<ImageView>(R.id.bottomSheetHandle)
        val expandArrow = R.drawable.ic_expand_arrow
        val collapseArrow = R.drawable.ic_collapse_arrow
        val googleButton = findViewById<ImageButton>(R.id.google_icon)
        googleButton.setOnClickListener {
            val intent = Intent(this@Login, GoogleSignInAccount::class.java)
            startActivity(intent)
        }
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val handleRotation =
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) 180f else 0f
                rotateArrow(handle, handleRotation)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val translationY = slideOffset * resources.getDimension(R.dimen.slide_translation)
                otherLoginIcons.translationY = -translationY
                iconAndName.translationY = -translationY
            }
        })
        handle.setOnClickListener {
            val newState =
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.state = newState
        }
    }

    private fun rotateArrow(view: View, degrees: Float) {
        val rotation = ObjectAnimator.ofFloat(view, View.ROTATION, degrees)
        rotation.duration = 300 // Set the duration of the animation (in milliseconds)
        rotation.start() // Start the animation
    }
}
