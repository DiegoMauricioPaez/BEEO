package com.beeo.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BEEODatabase_Impl extends BEEODatabase {
  private volatile DailyActivityDao _dailyActivityDao;

  private volatile HabitAlertDao _habitAlertDao;

  private volatile LocationSampleDao _locationSampleDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_activity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dateEpochDay` INTEGER NOT NULL, `steps` INTEGER NOT NULL, `distanceMeters` REAL NOT NULL, `activeMinutes` INTEGER NOT NULL, `locationChanges` INTEGER NOT NULL, `homeTimeMinutes` INTEGER NOT NULL, `maxDistanceFromHome` REAL NOT NULL, `routePoints` TEXT NOT NULL, `zones` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `habit_alert` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `message` TEXT NOT NULL, `severity` TEXT NOT NULL, `isRead` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `location_sample` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `timestamp` INTEGER NOT NULL, `accuracy` REAL NOT NULL, `dateEpochDay` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '43042892311c5de6e78e134a09e8d24b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `daily_activity`");
        db.execSQL("DROP TABLE IF EXISTS `habit_alert`");
        db.execSQL("DROP TABLE IF EXISTS `location_sample`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsDailyActivity = new HashMap<String, TableInfo.Column>(10);
        _columnsDailyActivity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("dateEpochDay", new TableInfo.Column("dateEpochDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("steps", new TableInfo.Column("steps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("distanceMeters", new TableInfo.Column("distanceMeters", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("activeMinutes", new TableInfo.Column("activeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("locationChanges", new TableInfo.Column("locationChanges", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("homeTimeMinutes", new TableInfo.Column("homeTimeMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("maxDistanceFromHome", new TableInfo.Column("maxDistanceFromHome", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("routePoints", new TableInfo.Column("routePoints", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyActivity.put("zones", new TableInfo.Column("zones", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyActivity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyActivity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyActivity = new TableInfo("daily_activity", _columnsDailyActivity, _foreignKeysDailyActivity, _indicesDailyActivity);
        final TableInfo _existingDailyActivity = TableInfo.read(db, "daily_activity");
        if (!_infoDailyActivity.equals(_existingDailyActivity)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_activity(com.beeo.app.data.local.DailyActivityEntity).\n"
                  + " Expected:\n" + _infoDailyActivity + "\n"
                  + " Found:\n" + _existingDailyActivity);
        }
        final HashMap<String, TableInfo.Column> _columnsHabitAlert = new HashMap<String, TableInfo.Column>(6);
        _columnsHabitAlert.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitAlert.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitAlert.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitAlert.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitAlert.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHabitAlert.put("isRead", new TableInfo.Column("isRead", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHabitAlert = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHabitAlert = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHabitAlert = new TableInfo("habit_alert", _columnsHabitAlert, _foreignKeysHabitAlert, _indicesHabitAlert);
        final TableInfo _existingHabitAlert = TableInfo.read(db, "habit_alert");
        if (!_infoHabitAlert.equals(_existingHabitAlert)) {
          return new RoomOpenHelper.ValidationResult(false, "habit_alert(com.beeo.app.data.local.HabitAlertEntity).\n"
                  + " Expected:\n" + _infoHabitAlert + "\n"
                  + " Found:\n" + _existingHabitAlert);
        }
        final HashMap<String, TableInfo.Column> _columnsLocationSample = new HashMap<String, TableInfo.Column>(6);
        _columnsLocationSample.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationSample.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationSample.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationSample.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationSample.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationSample.put("dateEpochDay", new TableInfo.Column("dateEpochDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocationSample = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocationSample = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocationSample = new TableInfo("location_sample", _columnsLocationSample, _foreignKeysLocationSample, _indicesLocationSample);
        final TableInfo _existingLocationSample = TableInfo.read(db, "location_sample");
        if (!_infoLocationSample.equals(_existingLocationSample)) {
          return new RoomOpenHelper.ValidationResult(false, "location_sample(com.beeo.app.data.local.LocationSampleEntity).\n"
                  + " Expected:\n" + _infoLocationSample + "\n"
                  + " Found:\n" + _existingLocationSample);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "43042892311c5de6e78e134a09e8d24b", "2ca4d8690b6729aff4f11210db4f6a83");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "daily_activity","habit_alert","location_sample");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `daily_activity`");
      _db.execSQL("DELETE FROM `habit_alert`");
      _db.execSQL("DELETE FROM `location_sample`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DailyActivityDao.class, DailyActivityDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HabitAlertDao.class, HabitAlertDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LocationSampleDao.class, LocationSampleDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public DailyActivityDao dailyActivityDao() {
    if (_dailyActivityDao != null) {
      return _dailyActivityDao;
    } else {
      synchronized(this) {
        if(_dailyActivityDao == null) {
          _dailyActivityDao = new DailyActivityDao_Impl(this);
        }
        return _dailyActivityDao;
      }
    }
  }

  @Override
  public HabitAlertDao habitAlertDao() {
    if (_habitAlertDao != null) {
      return _habitAlertDao;
    } else {
      synchronized(this) {
        if(_habitAlertDao == null) {
          _habitAlertDao = new HabitAlertDao_Impl(this);
        }
        return _habitAlertDao;
      }
    }
  }

  @Override
  public LocationSampleDao locationSampleDao() {
    if (_locationSampleDao != null) {
      return _locationSampleDao;
    } else {
      synchronized(this) {
        if(_locationSampleDao == null) {
          _locationSampleDao = new LocationSampleDao_Impl(this);
        }
        return _locationSampleDao;
      }
    }
  }
}
