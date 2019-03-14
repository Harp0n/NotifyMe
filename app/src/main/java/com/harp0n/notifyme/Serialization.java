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
        result.add(new Notify(37.421983, -122.084065, 50, "Google", "budynek google"));
        result.add(new Notify(37.422221, -122.086585, 100, "Rzeka", "rzeka obok google"));
        return result;
    }
}
