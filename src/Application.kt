
// BASIC IMPLEMENTATION OF RESTFUL SERVER
// save a person into a database
// retrieve list of person stored in database (relational data model)
// entity (object) is implemented as a table
// attribute is a column
// entity as a record
// each table has a primary key column (unique value)
// relationships implemented via foreign keys
// http post rest request (save a person)
// http get rest request (retrieve a person


//NOTES BELOW
//-------------------------------
package com.example

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import co.carrd.bluuueish.Dao.Database
import co.carrd.bluuueish.Dao.person.Person
import co.carrd.bluuueish.Dao.person.PersonDao
import com.google.gson.Gson

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
///Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk/Contents/Home/bin/java
fun Application.module(testing: Boolean = false) {
    //extention
    //@annotation
    //routing constructor only takes one parameter, which is a lambda function
    //DSL - Domain Specific Language
    val server = embeddedServer(Netty, 8080) {
        routing {
            this.get("/PersonService/Add") {
                println("HTTP message is using GET method with /get")
                //request parameters
                val fn = call.request.queryParameters["FirstName"]
                val ln = call.request.queryParameters["LastName"]
                val sn = call.request.queryParameters["SSN"]
                //print parameters
                val response = String.format("First name is %s and Last name is %s", fn, ln)
                //http://localhost:8080/get?FirstName=Blue&LastName=Bayani
                //Output: First name is Blue and Last name is Bayani
                //response, status okay, text
                val pObj = Person(fn, ln, sn)
                val dao = PersonDao().addPerson(pObj)
                val dbObj = Database.getInstance()
                call.respondText(
                    response,
                    status = HttpStatusCode.OK,
                    contentType = ContentType.Text.Plain
                )
            }
            get("PersonService/getAll") {
                val pList = PersonDao().getAll()
                println("The number of students: ${pList.size}")
                val RespJsonStr = Gson().toJson(pList)
                call.respondText(
                    RespJsonStr,
                    status = HttpStatusCode.OK,
                    contentType = ContentType.Application.Json
                )
            }
            post("/PersonService/add") {
                val contType = call.request.contentType()
                val data = call.request.receiveChannel()
                val dataLength = data.availableForRead
                var output = ByteArray(dataLength)
                data.readAvailable(output)
                //read data recieve data then put into buffer
                val str = String(output)

                println("HTTP message is using POST method with /post ${contType} ${str}")
                call.respondText(
                    "The POST request was successfully processed",
                    status = HttpStatusCode.OK,
                    contentType = ContentType.Text.Plain
                )
                //using postman at vvv
                //http://0.0.0.0:8080/post
                //in body raw, JSON
                //{"FirstName":"James","LastName":"Shen"}
            }
        }
    }
    server.start(wait = true)
}


