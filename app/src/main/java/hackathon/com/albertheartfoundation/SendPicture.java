package hackathon.com.albertheartfoundation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.printservice.PrintService;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintServiceProvider;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;


public class SendPicture extends ActionBarActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        dialog = new ProgressDialog(getApplicationContext());

        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("img");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, os);

        BackgroundTask task = new BackgroundTask() {
            @Override
            protected void onPostExecute(Void result) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                System.out.println("End of post picture");
                //Intent i = new Intent(getApplicationContext(), Donate.class);
                //startActivity(i);
            }
        };
        task.execute(bitmap);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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

    private class BackgroundTask extends AsyncTask<Bitmap, Void, Void> {

        public BackgroundTask() {
        }

        @Override
        protected void onPreExecute() {
            //dialog.setMessage();
            //dialog.show();
        }

        @Override
        protected Void doInBackground(Bitmap... params) {
            try {
                System.out.println("Starting");
                HttpURLConnection httpUrlConnection = null;
                URL url = new URL("http://192.168.43.103:8000/upload");
                httpUrlConnection = (HttpURLConnection) url.openConnection();
                httpUrlConnection.setUseCaches(false);
                httpUrlConnection.setDoOutput(true);

                System.out.println("Open connection");
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
                httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
                httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data;");


                DataOutputStream request = new DataOutputStream(httpUrlConnection.getOutputStream());
                //request.writeBytes("--" + "*****" + "\r\n");
                request.writeBytes("Content-Disposition: form-data; evt_id=\"LockInLove\"; loc=\"UoA\"; amt=\"55\" name=\"" + "picture" + "\";file=\"" + "picture.jpg" + "\"" + "\r\n");
                request.writeBytes("\r\n");

                System.out.println("Image");
                //I want to send only 8 bit black & white bitmaps
                byte[] pixels = new byte[params[0].getWidth() * params[0].getHeight()];
                for (int i = 0; i < params[0].getWidth(); ++i) {
                    for (int j = 0; j < params[0].getHeight(); ++j) {
                        //we're interested only in the MSB of the first byte,
                        //since the other 3 bytes are identical for B&W images
                        pixels[i + j] = (byte) ((params[0].getPixel(i, j) & 0x80) >> 7);
                    }
                }

                request.write(pixels);

                request.writeBytes("\r\n");
                request.writeBytes("--" + "*****" + "--" + "\r\n");

                request.flush();
                request.close();

                try {
                    InputStream responseStream = new BufferedInputStream(httpUrlConnection.getInputStream());

                    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = responseStreamReader.readLine()) != null)
                    {
                        stringBuilder.append(line).append("\n");
                    }
                    responseStreamReader.close();

                    String response = stringBuilder.toString();
                    System.out.println(response);
                    //System.out.println(res);
                    responseStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }


            return null;
        }

    }
}
