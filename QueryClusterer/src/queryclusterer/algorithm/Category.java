package queryclusterer.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Category {
	
	private static int NUMBER_OF_CATEGORIES = 0;
	private static List<Category> CATEGORIES = new ArrayList<Category>(); 
		
	private int id = -1;
	private String category = null;
	private String superCategory = null;
	private String subCategory = null;
	
	
	public Category(String category, String superCategory, String subCategory){
		this.category = category;
		this.superCategory = superCategory;
		this.subCategory = subCategory;
		this.id = Category.NUMBER_OF_CATEGORIES;
		Category.NUMBER_OF_CATEGORIES++;
	}
	
	public Category(String category){
		this.category = category;
		String[] split = category.split("/");
		this.superCategory = split[0];
		this.subCategory = split[1];
		this.id = Category.NUMBER_OF_CATEGORIES;
		Category.NUMBER_OF_CATEGORIES++;
	}
	
	public static Category getCategory(String category){
		
		for(Category existCat : CATEGORIES){
			if(existCat.getCategory().equals(category) )
				return existCat;
		}
		return null;
	}
	
	private String getCategory() {
		return category;
	}
	
	public static List<Category> getCategories(){
		return CATEGORIES;
	}

	public static int getNumberOfCategories(){
		return NUMBER_OF_CATEGORIES;
	}
	
	public int getCategoryId(){
		return id;
	}

	
}
