package com.karim.vacationhere.UI;

import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.karim.vacationhere.R;
public class MainActivity extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Simple hardcoded login credentials
        final String correctUsername = "wgu-login";
        final String correctPassword = "test123";

        // Set up the login check (you could use EditText for username/password here)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login");

        // Input fields for username and password
        final EditText usernameInput = new EditText(this);
        final EditText passwordInput = new EditText(this);
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        usernameInput.setHint("wgu-login");
        passwordInput.setHint("test123");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(usernameInput);
        layout.addView(passwordInput);

        builder.setView(layout);

        // OK button logic
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String enteredUsername = usernameInput.getText().toString();
                String enteredPassword = passwordInput.getText().toString();

                // Check credentials
                if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
                    // If credentials are correct, proceed to next activity
                    Intent intent = new Intent(MainActivity.this, VacationList.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If credentials are incorrect, show an error
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        // Show the login dialog
        builder.show();
    }
}

