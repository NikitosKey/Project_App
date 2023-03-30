package com.example.project_app;


import java.util.Calendar;
import java.util.Vector;


// база данных
public class LocalDataBase<bunkerListVector> {
    private static int sizeBunkerArr;                   // размер списка бункеров
    private static Vector<BunkerList> bunkerListVector; // вектор списка бункеров
    private static String userName;                     // имя пользователя
    private static Calendar dateCurrent;                // текущая выбранная дата
    private static TypeUser typeUser;                   // тип пользователя

    public static void init() {
        sizeBunkerArr = 40;
        bunkerListVector = new Vector<BunkerList>(0);
        userName = "";
        dateCurrent = Calendar.getInstance();
        typeUser = TypeUser.VISITOR;
    }

    private static String getNameMonth(int i) {
        switch (i) {
            case 1: return "января";
            case 2: return "февраля";
            case 3: return "марта";
            case 4: return "апеля";
            case 5: return "мая";
            case 6: return "июня";
            case 7: return "июля";
            case 8: return "августа";
            case 9: return "сентября";
            case 10: return "октября";
            case 11: return "ноября";
            case 12: return "декабря";
            default:
                return "none";
        }
    }

    // возвращает строковое представление даты
    public static String getStringDate(Calendar date) {
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        //int month_value = date.getMonth() + 1;
        //String month = month_value < 10 ? String.valueOf("0" + month_value) : String.valueOf(month_value);
        String month = getNameMonth(date.get(Calendar.MONTH) + 1);
        String year = String.valueOf(date.get(Calendar.YEAR));

        return day + " " + month + " " + year + " г.";
    }
    // возвращает строковое представление даты
    public static String getStringDateFB(Calendar date) {
        String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
        //int month_value = date.getMonth() + 1;
        //String month = month_value < 10 ? String.valueOf("0" + month_value) : String.valueOf(month_value);
        String month = String.valueOf(date.get(Calendar.MONTH) + 1);
        String year = String.valueOf(date.get(Calendar.YEAR));

        return day + "-" + month + "-" + year;
    }

    // перевод строкового представления даты в Date
    /*public static boolean stringToDate(String text, Date date) {
        String[] arr = text.split(Pattern.quote("."));
        if (arr.length != 3)
            return false;

        int year = Integer.parseInt(arr[2]);
        if (year < 1900)
            return false;

        int month = Integer.parseInt(arr[1]);
        if (month < 1 || month > 12)
            return false;

        int day = Integer.parseInt(arr[0]);
        // если месяц укороченный
        if (month == 4 || month == 6|| month == 9 || month == 11) {
            if (day < 1 || day > 30)
                return false;
        }
        // если февраль
        else if (month == 2) {
            int t = 28;
            if (year % 4 == 0 && year % 400 != 0) // если год високосный
                t = 29;
            if (day < 1 || day > t)
                return false;
        }
        else if (day < 1 || day > 31)
                return false;

        date.setDate(day);
        date.setMonth(month - 1);
        date.setYear(year - 1900);
        return true;
    }*/

    // добавить список бункеров bunkerList
    public static boolean addBunkerList(BunkerList bunkerList) {
        // если количество бункеров не равно
        if (bunkerList.getBunkerArrayListSize() != LocalDataBase.getSizeBunkerArr()) {
            return false;
        }
        Calendar date = bunkerList.getDate();

        // создаем упорядоченный по возрастанию вектор по датам
        int i = 0;
        while (i < LocalDataBase.bunkerListVector.size()) {
            Calendar date_add = bunkerList.getDate();
            Calendar date_current = LocalDataBase.bunkerListVector.get(i).getDate();

            // если найдена такая же дата, то заменим элемент на новый
            if (date_add.equals(date_current)) {
                LocalDataBase.bunkerListVector.set(i, bunkerList);
                return true;
            }
            // если добавляемая дата меньше текущей в цикле
            if (date_add.before(date_current))
                break;
            i++;
        }
        LocalDataBase.bunkerListVector.add(i, bunkerList);
        return true;
    }

    // добавить список бункеров bunkerList от заказчкка
    public static boolean addBunkerListCustomer(BunkerList bunkerList) {
        // если количество бункеров не равно
        if (bunkerList.getBunkerArrayListSize() != LocalDataBase.getSizeBunkerArr()) {
            return false;
        }
        Calendar date = bunkerList.getDate();

        // создаем упорядоченный по возрастанию вектор по датам
        int i = 0;
        while (i < LocalDataBase.bunkerListVector.size()) {
            Calendar date_add = bunkerList.getDate();
            Calendar date_current = LocalDataBase.bunkerListVector.get(i).getDate();

            // если найдена такая же дата, то заменим элемент на новый
            if (date_add.equals(date_current)) {
                BunkerList tmpListDB = LocalDataBase.bunkerListVector.get(i);
                // поменяем параметры, которые может изменять заказчик
                for (int j = 0; j < tmpListDB.getBunkerArrayListSize(); j++) {
                    tmpListDB.getList().get(j).setFeedBrand( bunkerList.getList().get(j).getFeedBrand() );
                    tmpListDB.getList().get(j).setPlan( bunkerList.getList().get(j).getPlan() );
                }
                return true;
            }
            // если добавляемая дата меньше текущей в цикле
            if (date_add.before(date_current))
                break;
            i++;
        }
        LocalDataBase.bunkerListVector.add(i, bunkerList);
        return true;
    }

