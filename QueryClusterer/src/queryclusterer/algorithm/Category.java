package queryclusterer.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Category {
	
	private static int NUMBER_OF_CATAGORIES = 0;
	private static List<Category> CATEGORIES = new ArrayList<Category>(); 
		
	private String category = null;
	private String superCategory = null;
	private String subCategory = null;
	private int id = -1;
	
	public Category(String category, String superCategory, String subCategory){
		this.category = category;
		this.superCategory = superCategory;
		this.subCategory = subCategory;
		this.id = Category.NUMBER_OF_CATAGORIES;
		Category.NUMBER_OF_CATAGORIES++;
	}
	
	public Category(String category){
		this.category = category;
		String[] split = category.split("/");
		this.superCategory = split[0];
		this.subCategory = split[1];
		this.id = Category.NUMBER_OF_CATAGORIES;
		Category.NUMBER_OF_CATAGORIES++;
		CATEGORIES.add(this);
	}
	
	public static void readCategoryFile(File file, boolean hasQueryColumn){
		try {
			Scanner scanLine = new Scanner(file);
			
			while(scanLine.hasNextLine()){
				Scanner scanWord = new Scanner(scanLine.nextLine());
				if(hasQueryColumn)
					scanWord.next(); // the query
				while(scanWord.hasNext()){
					String category = scanWord.next();
					new Category(category);
				}
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
