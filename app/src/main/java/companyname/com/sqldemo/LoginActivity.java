package companyname.com.sqldemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LENOVO on 11/22/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private String username;
    private String pass;
    private Button sign_in_register;
    private RequestQueue requestQueue;
    private static final String url = " http://devkpl.com/login.php";
    AlertDialog.Builder builder;
    private StringRequest request;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        sign_in_register = (Button) findViewById(R.id.sign_in_register);
        builder=new AlertDialog.Builder(LoginActivity.this);
        //requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SAD","ASDASD");
                 username= email.getText().toString();
                pass=password.getText().toString();

                if(username.equals("")||pass.equals(""))
                {
                    builder.setTitle("Enter Missing Fields");
                    displayAlert("Enter a valid Username and Password");
                }
                else
                {
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray=new JSONArray(response);
                                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                                       /*
                                        String code=jsonObject.getString("code");
                                        if(code.equals("login_failed"))
                                        {
                                            builder.setTitle("Login Error");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        */
                                        //else
                                        {
                                            Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                           Bundle bundle=new Bundle();
                                            bundle.putString("name",jsonObject.getString("name"));
                                            bundle.putString("email",jsonObject.getString("email"));
                                            i.putExtras(bundle);
                                            startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this,"ERROR",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }
                    )
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String, String>();
                            params.put("user_name",username);
                            params.put("user_pass",pass);
                            return params;
                        }
                    };
                   // MySingleton.getInstance(LoginActivity.this).addToRequestque(stringRequest);
                    Volley.newRequestQueue(LoginActivity.this).add(stringRequest);
                }

            }
        });

    }

    public void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                email.setText("");
                password.setText("");
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}

