package br.jp.redsparrow.engine.core.database;

import br.jp.redsparrow.engine.core.GameObject;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorldObjectsDBHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "game.db";
	private final static int VERSION = 1;

	private final String TABLE_WORLDOBJECTS = "objects";
	private final String COL_OBJ_POSITION_X = "position_x";
	private final String COL_OBJ_POSITION_Y = "position_y";
	private final String COL_OBJ_TYPE = "type";
	
	private final String TABLE_CREATE = "CREATE TABLE " + TABLE_WORLDOBJECTS + " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COL_OBJ_POSITION_X + " FLOAT, "
			+ COL_OBJ_POSITION_Y + " FLOAT, "
			+ COL_OBJ_TYPE + " TEXT)";

	public WorldObjectsDBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(TABLE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public long putObject(GameObject object) {
		
		ContentValues contVals = new ContentValues();
		
		contVals.put(COL_OBJ_POSITION_X, object.getX());
		contVals.put(COL_OBJ_POSITION_Y, object.getY());
		contVals.put(COL_OBJ_TYPE, object.getType().getName());
		
		return getWritableDatabase().insert(TABLE_WORLDOBJECTS, null, contVals);
	}

}
