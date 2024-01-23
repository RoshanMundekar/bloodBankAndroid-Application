package com.baecon.blood_blank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ExampleAdapter2 extends RecyclerView.Adapter<ExampleAdapter2.ExampleViewHolder> {
    private List<ExampleItem2> exampleList;
    private List<ExampleItem2> exampleListFull;
    private Context mContext;

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;

        Button btn;
        Button btn1;
        Button btn2;
//        RelativeLayout parentLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView1 = (TextView) itemView.findViewById(R.id.HOSPITALNAME);
            this.textView2 = (TextView) itemView.findViewById(R.id.bloodroup);
            this.textView3 = (TextView) itemView.findViewById(R.id.input_details);
            this.textView4 = (TextView) itemView.findViewById(R.id.location);
            this.textView5 = (TextView) itemView.findViewById(R.id.input_time_confirm);

            this.btn = (Button) itemView.findViewById(R.id.btnYes);
            this.btn1 = (Button) itemView.findViewById(R.id.btnno);
            this.btn2 = (Button) itemView.findViewById(R.id.upload);
        }
    }

    public ExampleAdapter2(List<ExampleItem2> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle3, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem2 currentItem = (ExampleItem2) this.exampleList.get(position);
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());
        holder.textView5.setText(currentItem.getmText5());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hospital= String.valueOf(currentItem.getmText1());
                String bloodgroup=String.valueOf(currentItem.getmText2());
                String time=String.valueOf(currentItem.getmText5());

                String url = UrlLinks.donation;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

                nameValuePairs.add(new BasicNameValuePair("hospital", hospital));
                nameValuePairs.add(new BasicNameValuePair("bloodgroup", bloodgroup));
                nameValuePairs.add(new BasicNameValuePair("time", time));


                String result = null;
                try {
                    result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result.equals("success")) {

                    Toast.makeText(mContext, "User donate blood successfully", Toast.LENGTH_SHORT).show();


                }
            }
        });

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hospital= String.valueOf(currentItem.getmText1());
                String bloodgroup=String.valueOf(currentItem.getmText2());
                String time=String.valueOf(currentItem.getmText5());
                String url = UrlLinks.donationreject;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("hospital", hospital));
                nameValuePairs.add(new BasicNameValuePair("bloodgroup", bloodgroup));
                nameValuePairs.add(new BasicNameValuePair("time", time));


                String result = null;
                try {
                    result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result.equals("success")) {

                    Toast.makeText(mContext, "User Not available for blood donation", Toast.LENGTH_SHORT).show();


                }
            }
        });


        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,certificate.class);
                i.putExtra("hospital",currentItem.getmText1());
                i.putExtra("mail",currentItem.getmText4());
                i.putExtra("bloodgroup",currentItem.getmText2());
                mContext.startActivity(i);




            }
        });
    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(List<ExampleItem2> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}