    public static boolean dateIsEqual(Calendar date_1, Calendar date_2) {
        return  date_1.get(Calendar.YEAR) == date_2.get(Calendar.YEAR)
                && date_1.get(Calendar.MONTH) == date_2.get(Calendar.MONTH)
                && date_1.get(Calendar.DAY_OF_MONTH) == date_2.get(Calendar.DAY_OF_MONTH);
    }

    // добавить список бункеров bunkerList от водителя
    public static boolean addBunkerListDriver(BunkerList bunkerList) {
        // если количество бункеров не равно
        if (bunkerList.getBunkerArrayListSize() != LocalDataBase.getSizeBunkerArr()) {
            return false;
        }
        Calendar date = bunkerList.getDate();

        // создаем упорядоченный по возрастанию вектор по датам
        int i = 0;
        while (i < LocalDataBase.bunkerListVector.size()) {
            Calendar date_add = bunkerList.getDate();
            Calendar date_current = LocalDataBase.bunkerListVector.get(i).getDate();

            // если найдена такая же дата, то заменим элемент на новый
            if (dateIsEqual(date_add, date_current)) {
                BunkerList tmpListDB = LocalDataBase.bunkerListVector.get(i);
                // прибавим к имеющемуся факту в базе данных факт водителя
                for (int j = 0; j < tmpListDB.getBunkerArrayListSize(); j++) {
                    float fact = tmpListDB.getList().get(j).getFact() + bunkerList.getList().get(j).getFact();
                    tmpListDB.getList().get(j).setFact(fact);

                    // если факт водителя не нулевой, добавим его имя
                    if (bunkerList.getList().get(j).getFact() != 0.0f)
                        tmpListDB.getList().get(j).setNameDriver( LocalDataBase.getUserName() );
                }
                return true;
            }
            // если добавляемая дата меньше текущей в цикле
            if (date_add.before(date_current))
                break;
            i++;
        }

        // занесем в каждую строку имя водителя, где было прописано ненулевое значние факта
        for (int j = 0; j < bunkerList.getBunkerArrayListSize(); j++) {
            // если факт водителя не нулевой, добавем его имя
            if (bunkerList.getList().get(j).getFact() != 0.0f)
                bunkerList.getList().get(j).setNameDriver( LocalDataBase.getUserName() );
        }

        LocalDataBase.bunkerListVector.add(i, bunkerList);
        return true;
    }

    // получить запись в BunkerList список бункеров по дате, установленной в нем
    public static boolean getBunkerListForDate(BunkerList bunkerList) {
        for (BunkerList obj : LocalDataBase.bunkerListVector) {
            // если даты равны
            if (obj.getDate().compareTo(bunkerList.getDate()) == 0) {
                // копирование массива информции о бункерах
                bunkerList.setList(obj.getList());
                return true;
            }
        }
        return false;
    }

    // удалить информацию о бункерах по дате date
    public static boolean deleteBunkerListForDate(Calendar date) {
        for (int i = 0; i < LocalDataBase.bunkerListVector.size(); i++) {
            if (LocalDataBase.bunkerListVector.get(i).getDate().compareTo(date) == 0) {
                LocalDataBase.bunkerListVector.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void setUserName(String userName) {
        LocalDataBase.userName = userName;
    }

    public static void setSizeBunkerArr(int size) {
        if (size < 0)
            return;

        LocalDataBase.sizeBunkerArr = size;
        for (BunkerList obj : LocalDataBase.bunkerListVector)
            obj.setSize(size);
    }

    public static String getUserName() { return LocalDataBase.userName; }

    public static Vector<BunkerList> getBunkerListVector() { return LocalDataBase.bunkerListVector; }

    public static int getSizeBunkerArr() { return LocalDataBase.sizeBunkerArr; }

    public static Calendar getDateCurrent() {
        return dateCurrent;
    }

    public static void setSizeBunkerArr (Integer sizeBunkerArr) {
        LocalDataBase.sizeBunkerArr = sizeBunkerArr;
    }

    public static void setBunkerList (BunkerList bunkerList) {
        LocalDataBase.sizeBunkerArr = sizeBunkerArr;
    }

    public static void setDateCurrent(Calendar dateCurrent) {
        LocalDataBase.dateCurrent = dateCurrent;
    }

    public static TypeUser getTypeUser() {
        return typeUser;
    }

    public static void setTypeUser(TypeUser typeUser) {
        LocalDataBase.typeUser = typeUser;
    }


}

