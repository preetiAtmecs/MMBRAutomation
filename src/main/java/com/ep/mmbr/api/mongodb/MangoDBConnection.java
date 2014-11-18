package com.ep.mmbr.api.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class MangoDBConnection {

	private static MongoClient mongoClient;

	public MongoClient getConnection(String url) {

		try {
			mongoClient = new MongoClient(url);
		} catch (UnknownHostException e) {
			System.out.println("MangoDb connection failed");
			e.printStackTrace();
		}
		System.out.println("MongoDB connection established successfully");
		return mongoClient;

	}

	public DBCollection getDataBaseCollection(String databaseName,
			String collectionName) {

		DB db = mongoClient.getDB(databaseName);

		DBCollection collection = db.getCollection(collectionName);
		System.out.println("\nCollection  selected successfully..........."
				+ collection.getFullName());
		return collection;

	}

	public int getCount(DBCollection collection, BasicDBObject query) {
		DBCursor cursor = collection.find(query);
		return cursor.count();
	}

	public void deleteRecord(DBCollection coll, BasicDBObject query) {
		WriteResult result = coll.remove(query);
		System.out.println("\nRecord removed: " + result.toString());
	}

	/*public void getResult(DBCollection coll, BasicDBObject query) {

		DBCursor cursor = coll.find(query);
		System.out.println("\n\ncursor :" + cursor.count());
		int i = 1;
		while (cursor.hasNext()) {
			System.out.println("Inserted Document: " + i);
			System.out.println(cursor.next());
			DBObject obj = cursor.next();
			System.out.println("\n obj: " + obj.get("name"));
			i++;
		}
	}*/

}
