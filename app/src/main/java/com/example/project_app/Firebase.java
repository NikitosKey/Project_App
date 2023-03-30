package com.example.project_app;

import static com.example.project_app.LocalDataBase.getDateCurrent;
import static com.example.project_app.LocalDataBase.getStringDateFB;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Objects;

public class Firebase {
    public static DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    // Получаем ссылку на путь с ключом "Date"
    public static    String Time = getStringDateFB(getDateCurrent());
    public static    String path_to_date = "DataBase/" + Time;
    public static    DatabaseReference DataReference = reference.child(path_to_date);



    public interface OnDataLoadedListener {
        void onDataLoaded(BunkerList bunkerList);
    }
    public static void FB_Read(int SizeBunkerArr, OnDataLoadedListener onDataLoadedListener) {
        BunkerList bunkerList = new BunkerList(SizeBunkerArr);
        // Проверяем наличие элемента с ключом "Date"
        Log.e("TAG", "Обращаемся к БД");
        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
        //@Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            //Если не пусто, то производим чтение данных из FB
            if (snapshot.exists()) {
                Log.e("Чтение", "Обращаемся к БД: ");

                if (snapshot.child("date").exists() && snapshot.child("vector").exists()) {
                    String date = snapshot.child("date").getValue(String.class);
                    Object array = snapshot.child("vector").getValue(Object.class);
                    ArrayList<HashMap<String, Object>> HashMapArrayList = (ArrayList<HashMap<String, Object>>) array;
                    ArrayList<Bunker> bunkerArrayList = new ArrayList<Bunker>();
                    for(int i = 0; i < HashMapArrayList.size(); i++) {
                        HashMap<String, Object> Map = HashMapArrayList.get(i);
                        Bunker bunker = new Bunker(Map);
                        bunkerArrayList.add(bunker);;
                    }
                    assert (array != null);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = new GregorianCalendar();
                    try {
                        assert date != null;
                        cal.setTime(Objects.requireNonNull(format.parse(date)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    bunkerList.setBunkerList(bunkerArrayList,cal);
                    Log.w("Чтение", "Успех");
                }
            } else {
                    Log.e("FBReading", "Snapshot doesn't exist");
                }
            onDataLoadedListener.onDataLoaded(bunkerList);
        }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }

    public static void FB_Read_CS(int SizeBunkerArr, OnDataLoadedListener onDataLoadedListener) {
        BunkerList bunkerList = new BunkerList(SizeBunkerArr);
        // Проверяем наличие элемента с ключом "Date"
        Log.e("TAG", "Обращаемся к БД");
        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Если не пусто, то производим чтение данных из FB
                if (snapshot.exists()) {
                    Log.e("Чтение", "Обращаемся к БД: ");

                    if (snapshot.child("date").exists() && snapshot.child("StatisticArray").exists()) {
                        //Считываем данные
                        String date = snapshot.child("date").getValue(String.class);
                        //FB не поддерживает классы Bunker и BunkerList, поэтому заносим их как Object
                        Object array = snapshot.child("StatisticArray").getValue(Object.class);
                        //Преобразуем все данный в нужный тип
                        ArrayList<HashMap<String, Object>> HashMapArrayList = (ArrayList<HashMap<String, Object>>) array; //Промежуточный массив ассоциативных массивов
                        ArrayList<Bunker> bunkerArrayList = new ArrayList<>(); //Целевой массив бункеров
                        for(int i = 0; i < HashMapArrayList.size(); i++) {
                            HashMap<String, Object> Map = HashMapArrayList.get(i); //Промежуточный HashMap
                            Bunker bunker = new Bunker(Map); //Целевой Bunker
                            bunkerArrayList.add(bunker);
                        }
                        assert (array != null);
                        //Преобразуем строковое представление даты в класс Date
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Calendar cal = new GregorianCalendar();
                        try {
                            assert date != null;
                            cal.setTime(Objects.requireNonNull(format.parse(date)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bunkerList.setBunkerList(bunkerArrayList,cal);
                        Log.w("Чтение", "Успех");
                    } else {
                        BunkerList bunkerList = new BunkerList();
                    }
                } else {
                    Log.e("FBReading", "Snapshot doesn't exist");
                }
                onDataLoadedListener.onDataLoaded(bunkerList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }

    public static void FB_Read_D(int SizeBunkerArr, OnDataLoadedListener onDataLoadedListener) {
        BunkerList bunkerList = new BunkerList(SizeBunkerArr);
        // Проверяем наличие элемента с ключом "Date"
        Log.e("TAG", "Обращаемся к БД");
        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Если не пусто, то производим чтение данных из FB
                if (snapshot.exists()) {
                    Log.e("Чтение", "Обращаемся к БД: ");
                    if (snapshot.child("date").exists() && snapshot.child("DriverArray").exists()) {
                        //
                        String date = snapshot.child("date").getValue(String.class);
                        Object array = snapshot.child("DriverArray").getValue(Object.class);
                        ArrayList<HashMap<String, Object>> HashMapArrayList = (ArrayList<HashMap<String, Object>>) array;
                        ArrayList<Bunker> bunkerArrayList = new ArrayList<Bunker>();
                        for(int i = 0; i < HashMapArrayList.size(); i++) {
                            HashMap<String, Object> Map = HashMapArrayList.get(i);
                            Bunker bunker = new Bunker(Map);
                            bunkerArrayList.add(bunker);;
                        }
                        assert (array != null);

                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Calendar cal = new GregorianCalendar();
                        try {
                            assert date != null;
                            cal.setTime(Objects.requireNonNull(format.parse(date)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bunkerList.setBunkerList(bunkerArrayList,cal);
                        Log.w("Чтение", "Успех");
                    }
                } else {
                    Log.e("FBReading", "Snapshot doesn't exist");
                }
                onDataLoadedListener.onDataLoaded(bunkerList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }

    public static void FB_Write(BunkerList bunkerList, String date){
        // Проверяем наличие элемента с ключом "Date"

        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataReference.child("date").setValue(date);
                DataReference.child("vector").setValue(bunkerList.getList());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }
    public static void FB_Write_Driver(BunkerList bunkerList, String date, String DriverName){
        // Проверяем наличие элемента с ключом "Date"

        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataReference.child("date").setValue(date);

                ArrayList<Bunker> bunkerArrayList = bunkerList.getList();
                for (int i = 0; i < bunkerList.getBunkerArrayListSize(); i++){
                    String child_i = String.valueOf(i);
                    Bunker bunker =  bunkerArrayList.get(i);
                    Float d_Fact = bunker.getFact();
                    System.out.println(d_Fact);
                    Float pre_Fact = snapshot.child("StatisticArray").child(child_i).child("fact").getValue(Float.class);
                    Float Plan = snapshot.child("StatisticArray").child(child_i).child("plan").getValue(Float.class);
                    String feedBrand = snapshot.child("StatisticArray").child(child_i).child("feedBrand").getValue(String.class);
                    //String pre_Name = snapshot.child("DriverArray").child(child_i).child("fact").getValue(String.class);
                    Float Fact;
                    if (pre_Fact != null) {
                        float pre_FactValue = pre_Fact.floatValue();
                        Fact = pre_FactValue + d_Fact;
                    }else{
                        Fact = d_Fact;
                    }
                    Float Result;
                    if (Plan != null) {
                        float PlanValue = Plan.floatValue();
                        Result = PlanValue - Fact;
                        if (Result < 0) {
                            DataReference.child("DriverArray").child(child_i).child("plan").setValue(0);
                        }else{
                            DataReference.child("DriverArray").child(child_i).child("plan").setValue(Result);}
                    }
                    DataReference.child("StatisticArray").child(child_i).child("fact").setValue(Fact);
                    DataReference.child("DriverArray").child(child_i).child("fact").setValue(Fact);
                    //DataReference.child("DriverArray").child(child_i).child("nameDriver").setValue(DriverName);
                   // if(feedBrand != null) {
                        DataReference.child("DriverArray").child(child_i).child("feedBrand").setValue(feedBrand);
                    //}
                    if(d_Fact != 0) {
                        DataReference.child("StatisticArray").child(child_i).child("nameDriver").setValue(DriverName);
                    }
                }
                Log.w("Запись", "Успех");
                //System.out.print(bunkerList.getList());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }
    public static void FB_Write_Customer(BunkerList bunkerList, String date){
        // Проверяем наличие элемента с ключом "Date"

        DataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataReference.child("date").setValue(date);
                ArrayList<Bunker> bunkerArrayList = bunkerList.getList();
                for (int i = 0; i < bunkerList.getBunkerArrayListSize(); i++){
                    Bunker bunker =  bunkerArrayList.get(i);
                    String child_i = String.valueOf(i);
                    DataReference.child("StatisticArray").child(child_i).child("plan").setValue(bunker.getPlan());
                    DataReference.child("StatisticArray").child(child_i).child("feedBrand").setValue(bunker.getFeedBrand());
                    DataReference.child("DriverArray").child(child_i).child("plan").setValue(bunker.getPlan());
                    DataReference.child("DriverArray").child(child_i).child("feedBrand").setValue(bunker.getFeedBrand());
                    Log.w("Запись", "Успех");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Обработка ошибок
                Log.e("TAG", "Database error: " + error.toException().toString());
            }
        });
    }
}