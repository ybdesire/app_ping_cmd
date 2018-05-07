package example.ybdesire.com.ping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
            int timeOut = p.waitFor();
            if(timeOut==1){
                return "Request time out\n";
            }

            while ((error = ie.readLine()) != null
                    && !error.equals("null")) {
                data += error + "\n";
            }


            String line = null;
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                data += line + "\n";
            }

            int startIndex = data.indexOf("\n");
            int endIndex = data.indexOf("---");

            if(endIndex>startIndex)
            {
                data = data.substring(startIndex+1,endIndex);
            }
            Log.v("ping_cmd", data);
            return data;
        } catch (IOException e) {
            return "IOException\n";
        }
        catch (InterruptedException e){
            return "InterruptedException\n";
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
                TextView txt=findViewById(R.id.txt_output);//find output label by id
                txt.setMovementMethod(new ScrollingMovementMethod());//enable text view
                String s = exeCmd("127.0.0.1");
                txt.append(s+s+s+s+s+s+s+s+s+s+s+s+s+s+s+s+s+s+"\nend");
                //scrolling to end
                while (txt.canScrollVertically(1)) {
                    txt.scrollBy(0, 10);
                }

            }
        });

    }
}
