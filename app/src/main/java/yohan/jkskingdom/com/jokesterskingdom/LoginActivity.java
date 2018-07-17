package yohan.jkskingdom.com.jokesterskingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail, inputPassword;
    private Button btnLogin;
    private TextView goToRegister;
    private FirebaseAuth auth;
    CheckInternet checkInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CHECK IF INTERNET WITH ASYNCTASK
        checkInternetMethod();


        auth = FirebaseAuth.getInstance();
        inputEmail = (TextInputLayout) findViewById(R.id.textInputLayout2);
        inputPassword = (TextInputLayout) findViewById(R.id.textInputLayout3);
        btnLogin = (Button) findViewById(R.id.button);
        goToRegister = (TextView) findViewById(R.id.textView2);

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //CHECK IF USER ALREADY LOGGED IN  AND TAKE HIM DIRECTLY TO THE JOKES FEED IF ALREADY LOGGED IN
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            Intent intent = new Intent(LoginActivity.this, JokesFeed.class);
            startActivity(intent);
            this.finish();
        }


        //LOGIN THE USER WITH FIREBASE
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getEditText().getText().toString().trim();
                final String password = inputPassword.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), R.string.forget_mail, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), R.string.forget_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.password_short));
                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.fail_authentication, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    StyleableToast.makeText(getBaseContext(), getString(R.string.welcome_toast_message), Toast.LENGTH_SHORT, R.style.mytoast).show();
                                    Intent intent = new Intent(LoginActivity.this, JokesFeed.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }

    public void checkInternetMethod() {
        checkInternet = new CheckInternet(this);
        checkInternet.execute();
    }

}
