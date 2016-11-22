package companyname.com.sqldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Login_success extends AppCompatActivity {
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        name= (TextView) findViewById(R.id.name);
        email= (TextView) findViewById(R.id.email);
        Bundle bundle=getIntent().getExtras();
        name.setText("Welcome "+bundle.getString("name"));
        email.setText("Welcome "+bundle.getString("email"));
    }
}
