package com.baecon.blood_blank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.baecon.blood_blank.ui.rewards.rewards;
import com.baecon.blood_blank.ui.showrewards.showrewards;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ExampleAdapter4 extends RecyclerView.Adapter<ExampleAdapter4.ExampleViewHolder> {
    private List<ExampleItem4> exampleList;
    private List<ExampleItem4> exampleListFull;
    private Context mContext;

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        Button btn;
        RatingBar rb;


//        RelativeLayout parentLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView1 = (TextView) itemView.findViewById(R.id.HOSPITALNAME);
            this.textView2 = (TextView) itemView.findViewById(R.id.bloodroup);
            this.textView3 = (TextView) itemView.findViewById(R.id.rewards);
            this.textView4 = (TextView) itemView.findViewById(R.id.mail);

            this.rb = (RatingBar) itemView.findViewById(R.id.rb);
            this. btn = (Button) itemView.findViewById(R.id.btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String data= String.valueOf(rb.getRating());
                    String username= String.valueOf(login.usersession);
                    String hospital= String.valueOf(showrewards.usersession1);

                    String url = UrlLinks.Rating;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

                    nameValuePairs.add(new BasicNameValuePair("username", username));
                    nameValuePairs.add(new BasicNameValuePair("Rate", data));
                    nameValuePairs.add(new BasicNameValuePair("hospital", hospital));

                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(mContext.getApplicationContext(), data+"star",Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(mContext, "please try again", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }

    public ExampleAdapter4(List<ExampleItem4> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle5, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem4 currentItem = (ExampleItem4) this.exampleList.get(position);
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());








    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(List<ExampleItem4> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}