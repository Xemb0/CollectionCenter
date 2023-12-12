package com.test.samplecollection.kotlin;



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
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.test.samplecollection.R

open class LoginActivity : AppCompatActivity() {
    private var mBottomSheetBehavior: BottomSheetBehavior<View>? = null
    private var otherLoginIcons: LinearLayout? = null
    private var iconAndName: LinearLayout? = null
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
        iconAndName = findViewById(R.id.Icon_and_Name)
        otherLoginIcons = findViewById(R.id.Other_login_icons)
        val handle = findViewById<ImageView>(R.id.bottomSheetHandle)
        val expandArrow = R.drawable.ic_expand_arrow
        val collapseArrow = R.drawable.ic_collapse_arrow
        val googleButton = findViewById<ImageButton>(R.id.google_icon)
        googleButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, GoogleSignInAccount::class.java)
            startActivity(intent)
        }
        otherLoginIcons?.let { icons ->
            iconAndName?.let { iconsAndName ->
                bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        val handleRotation =
                            if (newState == BottomSheetBehavior.STATE_EXPANDED) 180f else 0f
                        rotateArrow(handle, handleRotation)
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        val translationY = slideOffset * resources.getDimension(R.dimen.slide_translation)
                        icons.translationY = -translationY
                        iconsAndName.translationY = -translationY
                    }
                })
            }
        }
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