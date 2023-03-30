package com.example.project_app;

import java.util.HashMap;

// данные о бункере
public class Bunker {
    private FeedBrand feedBrand;    // марка корма
    private float plan;             // план по корму
    private float fact;             // фактическое кол-во кг корма
    private String nameDriver;      // имя водителя, который ввел факт

    // конструктор по умолчанию
    public Bunker() {
        this.feedBrand = FeedBrand.SC_1;
        this.plan = 0.0f;
        this.fact = 0.0f;
        this.nameDriver = "";
    }

    public Bunker(HashMap<String, Object> Map) {
        String fB = (String) Map.get("feedBrand");
        this.setFeedBrand((FeedBrand.getFeedBrand(fB)));
        Number planNum = (Number) Map.get("plan");
        Number factNum = (Number) Map.get("fact");
        String nameDriver = (String) Map.get("nameDriver");
        this.setPlan(planNum == null ? 0.0f : planNum.floatValue());
        this.setFact(factNum == null ? 0.0f : factNum.floatValue());
        this.setNameDriver(nameDriver == null ? "" : nameDriver);
    }
    public Bunker(FeedBrand feedBrand, float plan, float fact, String nameDriver) {
        this.feedBrand = feedBrand;
        this.plan = plan;
        this.fact = fact;
        this.nameDriver = nameDriver;
    }

    public FeedBrand getFeedBrand() {
        return feedBrand;
    }

    public void setFeedBrand(FeedBrand feedBrand) {
        this.feedBrand = feedBrand;
    }

    public float getPlan() {
        return plan;
    }

    public void setPlan(float plan) {
        this.plan = plan;
    }

    public float getFact() {
        return fact;
    }

    public void setFact(float fact) {
        this.fact = fact;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

}
