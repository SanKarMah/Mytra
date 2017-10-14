package com.keerthika.mytra;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;
import com.keerthika.mytra.DataModel.DataModel;
import com.keerthika.mytra.database.DBManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DBManager dbManager;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private String hairdresserName;
    String name ,phone,email,comments,rating;
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);





        dbManager = new DBManager(this);
        dbManager.open();
        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.input_layout_name);
        final TextInputLayout phoneWrapper = (TextInputLayout) findViewById(R.id.input_layout_phone);
        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.input_layout_email);
        final TextInputLayout commentsWrapper = (TextInputLayout) findViewById(R.id.input_layout_comments);
        final SmileRating ratingBar = (SmileRating) findViewById(R.id.rb_rating);
        final TextView btn_submit = (TextView) findViewById(R.id.btn_submit);
        final Spinner hairdresserSpinner = (Spinner)findViewById(R.id.hairdresser_spinner);
        ArrayList<String> hairdresserNames = new ArrayList<String>();
        hairdresserNames.add("Select a hairdresser");
        hairdresserNames.add("abc");
        hairdresserNames.add("def");
        hairdresserNames.add("hij");
        hairdresserNames.add("klm");
        hairdresserNames.add("nop");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,hairdresserNames);
        hairdresserSpinner.setAdapter(stringArrayAdapter);

        hairdresserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                hairdresserName = adapterView.getItemAtPosition(pos).toString();
                hairdresserSpinner.setBackgroundColor(Color.parseColor("#3a9fe5"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DataModel dataModel = new DataModel();

//Name
                if(!usernameWrapper.getEditText().getText().toString().equals(""))
                {
                 usernameWrapper.setError(null);
                 name = usernameWrapper.getEditText().getText().toString();
                    dataModel.setNAME(name);
                    Log.v("name ","name: "+name);
                }else
                    {
                        usernameWrapper.setError("Please enter the name");
                    }
//Phone number
                if(!phoneWrapper.getEditText().getText().toString().equals("") && phoneWrapper.getEditText().length() >= 10)
                {
                        phoneWrapper.setError(null);
                        phone = phoneWrapper.getEditText().getText().toString();
                        dataModel.setPHONENUMBER(phone);
                        Log.v("phone ","phone: "+phone);

                }else
                {
                    phoneWrapper.setError("Please enter the mobile");
                }
// email
                if(!emailWrapper.getEditText().getText().toString().equals(""))
                {
                    emailWrapper.setError(null);
                    email = emailWrapper.getEditText().getText().toString();
                    if(!validateEmail(email))
                    {
                        emailWrapper.setError("Please enter the email");
                    }else
                        {
                            email = emailWrapper.getEditText().getText().toString();
                            dataModel.setEMAIL(email);
                            Log.v("email ","email: "+email);
                        }
                }
//comments
                if(!commentsWrapper.getEditText().getText().toString().equals(""))
                {
                    comments = commentsWrapper.getEditText().getText().toString();
                    Log.v("comments ","comments: "+comments);
                    dataModel.setCOMMENTS(comments);

                }
//Rating
                if(ratingBar.getRating() > 0.0)
                {
                    rating = String.valueOf(ratingBar.getRating());
                    Log.v("rating ","rating: "+rating);
                    dataModel.setRATING(rating);
                }else
                    {
                        Toast.makeText(MainActivity.this, "Rating is missing ", Toast.LENGTH_SHORT).show();
                    }

//hair dresser name
                 if(!hairdresserName.equals(""))
                 {


                 }   else
                     {
                         Toast.makeText(MainActivity.this, "Select a hair dresser ", Toast.LENGTH_SHORT).show();
                     }

               if(name != null  && !name.equals(""))
               {
                   if(phone != null  && !phone.equals(""))
                   {
                       if(rating != null  && !rating.equals(""))
                       {
                           if(hairdresserName != null  && !hairdresserName.equals("Select a hairdresser"))
                           {

                               usernameWrapper.setError(null);
                               name = usernameWrapper.getEditText().getText().toString();
                               dataModel.setNAME(name);
                               Log.v("name ","name: "+name);

                               phoneWrapper.setError(null);
                               phone = phoneWrapper.getEditText().getText().toString();
                               dataModel.setPHONENUMBER(phone);
                               Log.v("phone ","phone: "+phone);

                               if(!emailWrapper.getEditText().getText().toString().equals(""))
                               {
                                   emailWrapper.setError(null);
                                   email = emailWrapper.getEditText().getText().toString();
                                   if(!validateEmail(email))
                                   {
                                       dataModel.setEMAIL("");
                                       emailWrapper.setError("Please enter the email");
                                   }else
                                   {
                                       emailWrapper.setError(null);
                                       email = emailWrapper.getEditText().getText().toString();
                                       dataModel.setEMAIL(email);
                                       Log.v("email ","email: "+email);
                                   }
                               }

                               comments = commentsWrapper.getEditText().getText().toString();
                               Log.v("comments ","comments: "+comments);
                               dataModel.setCOMMENTS(comments);

                               rating = String.valueOf(ratingBar.getRating());
                               Log.v("rating ","rating: "+rating);
                               dataModel.setRATING(rating);

                               dataModel.setHAIRDRESSER(hairdresserName);
                               Calendar calendar = Calendar.getInstance();
                               SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                               String strDate = mdformat.format(calendar.getTime());
                               SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                               String strTime = sdf.format(calendar.getTime());
                               dataModel.setDATE_TIME(strDate);
                               dataModel.setTIME(strTime);
                               dbManager.insert(dataModel);
                               phoneWrapper.getEditText().setText("");
                               usernameWrapper.getEditText().setText("");
                               //ratingBar.setRating(0.0f);
                               emailWrapper.getEditText().setText("");
                               commentsWrapper.getEditText().setText("");
                               hairdresserSpinner.setSelection(0);
                               name = "";
                               phone= "";
                               rating = "";
                               hairdresserName = "Select a hairdresser";

                               alertBox();
                              // Toast.makeText(getApplicationContext(),"Thanks for your Feedback",Toast.LENGTH_SHORT).show();

                           }else
                               {
                                   Toast.makeText(getApplicationContext(),"Please select the Hair Dresser",Toast.LENGTH_SHORT).show();
                               }
                       }


                   }else
                   {
                       phoneWrapper.setError("Please enter the mobile");
                   }
               }
               else
               {
                   usernameWrapper.setError("Please enter the name");
               }

            }
        });



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_menu:
               // Toast.makeText(this, "You have selected Sync option", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view_menu:
                startActivity(new Intent(getApplicationContext(),AllDataList.class));
                // Toast.makeText(this, "You have selected List option", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    void alertBox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Feed Back")
                .setMessage("Thank you for your feed back")
                .setCancelable(false)
                .setNegativeButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_camera:
                fragment = new f1();
                break;
            case R.id.nav_gallery:
                fragment = new f1();
                break;
            case R.id.nav_slideshow:
                fragment = new f1();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.f1, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(MainActivity.this,"Can",Toast.LENGTH_SHORT).show();
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }
}
