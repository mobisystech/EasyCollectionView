package com.mobisys.android.easycollectionviewsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobisys.android.easycollectionview.EasyListView;
import com.mobisys.android.easycollectionview.OnViewIdClickListener;
import com.mobisys.android.easycollectionview.ViewIdBinder;
import com.mobisys.android.easycollectionview.exceptions.ModelTypeMismatchException;
import com.mobisys.android.easycollectionviewsample.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] mOptions = {"Upcoming Movies ListView", "Upcoming Movie RecyclerView"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mOptions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position==0){
                    Intent intent = new Intent(MainActivity.this, UpcomingMovieListActivity.class);
                    startActivity(intent);
                } else if (position==1){
                    Intent intent = new Intent(MainActivity.this, MovieRecyclerListActivity.class);
                    startActivity(intent);
                }
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

    private String[] names={"Mahavir Jain", "Priyank Tiwari", "Vikas Hiran", "Rohit Panhalkar", "Sameer Khader"};
    private String[] emails={"mahavir@mobisys.in", "priyank@mobisys.in", "vikas@mobisys.in", "rohit@mobisys.in", "sameer@mobisys.in"};
    private String[] phones={"+919420730595", "+919881174007", "+919762214137", "+9190112021201", "+919213421394"};
    private List<Contact> populateContacts(){
        List<Contact> contacts = new ArrayList<>();
        for (int i=0;i<names.length;i++){
            Contact contact = new Contact(names[i], emails[i], phones[i]);
            contacts.add(contact);
        }

        return contacts;
    }
}
