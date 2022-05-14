package Model;

public class Dish {

    public int dish_id;
    public String name;

    public boolean first_dish;
    public boolean second_dish;
    public boolean bakery;
    public boolean garnish;
    public boolean desert;
    public boolean snack;    // только завтрак
    public boolean salad;
    public boolean nuts;
    public boolean citrus;
    public boolean gluten;
    public boolean lactose;

    public float cal;
    public float prot;
    public float fat;
    public float carb;

    public Dish(int dish_id, String name, boolean first_dish, boolean second_dish, boolean bakery, boolean garnish, boolean desert,
                boolean snack, boolean salad, boolean nuts, boolean citrus, boolean gluten, boolean lactose, float cal,
                float prot, float fat, float carb){

        this.dish_id = dish_id;
        this.name = name;
        this.first_dish = first_dish;
        this.second_dish = second_dish;
        this.bakery = bakery;
        this.garnish = garnish;
        this.desert = desert;
        this.snack = snack;
        this.salad = salad;
        this.nuts = nuts;
        this.citrus = citrus;
        this.gluten = gluten;
        this.lactose = lactose;
        this.cal = cal;
        this.prot = prot;
        this.fat = fat;
        this.carb = carb;
    }

    public String getCategory(){
        if(this.first_dish){return "first_dish";}
        else if(this.second_dish){return "second_dish";}
        else if(this.bakery){return "bakery";}
        else if(this.garnish){return "garnish";}
        else if(this.desert){return "desert";}
        else if(this.snack){return "snack";}
        else if(this.salad){return "salad";}
        else{return "NO CATEGORY";}
    }

}
