package com.baecon.blood_blank;

import android.graphics.Bitmap;

public class ExampleItem1 {

    private Bitmap mImageResource;

    private String mText2;
    private String mText3;
    private String mText4;

    public ExampleItem1(Bitmap imageResource, String mText2, String mText3, String mText4) {
        this.mImageResource = imageResource;
        this.mText2 = mText2;
        this.mText3 = mText3;
        this.mText4 = mText4;
    }

    public Bitmap getmText1() {return mImageResource;}

    public void setmImageResource(Bitmap mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public String getmText3() {
        return mText3;
    }

    public void setmText3(String mText3) {
        this.mText3 = mText3;
    }

    public String getmText4() {
        return mText4;
    }

    public void setmText4(String mText4) {
        this.mText4 = mText4;
    }


}