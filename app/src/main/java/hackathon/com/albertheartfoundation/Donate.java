package hackathon.com.albertheartfoundation;

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

import com.aevi.helpers.services.AeviServiceConnectionCallback;
import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintServiceProvider;
import com.aevi.printing.model.Alignment;
import com.aevi.printing.model.PrintPayload;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class Donate extends ActionBarActivity {

    ListView listView;
    Button btnSetAmount;
    Button btnCancel;
    Button btnDonate;
    EditText txtNewAmount;
    TextView txtFinalAmount;

    private PrintServiceProvider serviceProvider = new PrintServiceProvider(this);
    private PrintService printService;

    double amount = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        btnSetAmount = (Button) findViewById(R.id.button_set_amount);
        btnCancel = (Button) findViewById(R.id.button_cancel_donation);
        btnDonate = (Button) findViewById(R.id.button_donate);
        listView = (ListView) findViewById(R.id.list_item_values);
        txtNewAmount = (EditText) findViewById(R.id.txt_new_amount);
        txtFinalAmount = (TextView) findViewById(R.id.txt_amount_final);

        final String[] values = getResources().getStringArray(R.array.donation_values);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                amount = Float.parseFloat(values[position].substring(2));
                txtFinalAmount.setText("$ " + amount);
            }

        });

        final String[] list = getResources().getStringArray(R.array.donation_values);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.custom_arraylist, list));

        btnSetAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNewAmount.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "The value is empty", Toast.LENGTH_SHORT).show();
                } else {
                    amount = Float.parseFloat(txtNewAmount.getText().toString());
                    txtFinalAmount.setText("$ " + amount);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentRequest payment = new PaymentRequest(new BigDecimal(amount));
                payment.setCurrency(Currency.getInstance("USD"));

                // Launch the Payment app.
                startActivityForResult(payment.createIntent(), 0);
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
