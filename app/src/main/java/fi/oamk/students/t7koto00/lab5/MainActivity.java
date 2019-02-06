package fi.oamk.students.t7koto00.lab5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button buttonStart = null;
    public TextView textView1 = null;
    static final int ADD_NEW_PART_REQ_ID = 311;
    private PartArrayAdapter partArrayAdapter;
    ArrayList<WorkoutPartBase> workoutPartBases = new ArrayList<>();
    ArrayList<WorkoutPartBase> emptyList = new ArrayList<>();
    ListView listView1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart =(Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);
        listView1 = (ListView) findViewById(R.id.listView1);
        textView1 = (TextView) findViewById(R.id.textView1);
        workoutPartBases = read(this);

        setTotalLength();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.newmenu, menu);
        inflater.inflate(R.menu.clearmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_part:
                Intent intent = new Intent(this, AddPartActivity.class);
                startActivityForResult(intent, ADD_NEW_PART_REQ_ID);
                return true;
            case R.id.clear_part:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Clear workouts");
                builder.setMessage("Do you really want to clear all workouts?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        write(getApplicationContext(), emptyList);
                        workoutPartBases = read(getApplicationContext());
                        partArrayAdapter = new PartArrayAdapter(getApplicationContext(), workoutPartBases);
                        listView1.setAdapter(partArrayAdapter);
                        dialog.dismiss();

                        setTotalLength();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            Intent intent = new Intent(this, WorkoutActivity.class);
            intent.putExtra("DATA", workoutPartBases);
            startActivity(intent);
        }
    }

        @Override
    protected void onResume() {
        super.onResume();
        partArrayAdapter = new PartArrayAdapter(this, workoutPartBases);
        listView1.setAdapter(partArrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_PART_REQ_ID && resultCode == RESULT_OK) {
            WorkoutPartBase workoutPartBase = (WorkoutPartBase) data.getSerializableExtra("DATA");
            workoutPartBases.add(workoutPartBase);

            write(this, workoutPartBases);
            workoutPartBases = read(this);
            setTotalLength();
        }
    }

    public static void write(Context context, Object nameOfClass) {
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filename = "ArrayListFile.srl";
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(directory
                    + File.separator + filename));
            out.writeObject(nameOfClass);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<WorkoutPartBase> read(Context context) {

        ObjectInputStream input = null;
        ArrayList<WorkoutPartBase> ReturnClass = null;
        String filename = "ArrayListFile.srl";
        File directory = new File(context.getFilesDir().getAbsolutePath()
                + File.separator + "serlization");
        try {

            input = new ObjectInputStream(new FileInputStream(directory
                    + File.separator + filename));
            ReturnClass = (ArrayList<WorkoutPartBase>) input.readObject();
            input.close();

        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ReturnClass;
    }

public void setTotalLength()
{
    int totalLength = 0;
    for (int i = 0; i < workoutPartBases.size(); i++) {
        WorkoutPartBase part = workoutPartBases.get(i);
        totalLength = totalLength + part.getLength();
    }
    int minutes = (totalLength % 3600) / 60;
    int seconds = totalLength % 60;
    textView1.setText("Total length " +minutes +" minutes " + seconds + " seconds");
}
}
