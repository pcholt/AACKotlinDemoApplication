package com.overswell.demoapplication

import android.app.Application
import android.arch.persistence.room.*
import io.reactivex.Flowable

@Entity
data class Account(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val firstName: String = "",
        val lastName: String = ""
)

@Entity(foreignKeys = [
ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["creditAccount"]),
ForeignKey(
        entity = Account::class,
        parentColumns = ["uid"],
        childColumns = ["debitAccount"])
])
data class Transaction(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val creditAccount: Int,
        val debitAccount: Int
)

@Dao
interface AccountDao {

    @Query("Select * from Account")
    fun allAccounts(): Flowable<List<Account>>

    @Insert
    fun insert(account: Account)

}

class MyApp : Application() {
    companion object {
        var database: MyDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        MyApp.database = Room.databaseBuilder(this, MyDatabase::class.java, "my-db").build()
    }
}

@Database(
        entities = [
        Account::class,
        Transaction::class
        ],
        version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}
