package student.db;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import play.Logger;
import student.utils.ApplicationConstants;
import student.utils.Log;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoSingleton implements ApplicationConstants {
    private static final Logger.ALogger logger = Log.Logger;

    private MongoClient mongoClient = null;

    public MongoClient getMongoClient() {
        try {
            if(mongoClient == null) {
                ConnectionString connectionString = new ConnectionString("mongodb://" + MONGO_HOST + ":" + MONGO_PORT);
                com.mongodb.MongoClientSettings.Builder builder = com.mongodb.MongoClientSettings.builder();
                CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
                com.mongodb.MongoClientSettings settings = builder.applyConnectionString(connectionString).codecRegistry(pojoCodecRegistry).build();
                mongoClient = MongoClients.create(settings);
            }
            return mongoClient;
        } catch (Exception e) {
            logger.error("Exception occured in MongoSingleton.getMongoClient: {}", e);
        }

        return mongoClient;
    }
}