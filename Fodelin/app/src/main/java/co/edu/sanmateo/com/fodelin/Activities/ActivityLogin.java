package co.edu.sanmateo.com.fodelin.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sanmateo.com.fodelin.Model.ConnectionDetector;
import co.edu.sanmateo.com.fodelin.Model.LoginRequest;
import co.edu.sanmateo.com.fodelin.R;

public class ActivityLogin extends AppCompatActivity {

    ConnectionDetector cd;
    private AppCompatTextView textViewLinkRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        textViewLinkRegister = (AppCompatTextView) findViewById( R.id.textViewLinkRegister );

        final TextInputEditText Email = (TextInputEditText) findViewById( R.id.textInputEditTextEmail );
        final TextInputEditText Password = (TextInputEditText) findViewById( R.id.textInputEditTextPassword );

        final AppCompatButton Registrar = (AppCompatButton) findViewById( R.id.appCompatButtonLogin );

        cd = new ConnectionDetector(this);

        textViewLinkRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityLogin.this, ActivityRegister.class );
                startActivity( intent );
            }
        } );

        Registrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Email.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {

                    AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityLogin.this);
                    myBuild.setMessage("Uno o más campos vacios");
                    myBuild.setTitle("Fodelin");

                    myBuild.setNegativeButton("ACEPTAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = myBuild.create();
                    dialog.show();
                } else {

                    if (cd.isConnected()) {

                        final String email = Email.getText().toString();
                        final String password = Password.getText().toString();

                        Response.Listener <String> respoListener = new Response.Listener <String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject( response );
                                    boolean success = jsonResponse.getBoolean( "success" );

                                    if (success) {
                                        String nombres = jsonResponse.getString( "nombres" );

                                        Intent intent = new Intent( ActivityLogin.this, MainActivity.class );
                                        intent.putExtra( "nombres", nombres );

                                        ActivityLogin.this.startActivity( intent );
                                    } else {
                                        AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityLogin.this);
                                        myBuild.setMessage("Usuario y/o Password incorrectos");
                                        myBuild.setTitle("Fodelin");

                                        myBuild.setNegativeButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        AlertDialog dialog = myBuild.create();
                                        dialog.show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };


                        LoginRequest loginRequest = new LoginRequest( email, password, respoListener );
                        RequestQueue queue = Volley.newRequestQueue( ActivityLogin.this );
                        queue.add( loginRequest );

                        final ProgressDialog progressDialog = new ProgressDialog(ActivityLogin.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Ingresando...");
                        progressDialog.show();

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {

                                        // onLoginFailed();
                                        progressDialog.dismiss();
                                    }
                                }, 2000);
                    }else {
                        Toast.makeText(ActivityLogin.this, "No hay acceso a internet, por favor conectese a internet para poder realizar el registro",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        } );
    }
}
