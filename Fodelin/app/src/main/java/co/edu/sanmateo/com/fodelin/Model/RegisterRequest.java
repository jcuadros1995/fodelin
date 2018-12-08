package co.edu.sanmateo.com.fodelin.Model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://calmat.000webhostapp.com/Register.php";
    private Map<String,String> params;

    public RegisterRequest(String nombres, int documento, String telefono, String direccion, String email, String password, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nombres", nombres);
        params.put("documento", documento+ "");
        params.put("telefono", telefono);
        params.put("direccion", direccion);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
