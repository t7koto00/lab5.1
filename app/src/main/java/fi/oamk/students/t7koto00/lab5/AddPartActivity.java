package fi.oamk.students.t7koto00.lab5;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

public class AddPartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAdd = null;
    private EditText editTextPicker = null;
    int length = 0;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_part);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        editTextPicker = (EditText) findViewById(R.id.editTextPicker);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonAdd) {

            try {
                length = Integer.parseInt(editTextPicker.getText().toString());
            } catch(NumberFormatException nfe) {

            }

            if (type == "pause")
            {
                PausePart pausePart = new PausePart(length);
                returnData(pausePart);
            }
            else if(type == "workOut")
            {
                WorkoutPart workoutPart = new WorkoutPart(length);
                returnData(workoutPart);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_Pause:
                if (checked)
                    type = "pause";
                break;
            case R.id.radio_workOut:
                if (checked)
                    type = "workOut";
                break;
        }
    }

    private void returnData(WorkoutPartBase data) {
        Intent returnIntent = new Intent(this, MainActivity.class);
        returnIntent.putExtra("DATA", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
