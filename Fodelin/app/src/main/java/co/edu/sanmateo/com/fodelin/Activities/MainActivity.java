package co.edu.sanmateo.com.fodelin.Activities;

    import android.app.Activity;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.support.design.widget.TextInputEditText;
    import android.support.v7.app.AlertDialog;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.AppCompatTextView;
    import android.view.Menu;
    import android.view.MenuItem;

    import co.edu.sanmateo.com.fodelin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        final AppCompatTextView Nombres = (AppCompatTextView) findViewById( R.id.textViewName );

        Intent intent = getIntent();
        String nombres = intent.getStringExtra( "nombres" );
        String cedula = intent.getStringExtra( "cedula" );
        String telefono = intent.getStringExtra( "telefono" );
        String direccion = intent.getStringExtra( "direccion" );
        String email = intent.getStringExtra( "email" );

        Nombres.setText( nombres );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_exit, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
            myBuild.setMessage("¿Deseas Salir?");
            myBuild.setTitle("Salir");
            myBuild.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = myBuild.create();
            dialog.show();
        }

        return super.onOptionsItemSelected( item );
    }
}
