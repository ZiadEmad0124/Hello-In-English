package com.ziad_emad_dev.hello_in_english.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WordDB(context: Context) : SQLiteOpenHelper(context, databaseName, null, databaseVersion) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {

        val sql = "CREATE TABLE ${Table.tableName}(" +
                "${Table.id} INTEGER PRIMARY KEY," +
                "${Table.englishWord} TEXT," +
                "${Table.arabicWord} TEXT" +
                ")"

        sqliteDatabase?.execSQL(sql)
    }

    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addNewWord(word: Word) {

        val newWord = ContentValues().apply {
            put(Table.englishWord, word.englishWord)
            put(Table.arabicWord, word.arabicWord)
        }

        val sqLiteDatabase = this.writableDatabase
        sqLiteDatabase.insert(Table.tableName, null, newWord)
        sqLiteDatabase.close()
    }

    fun deleteWord(englishWord: String) {

        val sqLiteDatabase = this.writableDatabase
        val sql =
            "DELETE FROM ${Table.tableName} WHERE ${Table.englishWord} = '$englishWord'"
        sqLiteDatabase.execSQL(sql)
        sqLiteDatabase.close()
    }


    fun getAllWords(): List<Word> {

        val allWords = mutableListOf<Word>()

        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM ${Table.tableName}", null)

        while (cursor.moveToNext()) {
            val englishWord = cursor.getString(1)
            val arabicWord = cursor.getString(2)

            allWords.add(Word(englishWord, arabicWord))
        }
        sqLiteDatabase.close()

        return allWords
    }

    companion object {
        private const val databaseName = "words_database"
        private const val databaseVersion = 1
    }
}