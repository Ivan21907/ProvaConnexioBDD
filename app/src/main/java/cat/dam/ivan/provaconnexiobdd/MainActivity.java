package cat.dam.ivan.provaconnexiobdd;

import androidx.appcompat.app.AppCompatActivity;



import android.os.AsyncTask;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MainActivity extends AppCompatActivity {



    TextView text,errorText;

    Button show;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView);

        errorText = (TextView) findViewById(R.id.textView2);

        show = (Button) findViewById(R.id.button);



        show.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                new Async().execute();

            }

        });

    }



    class Async extends AsyncTask<Void, Void, Void> {



        String records = "",error="";

        @Override

        protected Void doInBackground(Void... voids) {

            try

            {

                Class.forName("com.mysql.jdbc.Driver");

                Connection connection = DriverManager.getConnection("jdbc:mysql://trackingmysql.cs5uvbmmvwen.us-east-1.rds.amazonaws.com:3306/btt-tracking", "admin", "12345678");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT * FROM Persona");

                while(resultSet.next()) {

                    records += resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3) + ", " +
                            resultSet.getString(4)  + ", " + resultSet.getString(5)  +  ", " + resultSet.getString(6) + "\n";

                }

            }

            catch(Exception e)

            {

                error = e.toString();

            }

            return null;

        }



        @Override

        protected void onPostExecute(Void aVoid) {

            text.setText(records);

            if(error != "")

                errorText.setText(error);

            super.onPostExecute(aVoid);

        }
    }
}