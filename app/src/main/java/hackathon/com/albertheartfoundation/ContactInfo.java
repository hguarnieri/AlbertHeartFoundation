package hackathon.com.albertheartfoundation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.printservice.PrintService;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintServiceProvider;

import java.math.BigDecimal;
import java.util.Currency;


public class ContactInfo extends ActionBarActivity {

    ListView listView;
    Button btnSendContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        listView = (ListView) findViewById(R.id.list_item_contact);
        final String[] values = getResources().getStringArray(R.array.contact_hours);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_arraylist, values));

        btnSendContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Done");
                alertDialog.setMessage("Message sent!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Obtain the transaction result from the returned data.
        TransactionResult result = TransactionResult.fromIntent(data);
        // Use a toast to show the transaction result.
        Toast.makeText(this, "Transaction result: " + result.getTransactionStatus(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Print.class);
        startActivity(i);
        finish();
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
}
