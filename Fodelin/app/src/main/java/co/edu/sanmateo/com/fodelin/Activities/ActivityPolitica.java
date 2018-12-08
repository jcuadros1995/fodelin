package co.edu.sanmateo.com.fodelin.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import co.edu.sanmateo.com.fodelin.R;

public class ActivityPolitica extends AppCompatActivity {

    private AppCompatButton appCompatButtonpolitica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_politica );

        appCompatButtonpolitica = (AppCompatButton) findViewById( R.id.appCompatButtonpolitica );

        appCompatButtonpolitica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityPolitica.this, ActivityRegister.class );
                startActivity( intent );
            }
        } );
    }
}
