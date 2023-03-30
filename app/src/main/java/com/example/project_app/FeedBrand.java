package com.example.project_app;

// марка корма
enum FeedBrand {
    SC_1,
    SC_2,
    SC_3,
    SC_4,
    SC_5,
    SC_6,
    SC_7,
    SC_8;

    public static String getString(FeedBrand feedBrand) {
        int value = 0;
        switch (feedBrand) {
            case SC_1:
                value = 1; break;
            case SC_2:
                value = 2; break;
            case SC_3:
                value = 3; break;
            case SC_4:
                value = 4; break;
            case SC_5:
                value = 5; break;
            case SC_6:
                value = 6; break;
            case SC_7:
                value = 7; break;
            case SC_8:
                value = 8; break;
        }
        return "СК-" + value;
    }

    public static int getIndex(FeedBrand feedBrand) {
        int value = 0;
        switch (feedBrand) {
            case SC_1:
               return 0;
            case SC_2:
                return 1;
            case SC_3:
                return 2;
            case SC_4:
                return 3;
            case SC_5:
                return 4;
            case SC_6:
                return 5;
            case SC_7:
                return 6;
            case SC_8:
                return 7;
            default:
                return 8;
        }
    }

    public static FeedBrand getFeedBrand(String text) {
        int value = text.charAt(3) - '0';

        switch (value) {
            case 1:
                return SC_1;
            case 2:
                return SC_2;
            case 3:
                return SC_3;
            case 4:
                return SC_4;
            case 5:
                return SC_5;
            case 6:
                return SC_6;
            case 7:
                return SC_7;
            case 8:
                return SC_8;
            default:
                return SC_1;
        }
    }
}
