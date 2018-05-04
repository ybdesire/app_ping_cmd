package example.ybdesire.com.ping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public String exeCmd(String ipAddress)
    {
        try {

            Process p = Runtime.getRuntime().exec("ping -c 1 -w 5 " + ipAddress);
            String data = "";
            BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String error = "";
            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                data += error + "\n";
            }
            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                data += line + "\n";
            }

            Log.v("ls", data);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button click listener
        Button btn=findViewById(R.id.button_ping_click);//find button by id(defined at activity_main.xml)
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView txt=findViewById(R.id.txt_ip_label);//find output label by id
                String s = exeCmd("127.0.0.1");
                Log.e("executeTop", s);
                txt.setText(s);
            }
        });

    }
}
