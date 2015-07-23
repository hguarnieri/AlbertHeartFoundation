package hackathon.com.albertheartfoundation;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;


public class MainActivity extends FragmentActivity {

    PagesCollectionPagerAdapter mPagesCollectionPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load gender spinner data
        Spinner spinner_gender = (Spinner) findViewById(R.id.spinner_gender);
        // Build ArrayAdapter
        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Set layout
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter_gender);

        // Load age spinner data
        Spinner spinner_age = (Spinner) findViewById(R.id.spinner_age);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_age = ArrayAdapter.createFromResource(this,
                R.array.ages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(adapter_age);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mPagesCollectionPagerAdapter =
                new PagesCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagesCollectionPagerAdapter);

        // Init button actions
        Button btnSignin;
        btnSignin = (Button) findViewById(R.id.btn_signup_button);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainSignup.class);
                startActivity(i);
            }
        });

        Button btnDonate;
        btnDonate = (Button) findViewById(R.id.btn_donate_button);
        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Donate.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class PagesCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public PagesCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new PageObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(PageObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // Instances of this class are fragments representing a single
// object in our collection.
    public static class PageObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();

            WebView webView = (WebView) rootView.findViewById(R.id.webView);
            webView.loadUrl("file:///android_asset/page" + Integer.toString(args.getInt(ARG_OBJECT)) + ".html");

            //((TextView) rootView.findViewById(android.R.id.text1)).setText(
                  //  Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }

}