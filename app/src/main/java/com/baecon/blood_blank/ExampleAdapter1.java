package com.baecon.blood_blank;

import android.content.Context;
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


public class ExampleAdapter1 extends RecyclerView.Adapter<ExampleAdapter1.ExampleViewHolder> {
    private List<ExampleItem1> exampleList;
    private List<ExampleItem1> exampleListFull;
    private Context mContext;


    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        Button btn;
        Button btn1;
//        RelativeLayout parentLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView2 = (TextView) itemView.findViewById(R.id.donarname);
            this.textView3 = (TextView) itemView.findViewById(R.id.donorContact);
            this.textView4 = (TextView) itemView.findViewById(R.id.email);
            this.btn = (Button) itemView.findViewById(R.id.btnYes);
            this.btn1 = (Button) itemView.findViewById(R.id.btnno);


        }
    }

    public ExampleAdapter1(List<ExampleItem1> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle2, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem1 currentItem = (ExampleItem1) this.exampleList.get(position);
        holder.imageView.setImageBitmap(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username= String.valueOf(currentItem.getmText2());
                String email=String.valueOf(currentItem.getmText4());

                String url = UrlLinks.verifyuser;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("email", email));



                String result = null;
                try {
                    result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result.equals("success")) {

                    Toast.makeText(mContext, "The user's identity was successfully verified.", Toast.LENGTH_SHORT).show();


                }else {

                    Toast.makeText(mContext, "User not successfully verified", Toast.LENGTH_SHORT).show();

                }
            }




        });


        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username= String.valueOf(currentItem.getmText2());
                String email=String.valueOf(currentItem.getmText4());
                String url = UrlLinks.rejectuser;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("username", username));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                String result = null;
                try {
                    result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (result.equals("success")) {

                    Toast.makeText(mContext, "The user's rejection was successfully verified.", Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(mContext, "please try again", Toast.LENGTH_SHORT).show();

                }
            }




        });
    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(List<ExampleItem1> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}