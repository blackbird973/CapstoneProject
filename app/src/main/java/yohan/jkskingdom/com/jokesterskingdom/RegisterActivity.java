package yohan.jkskingdom.com.jokesterskingdom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yohan on 29/06/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inputEmail, inputPassword, inputName;
    private Button btnRegister;
    private TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = (TextInputLayout) findViewById(R.id.textInputLayout);
        inputPassword = (TextInputLayout) findViewById(R.id.textInputLayout2);
        inputName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        btnRegister = (Button) findViewById(R.id.button);
        goToLogin = (TextView) findViewById(R.id.textView2);


        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }





}
