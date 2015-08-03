package com.scalainaction.mongo

// Interface /Collection of traits that operate on a MongoDB "collection"

import com.mongodb.{DBCollection => MongoDBCollection}
import com.mongodb.DBObject
import com.mongodb._

class DBCollection(override val underlying:MongoDBCollection) extends ReadOnly

trait ReadOnly {
	val underlying:MongoDBCollection
	def name = underlying.getName
	def fullName = underlying.getFullName
	def find(doc:DBObject) = underlying find doc
	def findOne(doc:DBObject) = underlying findOne doc
	def findOne = underlying.findOne
	def getCount(doc:DBObject) = underlying getCount doc
}

//All traits extends readOnly since we have to find one before we operate on it 

trait Administrable extends ReadOnly {
	def drop:Unit = underlying drop
	def dropIndexes = underlying dropIndexes
}

trait Updatable extends ReadOnly {
	def -=(doc: DBObject) = underlying remove doc
	def +=(doc: DBObject) = underlying save doc
}

trait Memoizer extends ReadOnly {

	val history = scala.collection.mutable.Map[Int, DBObject]()
  val historyCursor = scala.collection.mutable.Map[Int, DBCursor]()

	override def findOne = {
    history.getOrElseUpdate(-1, {super.findOne})
  }

  override def findOne(doc:DBObject) = {
    println("calling memoizer in findOne().........................")
    history.getOrElseUpdate(doc.hashCode, {super.findOne(doc)})
  }

  override def find(doc:DBObject) = {
    println("calling memoizer in find()..................")
    historyCursor.getOrElseUpdate(doc.hashCode, {super.find(doc)})
  }

}