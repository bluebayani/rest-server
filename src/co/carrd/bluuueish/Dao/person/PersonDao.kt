package co.carrd.bluuueish.Dao.person
import co.carrd.bluuueish.Dao.Dao
import co.carrd.bluuueish.Dao.Database

class PersonDao: Dao() {
    fun addPerson(pObj : Person) {
        //    1. get db connection
        val conn = Database.getInstance()?.getDbConnection()
        //    2. prepare the sql statement
        sqlStmt = "insert into person (first_name, last_name,ssn) values ('${pObj.firstName}','${pObj.lastName}'," +
                "'${pObj.ssn}')"
        // 3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll(): List<Person> {
        //    1. get db connection
        val conn = Database.getInstance()?.getDbConnection()
        //    2. prepare the sql statement
        sqlStmt = "select first_name, last_name, snn from person"
        // 3. submit the sql statement and init empty Person list
        val st = conn?.prepare(sqlStmt)
        var personList : MutableList<Person> = mutableListOf()
        // 4. convert into Kotlin object format
        while (st!!.step()) {
            //convert each record into Person object
            val fn = st.columnString(0)
            val ln = st.columnString(1)
            val ssn = st.columnString(2)
            personList.add(Person(fn,ln,ssn))
        }
        return personList
    }
}