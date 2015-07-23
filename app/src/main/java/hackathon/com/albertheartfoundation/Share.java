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

import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintServiceProvider;

import java.math.BigDecimal;
import java.util.Currency;


public class Share extends ActionBarActivity {

    Button btnMom;
    Button btnDad;
    Button btnOther;
    Button btnCancelShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        btnMom = (Button) findViewById(R.id.btnMom);
        btnDad = (Button) findViewById(R.id.btnDad);
        btnOther = (Button) findViewById(R.id.btnOther);
        btnCancelShare = (Button) findViewById(R.id.btnCancelShare);

        btnMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactInfo.class);
                startActivity(i);
                finish();
            }
        });

        btnDad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactInfo.class);
                startActivity(i);
                finish();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactInfo.class);
                startActivity(i);
                finish();
            }
        });

        btnCancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
