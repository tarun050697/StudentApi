package student.db;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import student.models.Student;
import org.bson.Document;
import play.Logger;
import student.utils.Log;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class MongoOperations {
    private static final Logger.ALogger logger = Log.Logger;

    private MongoClient mongoClient;

    @Inject
    public MongoOperations(MongoSingleton mongoSingleton) {
        this.mongoClient = mongoSingleton.getMongoClient();
    }

    public List<Document> find(String databaseName, String collectionName, Document queryDocument) {
        try {
            List<Document> documents = new ArrayList<>();

            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection collection = database.getCollection(collectionName);

            FindIterable iterable = collection.find(queryDocument).projection(new Document("_id", 0)).projection(new Document("_id", 0));
            iterable.into(documents);

            return documents;
        } catch (Exception e) {
            logger.error("Exception occurred in MongoOperations.find: {}", e);
        }

        return new ArrayList<>();
    }

       public Document insertOne(String databaseName, String collectionName, Document insertDoc) {
   // public void insertOne(String databaseName, String collectionName, Student student) {
        try {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection collection = database.getCollection(collectionName, Student.class);
            collection.insertOne(insertDoc);

        } catch (Exception e) {
            logger.error("Exception occurred in MongoOperations.insertOne: {}", e);
        }

        return insertDoc;
    }

    public DeleteResult deleteOne(String databaseName, String collectionName, Document queryDocument) {
        try {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection collection = database.getCollection(collectionName);

            return collection.deleteOne(queryDocument);

        } catch (Exception e) {
            logger.error("Exception occurred in MongoOperations.deleteOne: {}", e);
        }

        return null;
    }

    public Document updateOne(String databaseName, String collectionName, Document queryDocument, Document updateDocument) {
        try {
            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection collection = database.getCollection(collectionName);
           // UpdateOptions options = new UpdateOptions();
           // UpdateOptions options = new UpdateOptions().upsert(true);

             collection.updateOne(queryDocument, updateDocument);
             return updateDocument;

        } catch (Exception e) {
            logger.error("Exception occurred in MongoOperations.updateOne: " + e);
        }

        return null;
    }

    public List<Document> find(String databaseName, String collectionName, Document queryDocument, int limit, int skip) {
        try {
            List<Document> documents = new ArrayList<>();

            MongoDatabase database = mongoClient.getDatabase(databaseName);
            MongoCollection collection = database.getCollection(collectionName);

            FindIterable iterable = collection.find(queryDocument).projection(new Document("_id", 0)).limit(limit).skip(skip);
            iterable.into(documents);

            return documents;
        } catch (Exception e) {
            logger.error("Exception occurred in MongoOperations.find: {}", e);
        }

        return new ArrayList<>();
    }

    public int size(String databaseName, String collectionName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection collection = database.getCollection(collectionName);
        List<Document> docs = new ArrayList<>();
        collection.find().into(docs);

        return docs.size();
    }
    public  List<Document> searchMany(String query, String db, String coll){
        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection collection = database.getCollection(coll);
        String sp = query;
        Pattern pattern = Pattern.compile(sp, Pattern.CASE_INSENSITIVE);
        List<Document> docs = new LinkedList<>();
        Bson filter1 = Filters.regex("name",pattern);
        Bson filter2 = Filters.regex("emailId",pattern);
        Bson filter3 = Filters.regex("mobile",pattern);
        Bson filter4 = Filters.regex("education",pattern);

        Document que = new Document("$or", Arrays.asList(
                filter1,
                filter2,
                filter3,
                filter4

        ));
        collection.find(que).into(docs);

        return docs;

    }
}