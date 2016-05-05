package com.example.android.fractal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abc on 01-May-16.
 */
public class ManagerCardAdapter extends RecyclerView.Adapter<ManagerCardAdapter.ViewHolder> {
    private Context context;

    //List of superHeroes
    List<ManagerApplicationData> superHeroes;

    public ManagerCardAdapter(List<ManagerApplicationData> superHeroes, Context context){
        super();
        //Getting all the superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ManagerApplicationData superHero =  superHeroes.get(position);
        holder.textViewName.setText(superHero.getName());
        holder.textViewUsername.setText(superHero.getEmployeeid());
        holder.textViewVisatype.setText(superHero.getVisatype());
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textViewName;
        public TextView textViewUsername;
        public TextView textViewVisatype;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textView);
            textViewUsername = (TextView) itemView.findViewById(R.id.textView4);
            textViewVisatype = (TextView) itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            String visaid, name, employee_code,country, visatype, status, passportno, passportissue, passportexpiery, visastart, visaend, projectcode;
            ManagerApplicationData application_details =  superHeroes.get(pos);
            visaid = application_details.getVisaid();
            name = application_details.getName();
            employee_code = application_details.getEmployeeid();
            country = application_details.getCountry();
            visatype = application_details.getVisatype();
            status = application_details.getStatus();
            passportno = application_details.getPassportNumber();
            passportissue = application_details.getPlaceIssue();
            passportexpiery = application_details.getPassportExpieryDate();
            visastart = application_details.getVisaStartDate();
            visaend = application_details.getVisaEndDate();
            projectcode = application_details.getProjectnumber();
            Intent in = new Intent(v.getContext(), ManagerApplicationDecisionActivity.class);
            in.putExtra("visa_id",visaid);
            in.putExtra("employee_name",name);
            in.putExtra("employee_code",employee_code);
            in.putExtra("country",country);
            in.putExtra("visatype",visatype);
            in.putExtra("status",status);
            in.putExtra("passport_number",passportno);
            in.putExtra("place_issue",passportissue);
            in.putExtra("passport_expiry_date",passportexpiery);
            in.putExtra("date_required_from",visastart);
            in.putExtra("date_required_to",visaend);
            in.putExtra("projectcode",projectcode);
            v.getContext().startActivity(in);
        }
    }
}
