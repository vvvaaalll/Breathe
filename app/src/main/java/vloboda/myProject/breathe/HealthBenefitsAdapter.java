package vloboda.myProject.breathe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vloboda.myProject.breathe.R;

public class HealthBenefitsAdapter extends RecyclerView.Adapter<HealthBenefitsAdapter.MyViewHolder> {

    Context context;
    ArrayList<HealthInfo>  list;
    int daysSmokeFree;

    public HealthBenefitsAdapter(Context context, ArrayList<HealthInfo> list,int daysSmokeFree) {
        this.context = context;
        this.list = list;
        this.daysSmokeFree = daysSmokeFree;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.health, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HealthInfo healthInfo = list.get(position);

        holder.healthBenefit.setText(healthInfo.mHealthInfo);
        holder.progressBar.setVisibility(View.VISIBLE);

        if((double)daysSmokeFree/(double)healthInfo.timeHasToPass > 1){
            holder.progressBar.setProgress(100, true);
        }
        else{
            holder.progressBar.setProgress((int)((double)daysSmokeFree/(double)healthInfo.timeHasToPass * 100));
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView healthBenefit;
        ProgressBar progressBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            healthBenefit = itemView.findViewById(R.id.tvHealthBenefit);
            progressBar = itemView.findViewById(R.id.pgbar);

        }
    }
}
