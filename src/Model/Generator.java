package Model;

import java.util.*;

public class Generator {

    public DAO db;
    public HashMap<String, ArrayList<Float>> portions;
    public float calories;

    public Generator(Query query){
        db = new DAO();
        calories = countCalories(query);
        portions = new HashMap<>();
        {
            portions.put("first_dish", new ArrayList<>(Arrays.asList(2f, 2.5f, 3f)));
            portions.put("second_dish", new ArrayList<>(Arrays.asList(1.5f, 1.75f, 2.f)));
            portions.put("bakery", new ArrayList<>(Arrays.asList(1f, 1.25f, 1.5f)));
            portions.put("garnish", new ArrayList<>(Arrays.asList(1f, 1.5f, 2f)));
            portions.put("desert", new ArrayList<>(Arrays.asList(1f, 1.25f, 1.5f)));
            portions.put("snack", new ArrayList<>(Arrays.asList(1f, 1.25f, 1.5f)));
            portions.put("salad", new ArrayList<>(Arrays.asList(1f, 1.5f, 2f)));
        }
    }

    public int countCalories(Query query){
        float height = query.height;
        float weight = query.weight;
        int age = query.age;
        float[] activity = {1.2f, 1.375f, 1.55f, 1.7f, 1.9f};
        int cal = 0;
        float coefficient = activity[Integer.parseInt(query.activity) - 1];
        if(query.sex) { //male
            cal = (int)Math.round((10 * weight + 6.25 * height - 5 * age + 5) * coefficient);
        }else{
            cal = (int)Math.round((10 * weight + 6.25 * height - 5 * age - 161) * coefficient);
        }
        return cal;
    }

    public String generateSQL(Query query){

        //SELECT * FROM dish WHERE lactose=false AND gluten=false AND cal < 200;

        StringBuilder sql = new StringBuilder("SELECT * FROM dish WHERE ");     //отбор по форме
        if (query.form.equals("looseWeight")){
            sql.append("(carb < 15 AND fat < 19)");
        }
        else if(query.form.equals("keepWeight")){
            sql.append("(carb > 0)");
        }
        else if(query.form.equals("gainWeight")){
            sql.append("(prot > 1.5 OR carb > 8)");
        }

        if(query.allergy.contains("nuts")){     //отбор по аллергиям
            sql.append(" AND (nuts=false)");
        }
        if(query.allergy.contains("citrus")){
            sql.append(" AND (citrus=false)");
        }
        if(query.allergy.contains("gluten")){
            sql.append(" AND (gluten=false)");
        }
        if(query.allergy.contains("lactose")){
            sql.append(" AND (lactose=false)");
        }

        return sql.toString();
    }

    //для каждого мила исключить некоторые категории
    //в рамках мила категории не должны повторяться

    public ArrayList<String> generateBreakfast(Query query){  //bakery snack garnish

        float cal = calories * 0.25f;
        String sql = generateSQL(query) + " AND (bakery = true OR snack = true OR garnish = true);";
        HashSet<Dish> affordable = db.getSQL(sql);
        ArrayList<Dish> dishes = new ArrayList<>(affordable);

        ArrayList<String> dishNames = new ArrayList<>();
        float curCalories = 0;
        if(query.form.equals("looseWeight")){
            while(curCalories < (cal - (cal * 0.2))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.05){  //если превысилось на 5% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("keepWeight")){
            while(curCalories < (cal - (cal * 0.05))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.15){  //если превысилось на 15% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("gainWeight")){
            while(curCalories < (cal + (cal * 0.2))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;
            }
        }
        dishNames.add(String.valueOf(curCalories));
        return dishNames;
    }


    public ArrayList<String> generateLunch(Query query){     //salad snack

        float cal = calories * 0.1f;    //10% от дневных калорий
        String sql = generateSQL(query) + " AND (salad = true OR snack = true);";
        HashSet<Dish> affordable = db.getSQL(sql);
        ArrayList<Dish> dishes = new ArrayList<>(affordable);

        ArrayList<String> dishNames = new ArrayList<>();
        float curCalories = 0;
        if(query.form.equals("looseWeight")){
            while(curCalories < (cal - (cal * 0.3))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.1){  //если превысилось на 5% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("keepWeight")){
            while(curCalories < (cal - (cal * 0.1))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.15){  //если превысилось на 15% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("gainWeight")){
            while(curCalories < (cal + (cal * 0.2))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;
            }
        }
        dishNames.add(String.valueOf(curCalories));
        return dishNames;
    }


    public ArrayList<String> generateDinner(Query query){    //first_dish second_dish garnish desert salad

        float cal = calories * 0.4f;    //40% от дневных калорий
        String sql = generateSQL(query) + " AND (first_dish = true OR second_dish = true OR garnish = true OR desert = true or salad = true);";
        HashSet<Dish> affordable = db.getSQL(sql);
        ArrayList<Dish> dishes = new ArrayList<>(affordable);

        ArrayList<String> dishNames = new ArrayList<>();
        float curCalories = 0;
        if(query.form.equals("looseWeight")){
            while(curCalories < (cal - (cal * 0.2))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal){
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("keepWeight")){
            while(curCalories < (cal - (cal * 0.07))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.07){  //если превысилось на 15% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("gainWeight")){
            while(curCalories < (cal + (cal * 0.1))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;
            }
        }
        dishNames.add(String.valueOf(curCalories));
        return dishNames;
    }


    public ArrayList<String> generateSupper(Query query){    //first_dish second_dish garnish snack salad

        float cal = calories * 0.25f;
        String sql = generateSQL(query) + " AND (first_dish = true OR second_dish = true OR garnish = true OR snack = true OR salad = true);";
        HashSet<Dish> affordable = db.getSQL(sql);
        ArrayList<Dish> dishes = new ArrayList<>(affordable);

        ArrayList<String> dishNames = new ArrayList<>();
        float curCalories = 0;
        if(query.form.equals("looseWeight")){
            while(curCalories < (cal - (cal * 0.2))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.05){  //если превысилось на 5% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("keepWeight")){
            while(curCalories < (cal - (cal * 0.05))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                float prevCalories = curCalories;
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;

                if(curCalories > cal * 1.15){  //если превысилось на 15% выбираем другое блюдо
                    dishNames.remove(dishNames.size() - 1);
                    curCalories = prevCalories;
                }
            }
        }
        else if(query.form.equals("gainWeight")){
            while(curCalories < (cal + (cal * 0.1))) {
                int index = rnd(dishes.size() - 1);
                float curDishPortion =portions.get(dishes.get(index).getCategory()).get(rnd(2));
                dishNames.add(dishes.get(index).name + " " + curDishPortion * 100 + "г");
                curCalories = curCalories + curDishPortion * dishes.get(index).cal;
            }
        }
        dishNames.add(String.valueOf(curCalories));
        return dishNames;
    }

    public int rnd(int max) { return (int) (Math.random() * ++max); }
}
