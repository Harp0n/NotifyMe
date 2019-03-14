package com.harp0n.notifyme;

import java.util.ArrayList;

public class Serialization {

    public static void save() {

    }

    public static void edit() {

    }

    public static void remove() {

    }

    public static ArrayList<Notify> load(){
        ArrayList<Notify> result = new ArrayList<Notify>();
        //result.add(new Notify());
        result.add(new Notify(
                37.422221, -122.086585, 100.0,
                "Rzeka", "rzeka obok google", "zdejmij buty",
                true, 100,
                true, true,
                true, true,
                true,
                false
        ));
        result.add(new Notify(
                37.421994, -122.084095, 50.0,
                "Google", "budynek google", "zadzwon do mamy",
                false, 0,
                true, false,
                true, false,
                false,
                false
        ));
        return result;
    }
}
