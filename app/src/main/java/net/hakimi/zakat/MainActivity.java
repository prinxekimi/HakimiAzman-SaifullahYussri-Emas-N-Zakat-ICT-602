package net.hakimi.zakat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText goldWeight, goldValue;
    Button btnCalc, btnReset;
    TextView tvOutput, tvOutput2, tvOutput3;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float weightgold;
    float gvalue;


    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    private Menu menu;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos1, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        goldWeight = (EditText) findViewById(R.id.addGoldWeight);
        goldValue = (EditText) findViewById(R.id.addGoldValue);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        tvOutput = (TextView) findViewById(R.id.tvOutput);
        tvOutput2 = (TextView) findViewById(R.id.tvOutput2);
        tvOutput3 = (TextView) findViewById(R.id.tvOutput3);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnCalc.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        weightgold = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("value", 0.0F);

        goldWeight.setText(""+weightgold);
        goldValue.setText(""+gvalue);


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.aboutus:
                //Toast.makeText(this,"About Emas N Zakat",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.btnCalc:
                    calc();
                    break;

                case R.id.btnReset:
                    goldWeight.setText("");
                    goldValue.setText("");
                    tvOutput.setText("");
                    tvOutput2.setText("");
                    tvOutput3.setText("");

                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this,"Unknown Exception" + exp.getMessage(),Toast.LENGTH_SHORT).show();

            Log.d("Exception",exp.getMessage());

        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float weightgold = Float.parseFloat(goldWeight.getText().toString());
        float gvalue = Float.parseFloat(goldValue.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double totGvalue;
        double uruf;
        double payable;
        double totZakat;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", weightgold);
        editor.apply();
        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();


        if (stat.equals("Keep")){
            totGvalue= weightgold * gvalue;
            uruf= weightgold - 85;

            if(uruf>=0.0) {
                payable = uruf * gvalue;
                totZakat = payable * 0.025;
            }

            else{
                payable = 0.0;
                totZakat = payable * 0.025;

            }

            tvOutput.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(payable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));
        }

        else{
            totGvalue= weightgold * gvalue;
            uruf= weightgold - 200;

            if(uruf>=0.0) {
                payable = uruf * gvalue;
                totZakat = payable * 0.025;
            }

            else{
                payable = 0.0;
                totZakat = payable * 0.025;

            }

            tvOutput.setText("Total value of the gold: RM"+ df.format(totGvalue));
            tvOutput2.setText("Zakat payable: RM"+ df.format(payable));
            tvOutput3.setText("Total Zakat: RM"+ df.format(totZakat));

        }

    }


}