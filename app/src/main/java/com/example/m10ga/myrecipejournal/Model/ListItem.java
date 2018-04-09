package com.example.m10ga.myrecipejournal.Model;

/**
 * Created by m10ga on 2018-04-08.
 */

public class ListItem {

    private String url;
    private String recipe_name;
    private String preparation_time;
    private String cooking_time;
    private String ingredients;
    private String preparation_steps;


    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    private String people;

    public ListItem(){}
    public ListItem(String url, String cooking_time, String recipename, String preparation_time, String people_served){
        this.url = url;
        this.cooking_time = cooking_time;
//        this.recipename = recipename;
        this.preparation_time = preparation_time;
//        this.people_served = people_served;

    }





    public String getUrl(){return url;}
    public void setUrl(String url){this.url=url;}

    public String getCooking_time() {
        return cooking_time;
    }

    public void setCooking_time(String cooking_time) {
        this.cooking_time = cooking_time;
    }




    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    public String getIngredients(){return ingredients;}
    public void setIngredients(String ingredients){this.ingredients=ingredients;}

    public String getPreparation_steps(){return preparation_steps;}
    public void setPreparation_steps(String preparation_steps){this.preparation_steps=preparation_steps;}

}
