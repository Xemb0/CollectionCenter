package com.test.samplecollection;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class Login extends AppCompatActivity {

    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private LinearLayout otherLoginIcons;
    private LinearLayout iconAndName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final int DRAWER_PEEK_HEIGHT = 300;

        View mBottomSheet = findViewById(R.id.login_bottomsheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);

        View bottomSheet = findViewById(R.id.login_bottomsheet);
        final BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        iconAndName = findViewById(R.id.Icon_and_Name);
        otherLoginIcons = findViewById(R.id.Other_login_icons);
        ImageView handle = findViewById(R.id.bottomSheetHandle);
        final int expandArrow = R.drawable.ic_expand_arrow;
        final int collapseArrow = R.drawable.ic_collapse_arrow;

        ImageButton googleButton = findViewById(R.id.google_icon);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, GoogleSignInAccount.class);
                startActivity(intent);
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                float handleRotation = (newState == BottomSheetBehavior.STATE_EXPANDED) ? 180f : 0f;
                rotateArrow(handle, handleRotation);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                float translationY = slideOffset * getResources().getDimension(R.dimen.slide_translation);
                otherLoginIcons.setTranslationY(-translationY);
                iconAndName.setTranslationY(-translationY);
            }
        });

        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newState = (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) ?
                        BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED;
                bottomSheetBehavior.setState(newState);
            }
        });
    }

    private void rotateArrow(View view, float degrees) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, degrees);
        rotation.setDuration(300); // Set the duration of the animation (in milliseconds)
        rotation.start(); // Start the animation
    }
}
