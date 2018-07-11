package codepath.com.instagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.btLogin) Button btLogin;
    @BindView(R.id.btSignUp) Button btSignUp;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        context = getApplicationContext();

        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) startActivity(new Intent(this, HomeActivity.class));

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the inputted info from the text fields
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();

                // create a new user with the above attributes
                signUp(username, password, email);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the inputted info from the text fields
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // log in with the specified credentials
                login(username, password);
            }
        });
    }

    private void signUp(String username, String password, String email) {
        // create a new user
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // sign the user up in the background
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "signup successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
