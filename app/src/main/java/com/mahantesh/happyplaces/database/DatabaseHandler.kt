package com.mahantesh.happyplaces.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.mahantesh.happyplaces.model.HappyPlaces

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "HappyPlacesDatabase"
        private const val TABLE_HAPPY_PLACES = "HappyPlacesTable"

        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_IMAGE = "image"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_LOCATION = "location"
        private const val KEY_LATITUDE = "latitude"
        private const val KEY_LONGITUDE = "longitude"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE " + TABLE_HAPPY_PLACES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DATE + " Text,"
                + KEY_LOCATION + " TEXT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT)")
        p0?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACES")
        onCreate(p0)
    }

    fun addHappyPlaces(happyPlaces: HappyPlaces): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, happyPlaces.title)
        contentValues.put(KEY_IMAGE, happyPlaces.image)
        contentValues.put(KEY_DESCRIPTION, happyPlaces.description)
        contentValues.put(KEY_DATE, happyPlaces.date)
        contentValues.put(KEY_LOCATION, happyPlaces.location)
        contentValues.put(KEY_LATITUDE, happyPlaces.latitude)
        contentValues.put(KEY_LONGITUDE, happyPlaces.longitude)

        val result = db.insert(TABLE_HAPPY_PLACES, null, contentValues)
        db.close()
        return result
    }

    fun updateHappyPlaces(happyPlaces: HappyPlaces): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, happyPlaces.title)
        contentValues.put(KEY_IMAGE, happyPlaces.image)
        contentValues.put(KEY_DESCRIPTION, happyPlaces.description)
        contentValues.put(KEY_DATE, happyPlaces.date)
        contentValues.put(KEY_LOCATION, happyPlaces.location)
        contentValues.put(KEY_LATITUDE, happyPlaces.latitude)
        contentValues.put(KEY_LONGITUDE, happyPlaces.longitude)

        val result =
            db.update(TABLE_HAPPY_PLACES, contentValues, KEY_ID + "=" + happyPlaces.id, null)
        db.close()
        return result
    }

    fun deleteHappyPlace(happyPlaces: HappyPlaces): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_HAPPY_PLACES, KEY_ID + "=" + happyPlaces.id, null)
        db.close()
        return result
    }

    fun getHappyPlacesList(): ArrayList<HappyPlaces> {
        val happyPlacesList = ArrayList<HappyPlaces>()
        val selectQuery = "SELECT * FROM $TABLE_HAPPY_PLACES"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val place = HappyPlaces(
                        cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getString(cursor.getColumnIndex(KEY_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_LONGITUDE))
                    )
                    happyPlacesList.add(place)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        return happyPlacesList
    }
}