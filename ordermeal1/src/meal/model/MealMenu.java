package meal.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class MealMenu {

	List<Meal> meallist;
 public MealMenu() {
	// TODO Auto-generated constructor stub
	 //list
	 meallist=new LinkedList<Meal>();
	 }
 //添菜单
 public void addItem(String name,String description,double price,String pfilename){
	 Meal meal=new Meal(name,description,price,pfilename);
	 meallist.add(meal);
	 
 }
 public Vector<String> getMealNames() {
	 Vector<String> vector=new Vector<String>();
	Iterator<Meal> it=meallist.iterator();
	while(it.hasNext())
	{
		Meal meal=it.next();
		String name=meal.getName();
		vector.add(name);
		
	}
	 return vector;
	
}
 public Meal getDetail(int index) {
	 return meallist.get(index);
	 
	
}
	
}

