package queryclusterer.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import queryclusterer.algorithm.Category;
import queryclusterer.algorithm.Query;
import queryclusterer.algorithm.Word;

public class QueryDataReader {
	
	
	public static List<Query> readCategoryFile(File file){
		boolean TEST = true;
		
		ArrayList<Query> queriesFromFile = new ArrayList<Query>();
		try {
			Scanner scanLine = new Scanner(file);
			
			while(scanLine.hasNextLine()){
				Scanner scanWord = new Scanner(scanLine.nextLine());
				scanWord.useDelimiter("\t");
				String queryStr = null;
				Query query = null;
				if(scanWord.hasNext()){
					queryStr = scanWord.next(); // the query or string "catagories". Either way, ignore for now
					if (!TEST || queryStr.trim().equalsIgnoreCase("0 apr")) // for test case only do 0apr
						query = new Query(queryStr);
				}
				while(scanWord.hasNext()){
					String categoryStr = scanWord.next();
					 //read in the category
					Category category = Category.getCategory(categoryStr);
					if(category != null){
						category = new Category(categoryStr);
						Category.getCategories().add(category);
						if(query != null)
							query.addVotedCategory(category);
					}
				}
				if(query != null){
					queriesFromFile.add(query);
				}
			}			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return queriesFromFile;
	}
	
	public static void testWordEnrich(List<Query> queries) throws FileNotFoundException{
		File file = new File("/0apr.info");
		Scanner scan = new Scanner(file);
		List<Word> wordsFor0APRTest = new ArrayList<Word>();
		
		while(scan.hasNext()){
			String nextWord = scan.next();
			Word.getWord(nextWord); // creates word if we dont already have it;
		}
		for(Query query : queries){
			if(query.getQuery().equalsIgnoreCase("0 apr")){
				query.addQueryWords(wordsFor0APRTest); //find apr as a query
			}
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		File catLabelFile = new File("/labeler1.txt");
		
		List<Query> queries = readCategoryFile(catLabelFile); // read queries and categories
		
		testWordEnrich(queries); // have enriched queries (for 0 apr) / reads apr file for words
		for(Query query : queries){// get measures
			System.out.println(query.getDistribution());
		}
		
		
	}
}
