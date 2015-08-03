package com.scalainaction.mongo

import com.mongodb.{DB => MongoDB}
import scala.collection.convert.Wrappers._

class DB private(val underlying:MongoDB) {

	private def collection(name:String) = underlying.getCollection(name) 
	
	def readOnlyCollection(name: String) = new DBCollection(collection(name)) with Memoizer
	//We are able to mixin administrable only because DBCollection also extends from ReadOnly. We cannot create
	//a instance of DBcollection without providing the mongo collection which ReadOnly trait depends on.
	//Adminstrable and Updatable trait extends ReadyOnly can only be mixed in with a class that also extends from ReadOnly
	//since someone has to provide the dependency of ReadOnly before they can be mixed in
	def administrableCollection(name: String) = new DBCollection(collection(name)) with Administrable with Memoizer
	def updatableCollection(name: String) = new DBCollection(collection(name)) with Updatable with Memoizer

	def collectionNames = for (name <- new 
	    JSetWrapper(underlying.getCollectionNames) 
	   )yield name
}

object DB {
    def apply(underlying:MongoDB) = new DB(underlying)
}
