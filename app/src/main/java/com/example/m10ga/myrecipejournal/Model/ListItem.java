package com.example.m10ga.myrecipejournal.Model;

/**
 * Created by m10ga on 2018-04-08.
 */

public class ListItem {

    private String url;
    private String cooking_time ;
    private String recipename;
    private String preparation_time;
    private String people_served;

    public ListItem(){}
    public ListItem(String url, String cooking_time, String recipename, String preparation_time, String people_served){
        this.url = url;
        this.cooking_time = cooking_time;
        this.recipename = recipename;
        this.preparation_time = preparation_time;
        this.people_served = people_served;

    }

    public String getPeople_served() {
        return people_served;
    }

    public void setPeople_served(String people_served) {
        this.people_served = people_served;
    }

    public String getUrl(){return url;}
    public void setUrl(String url){this.url=url;}

    public String getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(String cooking_time) {
        this.cooking_time = cooking_time;
    }

    public String getRecipename() {
        return recipename;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }
}
