package com.test.samplecollection.kotlin;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.test.samplecollection.MainActivity;
import com.test.samplecollection.R;
import com.test.samplecollection.UserFragment;

public class LoginActivity extends AppCompatActivity {
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private LinearLayout iconAndName;
    private LinearLayout otherLoginIcons;

    private static final int RC_SIGN_IN = 100;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    SignInButton googlebutton;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    handleSignInResult(data);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final int DRAWER_PEEK_HEIGHT = 300;
        View mBottomSheet = findViewById(R.id.login_bottomsheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(false);
        mBottomSheetBehavior.setPeekHeight(DRAWER_PEEK_HEIGHT);

        iconAndName = findViewById(R.id.Icon_and_Name);
        otherLoginIcons = findViewById(R.id.Other_login_icons);

        View bottomSheet = findViewById(R.id.login_bottomsheet);
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        ImageView handle = findViewById(R.id.bottomSheetHandle);
        final int expandArrow = R.drawable.ic_expand_arrow;
        final int collapseArrow = R.drawable.ic_collapse_arrow;

        ImageButton googleButton = findViewById(R.id.google_icon);
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                signInLauncher.launch(signInIntent);
                Toast.makeText(LoginActivity.this, "sign in clicked", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                float handleRotation = newState == BottomSheetBehavior.STATE_EXPANDED ? 180f : 0f;
                rotateArrow(handle, handleRotation);
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                float translationY = slideOffset * getResources().getDimension(R.dimen.slide_translation);
                otherLoginIcons.setTranslationY(-translationY);
                iconAndName.setTranslationY(-translationY);
            }
        });

        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newState = bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED ?
                        BottomSheetBehavior.STATE_EXPANDED : BottomSheetBehavior.STATE_COLLAPSED;
                bottomSheetBehavior.setState(newState);
            }
        });

        googleSignInClient = GoogleSignIn.getClient(this, getGoogleSignInOptions());
        firebaseAuth = FirebaseAuth.getInstance();
        googlebutton = findViewById(R.id.bt_sign_in);

        checkCurrentUser();
    }

    private GoogleSignInOptions getGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    private void checkCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            startProfileActivity();
        }
    }

    private void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
        if (signInAccountTask.isSuccessful()) {
            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                    firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                displayToast("Firebase authentication successful");
                            } else {
                                displayToast("Authentication Failed: " + task.getException().getMessage());
                            }
                        }
                    });
                }
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void startProfileActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void rotateArrow(View view, float degrees) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, View.ROTATION, degrees);
        rotation.setDuration(300); // Set the duration of the animation (in milliseconds)
        rotation.start(); // Start the animation
    }
}
