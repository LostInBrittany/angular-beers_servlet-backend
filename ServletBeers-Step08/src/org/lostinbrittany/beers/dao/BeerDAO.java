package org.lostinbrittany.beers.dao;

import org.bson.Document;
import org.lostinbrittany.beers.model.Beer;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class BeerDAO {

	
	private BeerDAO() {
		gson = new Gson();
		mongoClient = new MongoClient();		
		database = mongoClient.getDatabase("beers");
		beers = database.getCollection("beers");
	}
	
	private static BeerDAO instance;
	
	public static BeerDAO getInstance() {
		if (null != instance) {
			return instance;
		}
		instance = new BeerDAO();
		return instance;
	}
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> beers;
	private Gson gson;
	

	public String getBeer(String id) {		
		
		Document beerDocument = beers.find(Filters.eq("id", id)).first();
		
		if (null == beerDocument) {
			return "{}";
		}
		
		Beer beer = gson.fromJson(beerDocument.toJson(), Beer.class);
		
		return beer.toJSONDetail();
	}
	
	public String getBeerList() {
		
		/*
		   {
		    "alcohol": 6.8,
		    "description": "Affligem Blonde, the classic clear blonde abbey ale, with a gentle roundness and 6.8% alcohol. Low on bitterness, it is eminently drinkable.",
		    "id": "AffligemBlond",
		    "img": "/img/AffligemBlond.jpg",
		    "name": "Affligem Blond"
		  },
		 */
		StringBuilder sb = new StringBuilder("[");
		for (Document beerDocument : beers.find()) {
			Beer beer = gson.fromJson(beerDocument.toJson(), Beer.class);			
			sb.append(beer.toJSON()).append(",");		
		}
		sb.deleteCharAt(sb.length()-1).append("]");
		return sb.toString();
	}
	
	public Document generateBeerDocument(Beer beer) {
		Document doc = new Document()
				.append("name", beer.getName())
				.append("id", beer.getId())
		    	.append("alcohol", beer.getAlcohol())
		    	.append("description", beer.getDescription())
		    	.append("img", beer.getImg())
		    	.append("label", beer.getLabel())
		    	.append("availability", beer.getAvailability())
		    	.append("serving", beer.getServing())
		    	.append("style", beer.getStyle())
		    	.append("brewery", beer.getBrewery());
		return doc;
	}
	
	public boolean hasBeer(String beerId) {
		return beers.find(Filters.eq("id", beerId)).iterator().hasNext(); 
	}
	
	public void createBeer(Beer beer) {
		beers.insertOne(generateBeerDocument(beer));
	}
	
	public void updateBeer(String id, Beer beer) {
		beers.replaceOne(beers.find(Filters.eq("id", id)).first(), generateBeerDocument(beer));
	}
	
	public void deleteBeer(String beerId) {
		beers.deleteOne(Filters.eq("id", beerId));
	}
}
