package com.example.todoboom;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mNames = new ArrayList<>();
    int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText1 = (EditText)findViewById(R.id.input_text);
        Button button1 = (Button)findViewById(R.id.input_button);
        final TextView TextError = (TextView) findViewById(R.id.errorText);

        if(savedInstanceState != null){
            //TextError.setText("change");
            String saved_string = savedInstanceState.getString("names");
            String[] split_string = saved_string.split(",");
            mNames.addAll(Arrays.asList(split_string));
            initRecyclerView();
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_text = editText1.getText().toString();
                if(!new_text.equals("")){
                    mNames.add(new_text);
                    initRecyclerView();
                    editText1.setText("");
                    TextError.setText("");
                }
                else{
                    TextError.setText("You can't create an empty TODO item, oh silly!");
                }
            }
        });

        initImageBitmap();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String new_string = "";
        for (int i=0; i<mNames.size(); i++){
            new_string = new_string+mNames.get(i)+",";
        }
        outState.putString("names", new_string);
    }

    private void initImageBitmap(){
        initRecyclerView();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.my_recyclerview);
        final RecyclerViewAdaptor adaptor = new RecyclerViewAdaptor(this, mNames);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor.setOnItemClickListener(new RecyclerViewAdaptor.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                if(!mNames.get(position).contains("Pressed")){
                    String new_string = "Pressed " + mNames.get(position);
                    mNames.set(position, new_string);
                    adaptor.notifyItemChanged(position);
                }

            }
        });
    }
}
