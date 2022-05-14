package Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ListIterator;

public class Recommend {

    synchronized public String recommend(Query query){

	StringBuilder out = new StringBuilder();
        try {
            
            out.append("<div>Ваша суточная норма килокалорий:" + new Generator(query).calories + "</div>");

            if(query.period.equals("week")) {
                out.append("<table class=\"table\" border=\"1\"> <caption class = \"week\">Неделя</caption> <tr> <th>Понедельник</th> <th>Вторник</th> <th>Среда</th> <th>Четверг</th> <th>Пятница</th> <th>Суббота</th> <th>Воскресенье</th> </tr>");
                out.append(generateWeek(query));
                out.append("</table>");
            }
            else if(query.period.equals("weeks")){
                for(int i = 0; i < 2; i++) {
                    out.append("<table class=\"table\" border=\"1\"> <caption class = \"week\">Неделя</caption> <tr> <th>Понедельник</th> <th>Вторник</th> <th>Среда</th> <th>Четверг</th> <th>Пятница</th> <th>Суббота</th> <th>Воскресенье</th> </tr>");
                    out.append(generateWeek(query));
                    out.append("</table>");
                }
            }
            else if(query.period.equals("month")){
                for(int i = 0; i < 4; i++) {
                    out.append("<table class=\"table\" border=\"1\"> <caption class = \"week\">Неделя</caption> <tr> <th>Понедельник</th> <th>Вторник</th> <th>Среда</th> <th>Четверг</th> <th>Пятница</th> <th>Суббота</th> <th>Воскресенье</th> </tr>");
                    out.append(generateWeek(query));
                    out.append("</table>");
                }
            }
            
        }catch (Exception e){
            System.out.append(e.getMessage());
        }
        
        return out.toString();

    }

    public String generateWeek(Query q)throws Exception{
    
    	StringBuilder out = new StringBuilder();

        Generator g = new Generator(q);

            ArrayList<ArrayList<String>> meal = new ArrayList<>();

            for(int i = 0; i < 7; i++) {
                meal.add(g.generateBreakfast(q));
            }
            out.append("<tr style=\"background-color:#ffffff; font-weight:bold;\"><td>Завтрак</td><td>Завтрак</td><td>Завтрак</td><td>Завтрак</td><td>Завтрак</td><td>Завтрак</td><td>Завтрак</td></tr>");
            out.append(generateMealHTML(meal));
            meal.clear();




            for(int i = 0; i < 7; i++) {
                meal.add(g.generateLunch(q));
            }
            out.append("<tr style=\"background-color:#ffffff; font-weight:bold;\"><td>Полдник</td><td>Полдник</td><td>Полдник</td><td>Полдник</td><td>Полдник</td><td>Полдник</td><td>Полдник</td></tr>");
            out.append(generateMealHTML(meal));
            meal.clear();




            for(int i = 0; i < 7; i++) {
                meal.add(g.generateDinner(q));
            }
            out.append("<tr style=\"background-color:#ffffff; font-weight:bold;\"><td>Обед</td><td>Обед</td><td>Обед</td><td>Обед</td><td>Обед</td><td>Обед</td><td>Обед</td></tr>");
            out.append(generateMealHTML(meal));
            meal.clear();




            for(int i = 0; i < 7; i++) {
                meal.add(g.generateSupper(q));
            }
            out.append("<tr style=\"background-color:#ffffff; font-weight:bold;\"><td>Ужин</td><td>Ужин</td><td>Ужин</td><td>Ужин</td><td>Ужин</td><td>Ужин</td><td>Ужин</td></tr>");
            out.append(generateMealHTML(meal));
            meal.clear();

		return out.toString();
    }

    public String generateMealHTML(ArrayList<ArrayList<String>> meal){
    
        StringBuilder out = new StringBuilder();
    
        int maxSize = getMaxSize(meal);
        ArrayList<ArrayList<String>> normalized = new ArrayList<>();
        for(int i = 0; i < 7; i++){ //для каждого дня недели
            normalized.add(normalize(meal.get(i), maxSize));
        }

        for(int i = 0; i < maxSize; i++) {
            out.append("<tr>");
            for (int j = 0; j < 7; j++) {
                out.append("<td>" + normalized.get(j).get(i) + "</td>");
            }
            out.append("</tr>\n");
        }
        
        return out.toString();
        
    }

    public int getMaxSize(ArrayList<ArrayList<String>> meal){
        int maxSize = 0;
        for(ArrayList<String> list : meal){
            if(list.size() > maxSize){ maxSize = list.size(); }
        }
        return maxSize;
    }

    ArrayList<String> normalize(ArrayList<String> list, int maxSize){
        ListIterator<String> it = list.listIterator(list.size() - 1);
        while(list.size() != maxSize){ it.add(" "); }
        return list;
    }

    public  String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
