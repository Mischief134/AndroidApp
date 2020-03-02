package com.example.mistplay;

import android.os.Bundle;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MaterialSearchView searchView;
    ListView listView;
//    @POST("/search")
//    Call<String> storeString(@Body String val);

    ArrayList<String> values = new ArrayList<>();

//        String[] values ={
//            "Nuri",
//        "HAw",
//            "ASDADA",
//            "ASDAD",
//            "BBBBBB"
//    };
//private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        textViewResult = findViewById(R.id.text_view_result);

        String url = "http://10.0.2.2:3001";
        String url_old ="https://jsonplaceholder.typicode.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONParser1 jason = retrofit.create(JSONParser1.class);

        Call<List<Post>> call = jason.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {

//                   textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();
                for (Post post : posts) {
                    values.add(post.getTitle());
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });



















        getSupportActionBar().setTitle("Search");
//        toolbar.setTitleTextColor();
        listView=(ListView)findViewById(R.id.listVIEW);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);
        searchView=(MaterialSearchView)findViewById(R.id.search_view);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,values);
                listView.setAdapter(adapter);
            }

        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()){
                    ArrayList<String> result = new ArrayList<String>();
                    for(String x :values){
                        if(x.contains(newText)){
                            result.add(x);
                        }
                    }
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,result);
                    listView.setAdapter(adapter);

                }
                else{
                    ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,values);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }


}
