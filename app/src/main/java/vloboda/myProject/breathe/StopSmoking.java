package vloboda.myProject.breathe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vloboda.myProject.breathe.R;

public class StopSmoking extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatePicker datePicker;
    private TextView dateView, price, daily;
    private Button stopSmokingBtn;
    private String  userID;
    private int year, month, day, mPrice, mDaily;
    private Calendar mCalendar;
    private Date mDate;

    private Dialog dialog;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_smoking);

        mCalendar = Calendar.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        dateView = findViewById(R.id.editTextDate);
        price = findViewById(R.id.editTextPrice);
        daily = findViewById(R.id.editTextDaily);
        stopSmokingBtn = findViewById(R.id.stopSmokingBtn);

        mDate = new Date();

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vloboda.myProject.breathe.DatePicker mDatePickerDialogFragment = new vloboda.myProject.breathe.DatePicker();
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
            }
        });

        stopSmokingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPrice = Integer.parseInt(price.getText().toString());
                mDaily = Integer.parseInt(daily.getText().toString());

                if(fStore.collection("users") != null){
                    FirebaseFirestore.getInstance().collection("users")
                            .document(FirebaseAuth.getInstance().getUid().toString()).delete();


                }

                mDate = mCalendar.getTime();

                userID = fAuth.getCurrentUser().getUid().toString();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("price",mPrice);
                user.put("daily",mDaily);
                user.put("year", mDate.getYear()+1900);
                user.put("month", mDate.getMonth());
                user.put("date", mDate.getDate());



                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(StopSmoking.this, "Congratulations on your first step to being smoke free",Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onSuccess: user " + userID + "stopped smoking");



                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "onFailure: "+e.toString());

                    }
                });

            }
        });


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String selectedDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(mCalendar.getTime());
        dateView.setText(selectedDate);
    }

}