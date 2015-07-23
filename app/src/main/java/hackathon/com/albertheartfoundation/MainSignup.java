package hackathon.com.albertheartfoundation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainSignup extends ActionBarActivity {

    Button btnSignin;
    ProgressDialog dialog;

    EditText txtFirstName;
    EditText txtSurname;
    EditText txtEmail;
    EditText txtContactNumber;
    EditText txtPostcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_signup);

        dialog = new ProgressDialog(getApplicationContext());

        txtFirstName = (EditText) findViewById(R.id.txtFirstSurname);
        txtSurname = (EditText) findViewById(R.id.txtSurname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtContactNumber = (EditText) findViewById(R.id.txtContact);
        txtPostcode = (EditText) findViewById(R.id.txtPostcode);

        btnSignin = (Button) findViewById(R.id.btn_signup_button);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtFirstName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The first name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (txtSurname.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The Surname cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (txtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    /*BackgroundTask task = new BackgroundTask() {
                        @Override
                        protected void onPostExecute(Void result) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Intent i = new Intent(getApplicationContext(), Donate.class);
                            startActivity(i);
                        }
                    };
                    task.execute("http://192.168.43.102:8080/heartfoundation/registerUser?firstname="
                            + txtFirstName.getText() + "&lastname=" + txtSurname.getText() +
                            "&email=" + txtEmail.getText() + "&contcatno=" + txtContactNumber.getText()
                            + "&postcode=" + txtPostcode.getText());*/

                    Intent i = new Intent(getApplicationContext(), Donate.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class BackgroundTask extends AsyncTask<String, Void, Void> {

        public BackgroundTask() {
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending your information");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url
                        .openConnection();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

    }
}
