package com.logdoc.delivery.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.logdoc.delivery.db.dao.PingDataDao;
import com.logdoc.delivery.db.model.Ping;

/**
 * Created by Dmitry Ushkevich on 15.05.2018.
 */

@Database(entities = {Ping.class}, version = 1)

public abstract class LOGDOCDatabase extends RoomDatabase {

	public static final int SYNC_NEW = 0;
	public static final int SYNC_PROGRESS = 1;
	public static final int SYNC_DONE = 2;

	public abstract PingDataDao getPingDao();

}
