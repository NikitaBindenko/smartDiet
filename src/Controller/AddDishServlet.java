package Controller;

import Model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class AddDishServlet extends HttpServlet {
    
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {
        response.setContentType("text/html");
        request.getRequestDispatcher("front/expert.html").include(request, response);
        
        String dish = request.getParameter("dish");
        float cal = Float.parseFloat(request.getParameter("cal"));
        float prot = Float.parseFloat(request.getParameter("prot"));
        float fat = Float.parseFloat(request.getParameter("fat"));
        float carb = Float.parseFloat(request.getParameter("carb"));
        String[] time = request.getParameterValues("time");
        
        boolean[] flags = new boolean[11];
        
        
        try{
        
        String[] allergy = request.getParameterValues("allergy");
        
        if (Arrays.asList(allergy).contains("nuts")){
            flags[7] = true;
        }
        if (Arrays.asList(allergy).contains("citrus")){
            flags[8] = true;
        }
        if (Arrays.asList(allergy).contains("gluten")){
            flags[9] = true;
        }
        if (Arrays.asList(allergy).contains("lactose")){
            flags[10] = true;
        }
        
        }catch(NullPointerException e){
            
            flags[7] = flags[8] = flags[9] = flags[10] = false;
        }
        
        
        if (Arrays.asList(time).contains("first")){
            flags[0] = true;
        }
        if (Arrays.asList(time).contains("second")){
            flags[1] = true;
        }
        if (Arrays.asList(time).contains("bakery")){
            flags[2] = true;
        }
        if (Arrays.asList(time).contains("garnish")){
            flags[3] = true;
        }
        if (Arrays.asList(time).contains("desert")){
            flags[4] = true;
        }
        if (Arrays.asList(time).contains("snack")){
            flags[5] = true;
        }
        if (Arrays.asList(time).contains("salad")){
            flags[6] = true;
        }
        
        
        
        Dish newDish = new Dish(0, dish, flags[0], flags[1], flags[2], flags[3], flags[4], flags[5], flags[6], flags[7], flags[8], flags[9], flags[10], cal, prot, fat, carb);
        DAO db = new DAO();
        db.insertDish(newDish);
    }
}
