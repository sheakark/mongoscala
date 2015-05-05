package com.scalainaction.mongo

//import for all classes of a package
import com.mongodb._
//import just a class
import com.mongodb.Mongo
//import for methods
import com.mongodb.Mongo._

// creates constructor with two arguments
class MongoClient(val host:String, val port:Int) {
	require(host != null, "Hostname required")

	private val mdb = new Mongo(host, port)

	//Default constructor
	def this() = this("127.0.0.1", 27017)

	//def version = mdb.getVersion

	def dropdb(name:String) = mdb.dropDatabase(name)

	def createdb(name:String) = DB(mdb.getDB(name))

	def db(name:String) = DB(mdb.getDB(name))

}
