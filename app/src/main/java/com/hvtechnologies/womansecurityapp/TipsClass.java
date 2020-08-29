package com.hvtechnologies.womansecurityapp;

public class TipsClass {

    String Heading ;
    String Tip ;

    public TipsClass(String heading, String tip) {
        Heading = heading;
        Tip = tip;
    }


    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }
}
