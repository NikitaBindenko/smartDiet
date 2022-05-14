package Model;

import java.sql.*;
import java.util.HashSet;

public class DAO {

    public ResultSet resultSet;

    public HashSet<Dish> getDatabase (){    //достает все обьекты из базы данных и добавляет в HashSet
        HashSet<Dish> db = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dishes", "newuser", "password");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from dish");

            while (resultSet.next()) {
                Dish dish = new Dish(getDishID(), getDishName(), getBool("first_dish"), getBool("second_dish"), getBool("bakery"),
                        getBool("garnish"), getBool("desert"), getBool("snack"), getBool("salad"),
                        getBool("nuts"), getBool("citrus"), getBool("gluten"), getBool("lactose"),
                        getFloat("cal"), getFloat("prot"), getFloat("fat"), getFloat("carb"));

                db.add(dish);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return db;
    }

    synchronized public void insertDish(Dish dish){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dishes", "newuser", "password");
            Statement statement = connection.createStatement();
            String sqlQuery = "INSERT dish(name, first_dish, second_dish, bakery, garnish, desert, snack, salad, nuts, citrus, gluten, lactose, cal, prot, fat, carb)"
                    + " VALUES('" + dish.name + "'," + dish.first_dish + "," + dish.second_dish + "," + dish.bakery + "," + dish.garnish + "," + dish.desert + "," + dish.snack + "," + dish.salad + "," + dish.nuts + "," + dish.citrus + "," + dish.gluten + "," + dish.lactose + "," + dish.cal + "," + dish.prot + "," + dish.fat + "," + dish.carb + ");";
            statement.executeUpdate(sqlQuery);
            statement.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    synchronized public HashSet<Dish> getSQL(String sql){    //достает все обьекты из базы данных и добавляет в HashSet
        HashSet<Dish> db = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dishes", "newuser", "password");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Dish dish = new Dish(getDishID(), getDishName(), getBool("first_dish"), getBool("second_dish"), getBool("bakery"),
                        getBool("garnish"), getBool("desert"), getBool("snack"), getBool("salad"),
                        getBool("nuts"), getBool("citrus"), getBool("gluten"), getBool("lactose"),
                        getFloat("cal"), getFloat("prot"), getFloat("fat"), getFloat("carb"));

                db.add(dish);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return db;
    }

    public int getDishID() throws SQLException{ return resultSet.getInt("dish_id"); }
    public String getDishName() throws SQLException{ return resultSet.getString("name"); }
    public float getFloat(String str)throws SQLException{return resultSet.getFloat(str); }
    public boolean getBool(String str)throws SQLException{return resultSet.getBoolean(str); }

}
