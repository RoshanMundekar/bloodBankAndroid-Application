package com.baecon.blood_blank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ExampleAdapter6 extends RecyclerView.Adapter<ExampleAdapter6.ExampleViewHolder> {
    private List<ExampleItem6> exampleList;
    private List<ExampleItem6> exampleListFull;
    private Context mContext;

    class ExampleViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;




        ExampleViewHolder(View itemView) {
            super(itemView);


            this.textView1 = (TextView) itemView.findViewById(R.id.user);
            this.textView2 = (TextView) itemView.findViewById(R.id.bloodroup);
            this.textView3 = (TextView) itemView.findViewById(R.id.email);
            this.textView4 = (TextView) itemView.findViewById(R.id.status);
            this.textView5 = (TextView) itemView.findViewById(R.id.feedback);




        }
    }

    public ExampleAdapter6(List<ExampleItem6> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle6, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem6 currentItem = (ExampleItem6) this.exampleList.get(position);
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());
        holder.textView5.setText(currentItem.getmText5());





    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(ArrayList<ExampleItem6> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}