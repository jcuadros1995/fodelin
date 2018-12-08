
package co.edu.sanmateo.com.fodelin.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sanmateo.com.fodelin.Model.ConnectionDetector;
import co.edu.sanmateo.com.fodelin.Model.RegisterRequest;
import co.edu.sanmateo.com.fodelin.R;

public class ActivityRegister extends AppCompatActivity {

    private AppCompatCheckBox Terminos;
    private AppCompatTextView appCompatTextViewLoginLink;
    private AppCompatTextView appCompatTextViewConditionLink;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById( R.id.appCompatTextViewLoginLink);
        appCompatTextViewConditionLink = (AppCompatTextView) findViewById( R.id.appCompatTextViewConditionLink );
        Terminos = (AppCompatCheckBox) findViewById( R.id.Terminos );


        final TextInputEditText Nombres = (TextInputEditText) findViewById( R.id.textInputEditTextName );
        final TextInputEditText Documento = (TextInputEditText) findViewById( R.id.textInputEditTextDocumento );
        final TextInputEditText Telefono = (TextInputEditText) findViewById( R.id.textInputEditTextTelephone );
        final TextInputEditText Direccion = (TextInputEditText) findViewById( R.id.textInputEditTextAdress );
        final TextInputEditText Email = (TextInputEditText) findViewById( R.id.textInputEditTextEmail );
        final TextInputEditText Password = (TextInputEditText) findViewById( R.id.textInputEditTextPassword );

        final AppCompatButton Registrar = (AppCompatButton) findViewById( R.id.appCompatButtonRegister );

        cd = new ConnectionDetector(this);

        appCompatTextViewConditionLink.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityRegister.this, ActivityPolitica.class );
                startActivity( intent );
            }
        } );

        appCompatTextViewLoginLink.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ActivityRegister.this, ActivityLogin.class );
                startActivity( intent );
            }
        } );

        Registrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Terminos.isChecked()) {


                    if (Nombres.getText().toString().isEmpty() || Documento.getText().toString().isEmpty() || Telefono.getText().toString().isEmpty() || Direccion.getText().toString().isEmpty() || Email.getText().toString().isEmpty() || Password.getText().toString().isEmpty()) {

                        AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityRegister.this);
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


                            final String nombres = Nombres.getText().toString();
                            final int documento = Integer.parseInt( Documento.getText().toString() );
                            final String telefono = Telefono.getText().toString();
                            final String direccion = Direccion.getText().toString();
                            final String email = Email.getText().toString();
                            final String password = Password.getText().toString();

                            Response.Listener <String> respoListener = new Response.Listener <String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject( response );
                                        boolean success = jsonResponse.getBoolean( "success" );

                                        if (success) {
                                            Intent intent = new Intent( ActivityRegister.this, ActivityLogin.class );
                                            ActivityRegister.this.startActivity( intent );
                                        } else {
                                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityRegister.this);
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


                            RegisterRequest registerRequest = new RegisterRequest( nombres, documento, telefono, direccion, email, password, respoListener );
                            RequestQueue queue = Volley.newRequestQueue( ActivityRegister.this );
                            queue.add( registerRequest );

                            final ProgressDialog progressDialog = new ProgressDialog( ActivityRegister.this, R.style.AppTheme_Dark_Dialog );
                            progressDialog.setIndeterminate( true );
                            progressDialog.setMessage( "Creando Usuario..." );
                            progressDialog.show();

                            new android.os.Handler().postDelayed( new Runnable() {
                                public void run() {

                                    // onLoginFailed();
                                    progressDialog.dismiss();
                                }
                            }, 2000 );
                        } else {
                            AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityRegister.this);
                            myBuild.setMessage("No hay acceso a internet, por favor conectese a internet para poder realizar el registro");
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
                    }
                }else {
                    AlertDialog.Builder myBuild = new AlertDialog.Builder(ActivityRegister.this);
                    myBuild.setMessage("Debes aceptar los términos y condiciones para continuar con el proceso");
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

            }
        } );

    }
}
