package vloboda.myProject.breathe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import vloboda.myProject.breathe.R;

public class Health extends AppCompatActivity {

    private int dayCount;

    private RecyclerView recyclerView;
    private FirebaseFirestore fStore;
    private ArrayList<HealthInfo> healthInfos;
    HealthBenefitsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helath);

     recyclerView = findViewById(R.id.healthListRW);
     recyclerView.setHasFixedSize(true);
     recyclerView.setLayoutManager(new LinearLayoutManager(this));

    dayCount = getIntent().getIntExtra("dayCount", dayCount);

    fStore = FirebaseFirestore.getInstance();

    healthInfos = new ArrayList<HealthInfo>();

    myAdapter = new HealthBenefitsAdapter(Health.this, healthInfos, dayCount);
    recyclerView.setAdapter(myAdapter);

    EventChangeListener();


    }



    private void EventChangeListener() {

        fStore.collection("HealthBenefits")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                        }

                        for (DocumentChange fDatabase : value.getDocumentChanges()) {

                            if (fDatabase.getType() == DocumentChange.Type.ADDED) {
                                HealthInfo healthInfo = fDatabase.getDocument().toObject(HealthInfo.class);


                                healthInfos.add(healthInfo);
                                healthInfos.sort(new TimeSorter());

                            }
                            myAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.health_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.returnToMain:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;

            case R.id.logOutMenu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



}