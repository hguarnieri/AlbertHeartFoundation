package hackathon.com.albertheartfoundation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.aevi.helpers.services.AeviServiceConnectionCallback;
import com.aevi.payment.PaymentRequest;
import com.aevi.payment.TransactionResult;
import com.aevi.printing.PrintService;
import com.aevi.printing.PrintServiceProvider;
import com.aevi.printing.model.Alignment;
import com.aevi.printing.model.PrintPayload;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;


public class Print extends Activity {

    private PrintServiceProvider serviceProvider = new PrintServiceProvider(this);
    private PrintService printService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        serviceProvider.connect(new AeviServiceConnectionCallback<PrintService>() {
            @Override
            public void onConnect(PrintService service) {

                if (service == null) {
                    // Print service failed to open, please check the ADB log file for details.
                    return;
                }

                PrintPayload printPayload = new PrintPayload();

                printPayload.append("Yeah!").align(Alignment.CENTER);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                Date date = new Date(System.currentTimeMillis());
                printPayload.append(String.format("Your donation was accepted!", dateFormatter.format(date)));

                for(int i = 0; i < 15; i++) {
                    printPayload.appendEmptyLine();
                }

                printService = service;
                printService.print(printPayload);
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
