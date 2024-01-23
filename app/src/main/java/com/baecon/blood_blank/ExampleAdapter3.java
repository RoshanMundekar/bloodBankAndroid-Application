package com.baecon.blood_blank;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.baecon.blood_blank.UrlLinks.urlserver;
import static com.baecon.blood_blank.UrlLinks.urlserverpython;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class ExampleAdapter3 extends RecyclerView.Adapter<ExampleAdapter3.ExampleViewHolder> {
    private List<ExampleItem3> exampleList;
    private List<ExampleItem3> exampleListFull;
    private Context mContext;

    class ExampleViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;

        Spinner reward;

        Button btn1;
        Button btn;
//        RelativeLayout parentLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);

            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.textView1 = (TextView) itemView.findViewById(R.id.HOSPITALNAME);
            this.textView2 = (TextView) itemView.findViewById(R.id.bloodroup);
            this.textView3 = (TextView) itemView.findViewById(R.id.status);
            this.textView4 = (TextView) itemView.findViewById(R.id.mail);
            this.textView5 = (TextView) itemView.findViewById(R.id.ratevalue);
            this.reward = (Spinner) itemView.findViewById(R.id.rewards);
            this.btn = (Button) itemView.findViewById(R.id.reward);

            this.btn1 = (Button) itemView.findViewById(R.id.download);
//
        }
    }

    public ExampleAdapter3(List<ExampleItem3> exampleList2, Context context) {
       // this.mContext = context;
        this.exampleList = exampleList2;
        this.exampleListFull = new ArrayList(exampleList2);
    }

    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // mContext = parent.getContext();
        return new ExampleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recycle4, parent, false));
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mContext = recyclerView.getContext();
    }
    
    public void onBindViewHolder(ExampleViewHolder holder, int position) {

        final ExampleItem3 currentItem = (ExampleItem3) this.exampleList.get(position);
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.textView3.setText(currentItem.getmText3());
        holder.textView4.setText(currentItem.getmText4());
        holder.textView5.setText(currentItem.getmText5());


        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(holder.reward.getSelectedItem().toString() == "select rewards $")  {
                  Toast.makeText(mContext, "Please select the proper rewards.", Toast.LENGTH_SHORT).show();
              }
              else {
                  String hospital = String.valueOf(currentItem.getmText1());
                  String mail = String.valueOf(currentItem.getmText4());
                  String reward = holder.reward.getSelectedItem().toString();
                  String url = UrlLinks.rewards;

                  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                  nameValuePairs.add(new BasicNameValuePair("hospital", hospital));
                  nameValuePairs.add(new BasicNameValuePair("mail", mail));
                  nameValuePairs.add(new BasicNameValuePair("reward", reward));

                  String result = null;
                  try {
                      result = jSOnClassforData.forCallingStringAndreturnSTring(url, nameValuePairs);
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

                  if (result.equals("success")) {

                      Toast.makeText(mContext, "Rewards sent to Donar successfully", Toast.LENGTH_SHORT).show();


                  }


              }
            }
        });


        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String url1 = urlserverpython+"csv/"+stud_name+"_file.csv";
                String url1 = urlserverpython+"static/certificates/"+currentItem.getmText6();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url1));
                String title = URLUtil.guessFileName(url1,null,null);
                request.setTitle(title);
                request.setDescription("Downloading ....");
                String cookie = CookieManager.getInstance().getCookie(url1);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);

                DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);


            }
        });


    }

    public int getItemCount() {
        return this.exampleList.size();
    }

    /* access modifiers changed from: 0000 */
    public void setFilter(List<ExampleItem3> filterdNames) {
        this.exampleList = filterdNames;
        notifyDataSetChanged();
    }
}