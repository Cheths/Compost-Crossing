package com.cs442.group10.compost_crossing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.group10.compost_crossing.Composter.ComposterRegistration;
import com.cs442.group10.compost_crossing.newsArticle.Article;
import com.cs442.group10.compost_crossing.newsArticle.News;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements ViewListingFragment.OnListingSelectedListener {

    Button readArticle;
    DbMain db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new DbMain(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readArticle = (Button) findViewById(R.id.readArticleButton);
        readArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickingReadArticleButton();
            }
        });

        final Button Composter= (Button)findViewById(R.id.compostButton);
        final Button residentButton= (Button)findViewById(R.id.residentButton);

        Composter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                int count = db.numberOfentries();
                if(count >= 1){
                    Intent compostDetailIntent = new Intent(getApplicationContext(),CompostDetailActivity.class);
                    startActivity(compostDetailIntent);
                }
                else{
                    Intent composterregistration = new Intent(getApplicationContext(),ComposterRegistration.class);
                    startActivity(composterregistration);
                }
            }
        });

        residentButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

//                Intent i = new Intent(getBaseContext(), "give the required class name");
//                startActivity(i);

            }
        });

        final Button button = (Button) findViewById(R.id.compostButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.screen_2);
                ListView lv = (ListView) findViewById(R.id.expandableListView);
                // We need to use a different list item layout for devices older than Honeycomb
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, Listings.Names);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//temporary work around to navigate to detail view
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent compostDetailIntent = new Intent(getApplicationContext(),CompostDetailActivity.class);
                        startActivity(compostDetailIntent);
                    }
                });
                /*
                //Fragment fragment = new RepeatEntry();
                //FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                //category_cont is a linear layout container for my fragment
                ViewListingFragment viewFrag = new ViewListingFragment();
                ft.replace(R.id.fragment_container, viewFrag).addToBackStack("tag");
                //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();*/
            }
        });
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            ViewListingFragment firstFragment = new ViewListingFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            //Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();

        }
    }

    public void onListingSelected(int position) {
        /*Code for fragment transaction when clicking on item*/
        Intent compostDetailIntent = new Intent(this,CompostDetailActivity.class);
        startActivity(compostDetailIntent);
    }

    public void onClickingReadArticleButton(){
        Intent readArticleIntent = new Intent(this, Article.class);
        startActivity(readArticleIntent);
    }
}