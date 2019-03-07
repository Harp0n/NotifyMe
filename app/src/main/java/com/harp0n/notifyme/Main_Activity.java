package com.harp0n.notifyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

////////////////////////////// To dla ciebie Seba, ale potrzebowałem głównej aktywności żeby sprawdzić moją /////////////////
public class Main_Activity extends AppCompatActivity {

    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Main_Activity.this, NotifyEditor_Activity.class);
                Main_Activity.this.startActivity(myIntent);
            }
        });

    }
}
