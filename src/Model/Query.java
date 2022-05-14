package Model;

public class Query {

    public String form;
    public String activity;
    public String allergy;

    public float height;
    public float weight;
    public int age;

    public boolean sex;    //1 - male  0 - female
    public String period;

    public Query(String form, String activity, String allergy, float height, float weight, int age, boolean sex, String period){
        this.form = form; this.activity = activity; this.allergy = allergy; this.height = height; this.weight = weight; this.age = age; this.sex = sex; this.period = period;
    }
//    public Query(String activity, float height, float weight, int age, boolean sex){
//        this.activity = activity;
//        this.height = height;
//        this.weight = weight;
//        this.age = age;
//        this.sex = sex;
//    }
}
