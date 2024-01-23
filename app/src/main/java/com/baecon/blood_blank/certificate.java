package com.baecon.blood_blank;



import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baecon.blood_blank.ui.bloodrequirement.blood_reqirements;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class certificate extends AppCompatActivity {
    Button reg;

    EditText ed1,ed2 ,ed3,ed4;
    static String fileNameOfImage = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    String file_path = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        getSupportActionBar().hide();
        String hospital=getIntent().getStringExtra("hospital");
        String mail=getIntent().getStringExtra("mail");
        String bloodgroup =getIntent().getStringExtra("bloodgroup");

        reg = findViewById(R.id.button);
        ed1 = findViewById(R.id.input_fullName);
        ed2 = findViewById(R.id.bloode);
        ed3 = findViewById(R.id.input);
        ed4 = findViewById(R.id.mail);

        ed1.setText(hospital);
        ed2.setText(bloodgroup);
        ed4.setText(mail);

        ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        filePicker();
                    } else {
                        requestPermission();
                    }
                } else {
                    filePicker();
                }
            }


        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String hospital = ed1.getText().toString();
                String bloodgroup = ed2.getText().toString();
                String address = ed4.getText().toString();
                String upload = ed3.getText().toString();


                if(hospital.equals("") || bloodgroup.equals("")|| address.equals("")){
                    Toast.makeText(certificate.this, "Please fill details", Toast.LENGTH_SHORT).show();
                }

                else {
                    SaveImage(file_path);
                    String upload_imge = fileNameOfImage;
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String url = UrlLinks.uploadcertificates;

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);

                    nameValuePairs.add(new BasicNameValuePair("hospital", hospital));
                    nameValuePairs.add(new BasicNameValuePair("bloodgroup", bloodgroup));
                    nameValuePairs.add(new BasicNameValuePair("address", address));
                    nameValuePairs.add(new BasicNameValuePair("upload", upload_imge));
                    nameValuePairs.add(new BasicNameValuePair("usersession", login.usersession));


                    String result = null;
                    try {
                        result = jSOnClassforData.forCallingStringAndreturnSTring(url,nameValuePairs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (result.equals("success")) {

                        Toast.makeText(certificate.this, "your certificate uploads to the NGO successfully", Toast.LENGTH_SHORT).show();
                        Intent io;
                        io = new Intent(certificate.this, MainActivity.class);

                        startActivity(io);
                        finish();

                    } else {

                        Toast.makeText(certificate.this, "please retry ", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }



    private void SaveImage(String filepath) {

        // String root = Environment.getExternalStorageDirectory();
        // File file = new File(Environment.getExternalStorageDirectory(), "OTAPP/lm_lisdat_01.txt");
        // File myDir = new File(root + "/OTAPP");
        //  myDir.mkdirs();
        String username="a";
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        String fname = username+"-"+timeStamp+".jpg";
        ContextWrapper cw = new ContextWrapper(certificate.this);
        File directory = cw.getDir("OTAPP", Context.MODE_PRIVATE);
        if (!directory.exists ()){
            directory.mkdirs();

        }
        File file = new File(directory, fname);

        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            File file1 = new File(filepath);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath(),bmOptions);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fileNameOfImage=fname;
        new certificate.UploadFileAsync().execute("");

        Toast.makeText(cw, "certificate  uploaded sucessfully.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            String filePath = getRealPathFromUri(data.getData(),certificate.this);
            Log.d("File Path : ", " " + filePath);
            //now we will upload the file
            //lets import okhttp first
            this.file_path = filePath;
            ed3.setText(filePath);

        }
    }

    public String getRealPathFromUri(Uri uri, Activity activity){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor=activity.getContentResolver().query(uri,proj,null,null,null);
        if(cursor==null){
            return uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int id=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id);
        }
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(certificate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            return false;
        }
    }

    private void filePicker(){

        //.Now Permission Working
        Toast.makeText(certificate.this, "File Picker Call", Toast.LENGTH_SHORT).show();
        //Let's Pick File
        Intent opengallery=new Intent(Intent.ACTION_PICK);
        opengallery.setType("image/*");
        startActivityForResult(opengallery,REQUEST_GALLERY);


    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(certificate.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(certificate.this, "Please Give Permission to Upload File", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat.requestPermissions(certificate.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
        }
    }



    public class UploadFileAsync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                //  File file = new File (Environment.getExternalStorageDirectory(), "OTAPP//"+fileNameOfImage);
                String sourceFileUri =fileNameOfImage;// "/mnt/sdcard/abc.png";


                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                int bytesRead, bytesAvailable, bufferSize;
                byte[] buffer;
                int maxBufferSize = 1 * 1024 * 1024;
                //  File sourceFile = new File(sourceFileUri);
                File sourceFile = new File (Environment.getExternalStorageDirectory(), "OTAPP//"+fileNameOfImage);


                ContextWrapper cw = new ContextWrapper(certificate.this);
                File directory = cw.getDir("OTAPP", Context.MODE_PRIVATE);
                File file = new File(directory, fileNameOfImage);
                sourceFile=file;
                sourceFileUri=file.getAbsolutePath();
                if (sourceFile.isFile()) {

                    try {
                        String upLoadServerUri = UrlLinks.upload_image_certificate;

                        // open a URL connection to the Servlet
                        FileInputStream fileInputStream = new FileInputStream(
                                sourceFile);
                        URL url = new URL(upLoadServerUri);

                        // Open a HTTP connection to the URL
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // Allow Inputs
                        conn.setDoOutput(true); // Allow Outputs
                        conn.setUseCaches(false); // Don't use a Cached Copy
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Connection", "Keep-Alive");
                        conn.setRequestProperty("ENCTYPE",
                                "multipart/form-data");
                        conn.setRequestProperty("Content-Type",
                                "multipart/form-data;boundary=" + boundary);
                        conn.setRequestProperty("filename", sourceFileUri);
                        //conn.setRequestProperty("usern","username");
                        //  conn.addRequestProperty("usern","username");
                        // conn.set
                        dos = new DataOutputStream(conn.getOutputStream());

                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                        dos.writeBytes("Content-Disposition: form-data; name=\"filename\";filename=\""
                                + sourceFileUri + "\"" + lineEnd);

                        dos.writeBytes(lineEnd);

                        // create a buffer of maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {

                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math
                                    .min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0,
                                    bufferSize);

                        }

                        // send multipart form data necesssary after file
                        // data...
                        dos.writeBytes(lineEnd);
                        dos.writeBytes(twoHyphens + boundary + twoHyphens
                                + lineEnd);

                        // Responses from the server (code and message)
                        int serverResponseCode = conn.getResponseCode();

                        String serverResponseMessage = conn.getResponseMessage();

                        fileInputStream.close();
                        dos.flush();
                        dos.close();

                    } catch (Exception e) {

                        // dialog.dismiss();
                        e.printStackTrace();

                    }
                    // dialog.dismiss();

                } // End else block


            } catch (Exception ex) {
                // dialog.dismiss();

                ex.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }
}