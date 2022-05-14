package Controller;

import Model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.*;

public class RecommendationServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try{
        
        String form = request.getParameter("form");
        String activity = request.getParameter("activity");
        float height = Float.parseFloat(request.getParameter("height"));
        float weight = Float.parseFloat(request.getParameter("weight"));
        int age = Integer.parseInt(request.getParameter("age"));
        boolean sex = Boolean.parseBoolean(request.getParameter("sex"));
        String period = request.getParameter("period");
        
        StringBuilder allergyString = new StringBuilder();
        try{
        String[] allergy = request.getParameterValues("allergy");
        
        for(String item : allergy){
            allergyString.append(item);
        }
        }catch(NullPointerException e){
            allergyString.append("empty");
        }
        
        Query q = new Query(form, activity, allergyString.toString(), height, weight, age, sex, period);
        Recommend r = new Recommend();
        
        request.getRequestDispatcher("front/i1.html").include(request, response);
        out.println(r.recommend(q));
        request.getRequestDispatcher("front/i2.html").include(request, response);
        
        }catch(Exception e){
        	out.println(e);
        }
        
        
        //request.getRequestDispatcher("front/i.html").include(request, response);
        
        
    	}

}
