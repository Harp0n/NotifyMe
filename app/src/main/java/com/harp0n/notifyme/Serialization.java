package com.harp0n.notifyme;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;

import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

public class Serialization extends Main_Activity{

    // wczytuje dane z storage.json i oddaje je w postaci stinga
    private static String read(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }
    // tworzy nowy plik storage.json
    private static boolean create(Context context, String fileName, String jsonString){
        String FILENAME = "storage.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }
    // sprawdza czy plik z danymi już istnieje u usera
    public static boolean isFilePresent(Context context, String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }


    public static void save(Notify notifyObj, Context context) {
        JSONObject dataJObject = new JSONObject();
        //JSONParser parser = new JSONParser();
        Gson gson = new Gson();
        //przypisanie do obiektu json danych wprowadzonych przez usera w celu zapisania ich
        try{
            String json = gson.toJson(notifyObj);
            System.out.println(json);
            dataJObject = new JSONObject(json);
           /* dataJObject.put("ID",notifyObj.getID());
            dataJObject.put(" x_coordinate=",notifyObj.getX_coordinate());
            dataJObject.put(" y_coordinate=",notifyObj.getY_coordinate());
            dataJObject.put(" radius=",notifyObj.getRadius());
            dataJObject.put("name=", notifyObj.getName());
            dataJObject.put("description=", notifyObj.getDescription());
            dataJObject.put("notificationMessage=",notifyObj.getNotificationMessage());
            dataJObject.put("isOneTime=",notifyObj.isOneTime());
            dataJObject.put("volumeChangeOn=",notifyObj.isVolumeChangeOn());
            dataJObject.put("soundVolume=",notifyObj.getSoundVolume());
            dataJObject.put("wifiChangeOn=",notifyObj.isWifiChangeOn());
            dataJObject.put("wifiIsOn=",notifyObj.isWifiChangeOn());
            dataJObject.put("bluetoothChangeOn=",notifyObj.isBluetoothChangeOn());
            dataJObject.put("bluetoothIsOn=",notifyObj.isBluetoothIsOn());
            dataJObject.put("phoneDataChangeOn=",notifyObj.isPhoneDataChangeOn());
            dataJObject.put("phoneDataIsOn=",notifyObj.isPhoneDataIsOn());
            dataJObject.put("planeModeChangeOn=",notifyObj.isPlaneModeChangeOn());
            dataJObject.put("planeModeIsOn=",notifyObj.isPlaneModeIsOn());
            dataJObject.put("alarmSoundChangeOn=",notifyObj.isAlarmSoundChangeOn());
            dataJObject.put("alarmSoundIsOn=",notifyObj.isAlarmSoundIsOn());*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // sprawdzanie czy plik do zapisu danych istnieje w pamięci telefonu, jeśli nie to go tworzy
        boolean isFilePresent = isFilePresent(context, "storage.json");
        if(!isFilePresent) {
            boolean isFileCreated = create(context, "storage.json", "{}");
        }
        // wczytywanie danych z pliku
        String jsonString = read(context, "storage.json");
        //objDataArray = new JSONArray(jsonString);

        try{
            // dodanie obiektu do JSONarray
            JSONArray dataJArray;
            //jesli nie ma zadnych problemow
            if (jsonString != null && jsonString.length() != 0 && !jsonString.equals("{}"))
                dataJArray = new JSONArray(jsonString);
            else
                dataJArray = new JSONArray();
            dataJArray.put(dataJObject);
            // zapisywanie do pliku
            Writer output;
            File file = new File(context.getFilesDir().getAbsolutePath() + "/storage.json");
            output = new BufferedWriter(new FileWriter(file));

            // pobranie id ostatniego obiektu z pliku
            //ID = objDataArray.getJSONObject(objDataArray.length()-1).getInt("ID") + 1;
            //objJSONObject.put("ID",ID);
            //objDataArray.put(objJSONObject);

            // wpisanie do pliku storage.json tablicy
            output.write(dataJArray.toString());
            output.close();
            Toast.makeText(context, "Composition saved", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void edit(Notify notifyObj,Context context) {
        remove(notifyObj, context);
        save(notifyObj, context);
    }

    public static void remove(Notify notifyObj, Context context) {
        String jsonString = read(context, "storage.json");
        try{
            JSONArray array = new JSONArray(jsonString);
            for(int i = 0; i < array.length();i++) {
                // szuaknie obiektu po ID i usinięcie go
                if(array.getJSONObject(i).getInt("ID") == notifyObj.getID()) if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    array.remove(i);
                }
            }

            Writer output;
            File file = new File(context.getFilesDir().getAbsolutePath() + "/storage.json");
            output = new BufferedWriter(new FileWriter(file));
            output.write(array.toString());
            output.close();
            Toast.makeText(context, "Composition saved", Toast.LENGTH_LONG).show();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Notify> load(Context context){

        ArrayList<Notify> notifyArrayList = new ArrayList<>();
        Gson gson = new Gson();
        String jsonString = read(context, "storage.json");
        try {
            JSONArray loadJArray = new JSONArray(jsonString);

            // tworzenie obiektów Notify i wpisanie ich do notifyArrayList
            for (int i = 0; i < loadJArray.length(); i++) {
                try {
                    Notify notify = gson.fromJson(loadJArray.getJSONObject(i).toString(),Notify.class);
                    //notifyObj = mapper.readValue(loadJArray.getJSONObject(i).toString(),Notify.class);
                    /*String name = loadJArray.getJSONObject(i).getString("name=");
                    String description = loadJArray.getJSONObject(i).getString("description=");
                    String notificationMessage = loadJArray.getJSONObject(i).getString("notificationMessage=");
                    boolean isOneTime = loadJArray.getJSONObject(i).getBoolean("isOneTime=");
                    int soundVolume = loadJArray.getJSONObject(i).getInt("soundVolume=");
                    notifyObj = new Notify(name, description, notificationMessage, isOneTime, soundVolume);
                    notifyObj.setID(loadJArray.getJSONObject(i).getInt("ID"));
                    notifyObj.setVolumeChangeOn(loadJArray.getJSONObject(i).getBoolean("volumeChangeOn="));
                    notifyObj.setWifiChangeOn(loadJArray.getJSONObject(i).getBoolean("wifiChangeOn="));
                    notifyObj.setBluetoothChangeOn(loadJArray.getJSONObject(i).getBoolean("bluetoothChangeOn="));
                    notifyObj.setAlarmSoundChangeOn(loadJArray.getJSONObject(i).getBoolean("alarmSoundOn="));
                    notifyObj.setWifiIsOn(loadJArray.getJSONObject(i).getBoolean("wifiIsOn="));
                    notifyObj.setBluetoothIsOn(loadJArray.getJSONObject(i).getBoolean("bluetoothIsOn="));
*/
                    notifyArrayList.add(notify);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return notifyArrayList;
    }
}
