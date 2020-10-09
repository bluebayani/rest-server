package co.carrd.bluuueish.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor(var dbName:String = ""){
    init {
        //create the database, create tables and keep the database connection
        //create the database, create tables and keeps the db connection
        dbName = "C:\\Users\\blues\\Desktop\\db.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists person (first_name text, last_name text, ssn text)"
        dbConn.exec(sqlStmt)

    }
    fun getDbConnection(): SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }
    //single object pattern
    //singleton
    companion object {
        private var dbObj : Database? = null
        fun getInstance() : Database? {
            if(dbObj == null) {
                dbObj == Database()
            }
            return dbObj
        }
    }
}