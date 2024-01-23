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


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private List<ExampleItem> exampleList;
    private List<ExampleItem> exampleListFull;
    private Context mContext;

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;

        Button btn,btn2;
//        RelativeLayout parentLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView1 = (TextView) itemView.findViewById(R.id.id);
            this.textView2 = (TextView) itemView.findViewById(R.id.donorName);
            this.textView3 = (TextView) itemView.findViewById(R.id.donorContact);
            this.textView4 = (TextView) itemView.findViewById(R.id.email);
            this.textView5 = (TextView) itemView.findViewById(R.id.blood);


            this.btn = (Button) itemView.findViewById(R.id.btnYes);
            this.btn2 = (Button) itemView.findViewById(R.id.btnNo);


        }
    }

    public ExampleAdapter(List<ExampleItem> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem currentItem = (ExampleItem) this.exampleList.get(position);
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());
        holder.textView5.setText(currentItem.getmText5());




        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,bookslot.class);
                i.putExtra("bloodgroup",currentItem.getmText5());
                i.putExtra("emailid",currentItem.getmText4());
                mContext.startActivity(i);

            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,feedbackform.class);
                i.putExtra("username",currentItem.getmText2());
                i.putExtra("bloodgroup",currentItem.getmText5());
                i.putExtra("emailid",currentItem.getmText4());
                mContext.startActivity(i);

            }
        });
    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(List<ExampleItem> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}