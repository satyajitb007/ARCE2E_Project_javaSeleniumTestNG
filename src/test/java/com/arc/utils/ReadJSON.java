package com.arc.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Parameters;
import java.io.FileReader;


public class ReadJSON {
	static String data;
	private static JSONObject obj;
	private static String _jsonFile;
	
	
	public static void readJSON(String jsonFile) throws IOException, ParseException
	{
		
		  _jsonFile = jsonFile; File inputFile = new File(jsonFile); 
		  Reader read = new
		   FileReader(inputFile); JSONParser parser=new JSONParser(); obj =
		   (JSONObject)parser.parse(read);
		 
		
		
	}
	
	
	public static String getData(String keyword) throws IOException, ParseException  {
		String data = (String)obj.get(keyword);
    	return data;
	
	}
}
