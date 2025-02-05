package com.karim.vacationhere.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karim.vacationhere.R;
import com.karim.vacationhere.entities.Vacation;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationListItem;

        private VacationViewHolder(View itemView) {
            super(itemView);
            vacationListItem = itemView.findViewById(R.id.vacation_list_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){

                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    int position = getAdapterPosition();
                    final Vacation current = mVacation.get(position);

                    //Intent is used to switch screens
                    Intent intent = new Intent(context, VacationDetails.class);

                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("vacationTitle", current.getVacationTitle());
                    intent.putExtra("vacationHotel", current.getVacationHotel());
                    intent.putExtra("vacationStartDate", current.getStartDate());
                    intent.putExtra("vacationEndDate", current.getEndDate());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }

            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<Vacation> mVacation;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_vacation_list_item, parent,false);
        return new VacationViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder,int position) {

        if(mVacation != null) {
            Vacation current = mVacation.get(position);
            String title =  current.getVacationTitle();
            holder.vacationListItem.setText(title);

        }else{
            holder.vacationListItem.setText("No Title");
        }
    }

    public void setVacationTitle(List<Vacation> title) {
        mVacation = title;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mVacation != null)
            return mVacation.size();
        else return 0;
    }

    public void setVacations(List<Vacation> vacations){
        mVacation = vacations;
        notifyDataSetChanged();

    }
}
