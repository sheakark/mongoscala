package com.scalainaction.mongoexample
import com.mongodb.Mongo
import com.mongodb.Mongo._
import com.mongodb._
//import com.mongodb.MongoDB

object DB {
    def apply(underlying:MongoDB)= new DB(underlying)
}

class MongoClient(val host:String, val port:Int){
require(host !=null, " you have to provide a host name")
private val underlying = new Mongo(host, port)
def this()= this("127.0.0.1", 27017)

def version = underlying.getVersion

def dropDB(name:String) = underlying.dropDatabase(name)

def createDB(name:String) = DB(underlying.getDB(name))

def db(name:String) = DB(underlying.getDB(name))
    
}


