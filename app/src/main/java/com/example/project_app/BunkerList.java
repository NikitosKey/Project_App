package com.example.project_app;

import java.util.ArrayList;
import java.util.Calendar;

// список бункеров на определенную дату
public class BunkerList extends ArrayList<Bunker> {
    public ArrayList<Bunker> bunkerArrayList;    // вектор бункеров
    public Calendar date;                  // дата
    public int size;

    public BunkerList() {
    }

    public BunkerList(ArrayList<Bunker> bunkerArrayList, Calendar date) {
        this.bunkerArrayList = bunkerArrayList;
        this.date = date;
    }

    public BunkerList(int size) {
        bunkerArrayList = new ArrayList<Bunker>(size);
        for (int i = 0; i < size; i++)
            bunkerArrayList.add(new Bunker());

        date = Calendar.getInstance();
    }

    public void setSize(int size) {
        int sizePrev = bunkerArrayList.size();
        bunkerArrayList.ensureCapacity(size);

        if (size > sizePrev) {
            for (int i = sizePrev; i < size; i++)
                bunkerArrayList.add(i, new Bunker());
        }
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setList(ArrayList<Bunker> bunkerArrayList) {this.bunkerArrayList = bunkerArrayList;}

    public void setElem(int i, Bunker bunker) {
        bunkerArrayList.set(i, bunker);
    }

    public int getBunkerArrayListSize() {
        return bunkerArrayList.size();
    }

    public Calendar getDate() {return date;}

    public ArrayList<Bunker> getList() {
        return bunkerArrayList;
    }

    public void setBunkerList(ArrayList<Bunker> bunkerArrayList, Calendar date) {
        this.bunkerArrayList = bunkerArrayList;
        this.date = date;
    }

}
