package unicornheight.dairyapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NewEntry extends AppCompatActivity {

    private static final String TAG = "Entry";
    FloatingActionButton floatingActionButton;
    private static final String TITLE = "title";
    private static final String DETAILS = "details";
    private static final String MESSAGE = "someMessage";
    private static final String DATABASE_CONFIG = "Diary";
    private static final String TIMESTAMP = "timeUpdate";
    public static final String ENTRY_ARGS = "entry_args";
    private FirebaseDatabase db;
    private DiaryEntry entry;
    DatabaseReference entryRef;
    String getID;
    EditText title;
    EditText details;
    EditText message;
    boolean update = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        db = FirebaseDatabase.getInstance();
        entryRef = db.getReference(DATABASE_CONFIG);
        floatingActionButton = findViewById(R.id.fab);
        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        message = findViewById(R.id.message);
        Intent parentIntent = getIntent();
        if (parentIntent.hasExtra(ENTRY_ARGS)){
            update = true;
            entry = getIntent().getParcelableExtra(ENTRY_ARGS);
            title.setText(entry.getTitle());
            details.setText(entry.getDetails());
            message.setText(entry.getSomeMessage());
            getID = entry.getId();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update){
                    updateEntry();
                }else {
                    addEntry();
                }
            }
        });
    }

    private void addEntry() {
        getID = entryRef.child(DATABASE_CONFIG).push().getKey();
        entry = getData();
        entryRef.child(getID).setValue(entry).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "update successful");
                Toast.makeText(NewEntry.this, "Successfully ",
                        Toast.LENGTH_SHORT).show();
            }

        });
        finish();
    }

    private DiaryEntry getData() {
        entry = new DiaryEntry();
        entry.setTitle(title.getText().toString());
        entry.setDetails(details.getText().toString());
        entry.setSomeMessage(message.getText().toString());
        entry.setId(getID);
        return entry;
    }


    private void updateEntry(){
        entry = getData();
        entryRef.child(entry.getId()).setValue(entry);
        Intent intent = new Intent(NewEntry.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
