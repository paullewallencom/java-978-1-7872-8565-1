/*  Data Analysis with Java
 *  John R. Hubbard
 *  Jul 26, 2017
 */

package dawj.ch10;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

public class InsertMongoDB {
    public static void main(String[] args) {
        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase friends = client.getDatabase("friends");
        MongoCollection relatives = friends.getCollection("relatives");
        
        addDoc("John", "son", relatives);
        addDoc("Bill", "brother", relatives);
        addDoc("Helen", "grandmother", relatives);
        
        printCollection(relatives);
    }
    
    public static void addDoc(String fname, String relation, 
            MongoCollection collection) {
        Document doc = new Document();
        doc.put("fname", fname);
        doc.put("relation", relation);
        collection.insertOne(doc);
    }
    
    public static void printCollection(MongoCollection collection) {
        Bson bson = Sorts.ascending("fname");
        FindIterable<Document> docs = collection.find().sort(bson);
        int num = 0;
        for (Document doc : docs) {
            String name = doc.getString("fname");
            String relation = doc.getString("relation");
            System.out.printf("%4d. %s, %s%n", ++num, name, relation);
        }
    }
}

