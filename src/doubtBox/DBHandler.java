package doubtBox;

import java.util.Iterator;
import java.util.Vector;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;



public class DBHandler
{
	MongoClient mongo;
	MongoDatabase database;
	MongoCollection<Document> collection;
	public DBHandler()
	{
		mongo = new MongoClient( "localhost" , 27017 ); 
		database = mongo.getDatabase("cam"); 
		collection = database.getCollection("dBox");
		
	}
	
	public Teacher getTdetais(String strTname)
	{
		Teacher t1 =null;
		
		FindIterable<Document> iterDoc = collection.find(eq("strTname",strTname)).projection(new Document("strIP", true)
                .append("_id" , false));
		Iterator it = iterDoc.iterator();
		String strIP="",strTime="",result="";
		if(it.hasNext())
		{
			 result = it.next().toString();
			strIP=result.substring(result.indexOf("=")+1,result.indexOf("}"));

		}  
		
		iterDoc = collection.find(eq("strTname",strTname)).projection(new Document("strTime", true)
                .append("_id" , false));
		it = iterDoc.iterator();
		if(it.hasNext())
		{
			 result = it.next().toString();
			strTime=result.substring(result.indexOf("=")+1,result.indexOf("}"));

		} 
		
		t1 = new Teacher(strTname, strIP, strTime);		
	
		return t1;
	}
	
	public Vector<String> getTnameTblteacher()
	{
		Vector<String> vctrTdetails = new Vector<String>();
		
		FindIterable<Document> iterDoc = collection.find(eq("ops",null)).projection(new Document("strTname", true)
                  .append("_id" , false));
		String result = "";
		vctrTdetails.add("");  
		Iterator it = iterDoc.iterator(); 
	      while (it.hasNext()) {  
	    	  result = it.next().toString();
	         vctrTdetails.add(result.substring(result.indexOf("=")+1,result.indexOf("}")));  
	      }
		
	      
		return vctrTdetails;
		
	}
	
	
	
	public void insertIntoTblteacher(String strTname, String strIP, String strTime)
	{
		Document document = new Document();
		document.append("strTname", strTname);
		document.append("strIP", strIP);
		document.append("strTime", strTime);
		collection.insertOne(document);
		
		
	}
	
	
	public void deleteFromTblteacher(String strIP)
	{
		Bson filter = new Document("strIP", strIP);
		collection.deleteOne(filter);
		
		
	}
	
	
	public void updateIntoTblteacher(String strTname, String strIP, String strTime)
	{
		collection.updateOne(new Document("strIP", strIP),  
                new Document("$set", new Document("strTname", strTname).append("strIP", strIP).append("strTime", strTime)));
		
		
	}
	


	public boolean isValidTeacher(String strUid, String strPwd) {
		boolean res = false;
	
		FindIterable<Document> iterDoc = collection.find(and(eq("ops","loginTeacher"),eq("userid",strUid),eq("upwd",strPwd)));
		Iterator it = iterDoc.iterator(); 
	      while (it.hasNext()) {  
	         res = true;
	         System.out.println(it.next());  
	      }
	      
	      
		return res;
	}
	
	
	public boolean isValidStudent(String strUid, String strPwd) {
		boolean res = false;
		FindIterable<Document> iterDoc = collection.find(and(eq("ops","loginStd"),eq("userid",strUid),eq("upwd",strPwd)));
		Iterator it = iterDoc.iterator(); 
	      while (it.hasNext()) {  
	         res = true;
	         System.out.println(it.next());  
	      }
	      
	      

		return res;
	}
}