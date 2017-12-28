package com.example.yinon.mycloset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String userId;
    Vector<User> users;
    Vector<User> admins;
    EditText edittextusername;
    EditText edittextpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View backgroundImage = findViewById(R.id.backgroung);
        Drawable background = backgroundImage.getBackground();
        background.setAlpha(30);
        findViewById(R.id.username).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText username = (EditText)v;
                if (hasFocus) {
                    if(username.getText().toString().equals("Username")) {
                        username.setText("");
                    }
                } else { // no focus
                    if(username.getText().toString().equals("")) {
                        username.setText("Username");
                    }
                }
            }
        });
        findViewById(R.id.password).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText username = (EditText)v;
                if (hasFocus) {
                    if(username.getText().toString().equals("Password")) {
                        username.setText("");
                        username.setTransformationMethod(new PasswordTransformationMethod());
                    }
                } else { // no focus
                    if(username.getText().toString().equals("")) {
                        username.setText("Password");
                        username.setTransformationMethod(null);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUsers();
    }

    public void openRegistration(View view) {
        Intent intent = new Intent(MainActivity.this, Registration.class);
        startActivity(intent);
    }

    public void openReadMe(View view) {
        Intent intent = new Intent(MainActivity.this, README.class);
        startActivity(intent);
    }

    public void loginClicked(View view) {
        edittextusername = (EditText) findViewById(R.id.username);
        edittextpassword = (EditText) findViewById(R.id.password);
        String username = edittextusername.getText().toString();
        String password = edittextpassword.getText().toString();

        if (isUserNameAdmin(username, password)) {
            Intent intent = new Intent(MainActivity.this, Admin.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        else if (isCorrectUsername(username, password)) {
            Intent intent = new Intent(MainActivity.this, Menu.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
        else Toast.makeText(MainActivity.this, "Username or password incorrect", Toast.LENGTH_LONG).show();
    }

    private boolean isUserNameAdmin(String username, String password) {
        for (User u : admins) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password))
                return true;
        }
        return false;
    }

    private boolean isCorrectUsername(String username, String password) {
        for (User i : users) {
            if (i.getUsername().equals(username) && i.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void openGoogleMaps(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void setUsers() {
        users = new Vector<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        database.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot d: dataSnapshot.getChildren()) {
                String pass = "";
                for (DataSnapshot attr : d.getChildren()) {
                    if (attr.getKey().toString().equals("password")) {
                        pass = attr.getValue().toString();
                        break;
                    }
                }
                User u = new User(d.getKey(), pass);
                users.add(u);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_LONG).show();
        }
        });
    }

    public void setAdmins() {
        admins = new Vector<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Managers");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    String pass = "";
                    for (DataSnapshot attr : d.getChildren()) {
                        if (attr.getKey().toString().equals("password")) {
                            pass = attr.getValue().toString();
                            break;
                        }
                    }
                    User u = new User(d.getKey(), pass);
                    admins.add(u);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_LONG).show();
            }
        });
    }

}
