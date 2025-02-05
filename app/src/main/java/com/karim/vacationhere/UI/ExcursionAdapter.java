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
import com.karim.vacationhere.entities.Excursion;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionListItem;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionListItem = itemView.findViewById(R.id.excursionListItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    int position = getAdapterPosition();
                    final Excursion current = mExcursion.get(position);

                    Intent intent = new Intent(context, ExcursionDetails.class);

                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionID", current.getExcursionID());
                    intent.putExtra("excursionTitle", current.getExcursionTitle());
                    intent.putExtra("excursionStartDate", current.getExcursionDate());
                    context.startActivity(intent);
                }

            });

        }
    }

    private final LayoutInflater mInflater;

    private final Context context;

    private List<Excursion> mExcursion;

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_excursion_list_item, parent,false);
        return new ExcursionAdapter.ExcursionViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {

        if(mExcursion != null) {
            Excursion current = mExcursion.get(position);
            String title =  current.getExcursionTitle();
            holder.excursionListItem.setText(title);


        }else{
            holder.excursionListItem.setText("No Title");
        }
    }

    public void setVacationTitle(List<Excursion> title) {
        mExcursion = title;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mExcursion != null)
            return mExcursion.size();
        else return 0;
    }

    public void setExcursions (List<Excursion> excursions){
        mExcursion = excursions;
        notifyDataSetChanged();
    }
}
