package vloboda.myProject.breathe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import vloboda.myProject.breathe.R;


public class MainActivity extends AppCompatActivity {



    private TextView nonSmokerFor, saved, notSmoked;
    private ImageButton healthBtn;
    private String userID;

    private long dayCount;

    private User user;
    private Calendar mDate;
    private Calendar currentDate;



    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;


    private void EventChangeListener() {

        DocumentReference docRef = fStore.collection("users").document(userID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    System.err.println("Listen failed: " + error);
                    return;
                }

                if (value != null && value.exists()) {

                    user = value.toObject(User.class);
                    Log.d("TAG", " data retrieved ");

                    mDate = new GregorianCalendar((int) user.getYear(), (int) user.getMonth(), (int) user.getDate());

                    currentDate = Calendar.getInstance();




                    long diff = currentDate.getTimeInMillis() - mDate.getTimeInMillis();

                    dayCount = TimeUnit.MILLISECONDS.toDays(diff);


                    nonSmokerFor.setText(String.valueOf(dayCount));

                    saved.setText(String.valueOf(dayCount * user.price));
                    notSmoked.setText(String.valueOf(dayCount * user.daily));

                } else {
                    Log.d("TAG", " null data ");
                }
            }
        });
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        user = new User();


        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid().toString();




        nonSmokerFor = findViewById(R.id.twSmokeFreeFor);
        saved = findViewById(R.id.twMoneySaved);
        notSmoked = findViewById(R.id.twCigarettsNotSmoked);

        EventChangeListener();


        healthBtn = findViewById(R.id.IBhealth);

        healthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Health.class);
                intent.putExtra("dayCount", (int)dayCount);
                startActivity(intent);

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.changeDate:
                startActivity(new Intent(getApplicationContext(), StopSmoking.class));
                return true;

            case R.id.logOutMenu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                return true;

            case R.id.deleteAccount:
                FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getUid().toString()).delete();
                FirebaseAuth.getInstance().getCurrentUser().delete();
                startActivity(new Intent(getApplicationContext(),Login.class));

        }
        return super.onOptionsItemSelected(item);
    }




}