package com.example.Dao.person

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Person(var firstName: String?, var lastName: String?, var ssn: String?)

fun main() {
    //JSON Serialization
    val pObj = Person("George", "Simpson", "123212413")
    val jsonStr = Gson().toJson(pObj)
    println("The converted JSON string: ${jsonStr}")
    //JSON Serialization of List<Person>
    var pList: MutableList<Person> = mutableListOf()
    pList.add(pObj)
    pList.add(Person("Anne", "Simpson", "123322413"))
    val listJsonString = Gson().toJson(pList)
    println("${listJsonString}")
    //List<Person> object deserialization
    val personListType: Type = object : TypeToken<Person>() {}.type
    //JSON Deserialization
    val pObj1: Person
    pObj1 = Gson().fromJson(jsonStr, Person::class.java)
    println("${pObj1.toString()}")
}
