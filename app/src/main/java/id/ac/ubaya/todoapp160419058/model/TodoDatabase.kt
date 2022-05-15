package id.ac.ubaya.todoapp160419058.model

import android.content.Context
import androidx.room.*
import id.ac.ubaya.todoapp160419058.util.MIGRATION_1_2

@Database(entities = arrayOf(Todo::class), version =  2)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile private var instance: TodoDatabase ?= null
        private val LOCK = Any()

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "newtododb").addMigrations(MIGRATION_1_2).build()

        operator fun invoke(context:Context) {
            if(instance!=null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}

