package com.scalainaction.mongo

import com.mongodb.{DB => MongoDB}
import scala.collection.convert.Wrappers._

class DB private(val underlying:MongoDB) {

def collectionNames = for (name <- new 
    JSetWrapper(underlying.getCollectionNames) 
   )yield name
}

object DB {
    def apply(underlying:MongoDB) = new DB(underlying)
}
