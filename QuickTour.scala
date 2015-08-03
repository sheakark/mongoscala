import com.scalainaction.mongo._
import com.mongodb.BasicDBObject

def client = new MongoClient
def db = client.db("mydb1")


val col = db.readOnlyCollection("test")
println(col.name)

val adminCol = db.administrableCollection("test")
adminCol.drop

val updatableCol = db.updatableCollection("test")

for(name <- db.collectionNames) println(name)

val doc = new BasicDBObject()
doc.put("name","MongoDB")
doc.put("type", "database")
doc.put("count", "1")

val info = new BasicDBObject()
info.put("x", "203")
info.put("y", "102")
doc.put("info", info)

updatableCol += doc

println(updatableCol.findOne)

for(i <- 1 to 100) updatableCol += new BasicDBObject("i",i)

val cursor = col.find(new BasicDBObject("i",71))
while(cursor.hasNext()){
	println(cursor.next())
}

