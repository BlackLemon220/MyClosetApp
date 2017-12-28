package com.example.yinon.mycloset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Base64;

public class Registration extends AppCompatActivity {
    String userId;
    DatabaseReference mDatabase;
    Vector<String> users;
    /*regular expression*/
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private int IMAGE_FROM_GALLERY = 1;
    private int TAKE_PICTURE_CAMERA = 2;
    Bitmap bitmap = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);

            View backgroundImage = findViewById(R.id.backgroung);
            Drawable background = backgroundImage.getBackground();
            background.setAlpha(30);
            findViewById(R.id.registerUsername).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Username")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("Username");
                    }
                }
            });
            setUsers();

            findViewById(R.id.firstPassword).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Password")){
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

            findViewById(R.id.repassword).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Re - Password")) {
                            username.setText("");
                            username.setTransformationMethod(new PasswordTransformationMethod());
                        }
                    } else { // no focus
                        if(username.getText().toString().equals("")) {
                            username.setText("Re - Password");
                            username.setTransformationMethod(null);
                        }
                    }
                }
            });

            findViewById(R.id.email).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Email")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("Email");
                    }
                }
            });

            findViewById(R.id.registerFirstName).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("First Name")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("First Name");
                    }
                }
            });

            findViewById(R.id.registerLastName).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Last Name")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("Last Name");
                    }
                }
            });

            findViewById(R.id.registerCity).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("City")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("City");
                    }
                }
            });

            findViewById(R.id.registerStreetAdress).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText username = (EditText)v;
                    if (hasFocus) {
                        if(username.getText().toString().equals("Street Address")) username.setText("");
                    } else { // no focus
                        if(username.getText().toString().equals("")) username.setText("Street Address");
                    }
                }
            });
        }

    public void registerClicked(View view) {
        EditText editusername = (EditText)findViewById(R.id.registerUsername);
        EditText editpassword = (EditText)findViewById(R.id.firstPassword);
        EditText editrepassword = (EditText)findViewById(R.id.repassword);
        EditText editemail = (EditText)findViewById(R.id.email);
        EditText edittextfirstname = (EditText)findViewById(R.id.registerFirstName);
        EditText edittextlastname = (EditText)findViewById(R.id.registerLastName);
        EditText edittextcity = (EditText)findViewById(R.id.registerCity);
        EditText edittextstreet = (EditText)findViewById(R.id.registerStreetAdress);

        String username = editusername.getText().toString();
        String password = editpassword.getText().toString();
        String repassword = editrepassword.getText().toString();
        String email = editemail.getText().toString();
        String firstname = edittextfirstname.getText().toString().equals("First Name") ? "" : edittextfirstname.getText().toString();
        String lastname = edittextlastname.getText().toString();
        String city = edittextcity.getText().toString();
        String street = edittextstreet.getText().toString();

        String image = "";
        if (bitmap != null) {
            image = BitMapToString(bitmap);
        }

        Person p = new Person(username,password,email,firstname,lastname,city,street,image);

        if(username.equals("Username")) {
            Toast.makeText(Registration.this, "Must input username", Toast.LENGTH_LONG).show();
        }
        else if(password.equals("Password")){
            Toast.makeText(Registration.this, "Must input password", Toast.LENGTH_LONG).show();
        }
        else if(repassword.equals("Re - Password")){
            Toast.makeText(Registration.this, "Must input re - password", Toast.LENGTH_LONG).show();
        }
        else if(email.equals("Email")){
            Toast.makeText(Registration.this, "Must input email", Toast.LENGTH_LONG).show();
        }
        else if(firstname.equals("First Name")){
            Toast.makeText(Registration.this, "Must input first name", Toast.LENGTH_LONG).show();
        }
        else if(lastname.equals("Last Name")){
            Toast.makeText(Registration.this, "Must input last name", Toast.LENGTH_LONG).show();
        }
        else if(city.equals("City")){
            Toast.makeText(Registration.this, "Must input city", Toast.LENGTH_LONG).show();
        }
        else if(street.equals("Street Address")){
            Toast.makeText(Registration.this, "Must input street", Toast.LENGTH_LONG).show();
        }
        else if(users.contains(username)) {
            Toast.makeText(Registration.this, "Username already exists", Toast.LENGTH_LONG).show();
        }
        else if(username.length() > 12 || username.length() < 3){
            Toast.makeText(Registration.this, "Invalid username length (4 - 12)", Toast.LENGTH_LONG).show();
        }
        else if(password.length() > 12 || password.length() < 3){
            Toast.makeText(Registration.this, "Invalid password length (4 - 12)", Toast.LENGTH_LONG).show();
        }
        else if(!password.equals(repassword)){
            Toast.makeText(Registration.this, "Passwords do not match", Toast.LENGTH_LONG).show();
        }
        /*
        else if(!isValidEmail(email)){
            Toast.makeText(Registration.this, "Invalid email", Toast.LENGTH_LONG).show();
        }
        */
        else if(firstname.length() < 1 || firstname.length() > 20){
            Toast.makeText(Registration.this, "Invalid First name length (1 - 20)", Toast.LENGTH_LONG).show();
        }
        else if(lastname.length() < 1 || lastname.length() > 20){
            Toast.makeText(Registration.this, "Invalid Last name length (1 - 20)", Toast.LENGTH_LONG).show();
        }
        else if(city.length() < 1 || city.length() > 20){
            Toast.makeText(Registration.this, "Invalid city length (1 - 20)", Toast.LENGTH_LONG).show();
        }
        else if(street.length() < 1 || street.length() > 20){
            Toast.makeText(Registration.this, "Invalid street length (1 - 20)", Toast.LENGTH_LONG).show();
        }
        else if(street.length() < 1 || street.length() > 20){
            Toast.makeText(Registration.this, "Invalid street length (1 - 20)", Toast.LENGTH_LONG).show();
        }
        else if (bitmap == null) {
            Toast.makeText(Registration.this, "Must input image", Toast.LENGTH_LONG).show();
        }
        else{
            register(p);
            Toast.makeText(Registration.this, "Registration completed", Toast.LENGTH_LONG).show();
            finish();
        }
        view.setEnabled(true);
    }

    private void register(Person p) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = p.getUsername();
        mDatabase.child("users").child(userId).setValue(p);
    }


    private boolean isValidEmail(String email) {
        if (email == null)
            return false;
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public void picGallery(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_FROM_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Toast.makeText(Registration.this, "Image added successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == TAKE_PICTURE_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            Toast.makeText(Registration.this, "Image added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void takePic(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_PICTURE_CAMERA);
        }
    }


    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public void setUsers() {
        users = new Vector<>();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    users.add(d.getKey().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Registration.this, "An error occured", Toast.LENGTH_LONG).show();

            }
        });
    }
}