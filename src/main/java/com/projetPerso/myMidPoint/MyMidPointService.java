package com.projetPerso.myMidPoint;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParserFactory;

import org.springframework.web.multipart.MultipartFile;

public class MyMidPointService {
	
	public Position processJson(MultipartFile file) throws IOException {
        JsonParserFactory factory = Json.createParserFactory(null);
        JsonParser parser = factory.createParser(file.getInputStream());
        
        long sumlat=0;
        long sumlong=0;
        long countlat=0;
        long countlong=0;
        Position position = new Position();
		while (parser.hasNext()) {
		     Event event = parser.next();
		     if (event == JsonParser.Event.KEY_NAME ) {
		         String key = parser.getString();
		         event = parser.next();
		         if (key.equals("latitudeE7")) {
		        	 long latitude = Long.parseLong(parser.getString());
		             sumlat=sumlat+latitude;
		             ++countlat;
		         }else if(key.equals("longitudeE7")){
		        	 long longitude = Long.parseLong(parser.getString());
		             sumlong=sumlong+longitude;
		        	 ++countlong;
		         }
		     }
		 }
		
		position.setLatitude(sumlat/countlat);
		position.setLongitude(sumlong/countlong);
		return position;
		 
	}

}
