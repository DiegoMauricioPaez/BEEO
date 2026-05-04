package com.beeo.app.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.beeo.app.domain.model.RoutePoint;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DailyActivityDao_Impl implements DailyActivityDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyActivityEntity> __insertionAdapterOfDailyActivityEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<DailyActivityEntity> __updateAdapterOfDailyActivityEntity;

  public DailyActivityDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyActivityEntity = new EntityInsertionAdapter<DailyActivityEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_activity` (`id`,`dateEpochDay`,`steps`,`distanceMeters`,`activeMinutes`,`locationChanges`,`homeTimeMinutes`,`maxDistanceFromHome`,`routePoints`,`zones`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyActivityEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDateEpochDay());
        statement.bindLong(3, entity.getSteps());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindLong(5, entity.getActiveMinutes());
        statement.bindLong(6, entity.getLocationChanges());
        statement.bindLong(7, entity.getHomeTimeMinutes());
        statement.bindDouble(8, entity.getMaxDistanceFromHome());
        final String _tmp = __converters.fromRoutePoints(entity.getRoutePoints());
        statement.bindString(9, _tmp);
        final String _tmp_1 = __converters.fromStringList(entity.getZones());
        statement.bindString(10, _tmp_1);
      }
    };
    this.__updateAdapterOfDailyActivityEntity = new EntityDeletionOrUpdateAdapter<DailyActivityEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `daily_activity` SET `id` = ?,`dateEpochDay` = ?,`steps` = ?,`distanceMeters` = ?,`activeMinutes` = ?,`locationChanges` = ?,`homeTimeMinutes` = ?,`maxDistanceFromHome` = ?,`routePoints` = ?,`zones` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyActivityEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDateEpochDay());
        statement.bindLong(3, entity.getSteps());
        statement.bindDouble(4, entity.getDistanceMeters());
        statement.bindLong(5, entity.getActiveMinutes());
        statement.bindLong(6, entity.getLocationChanges());
        statement.bindLong(7, entity.getHomeTimeMinutes());
        statement.bindDouble(8, entity.getMaxDistanceFromHome());
        final String _tmp = __converters.fromRoutePoints(entity.getRoutePoints());
        statement.bindString(9, _tmp);
        final String _tmp_1 = __converters.fromStringList(entity.getZones());
        statement.bindString(10, _tmp_1);
        statement.bindLong(11, entity.getId());
      }
    };
  }

  @Override
  public Object insertActivity(final DailyActivityEntity activity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyActivityEntity.insert(activity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateActivity(final DailyActivityEntity activity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfDailyActivityEntity.handle(activity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyActivityEntity>> getAllActivities() {
    final String _sql = "SELECT * FROM daily_activity ORDER BY dateEpochDay DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_activity"}, new Callable<List<DailyActivityEntity>>() {
      @Override
      @NonNull
      public List<DailyActivityEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpochDay");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfLocationChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "locationChanges");
          final int _cursorIndexOfHomeTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "homeTimeMinutes");
          final int _cursorIndexOfMaxDistanceFromHome = CursorUtil.getColumnIndexOrThrow(_cursor, "maxDistanceFromHome");
          final int _cursorIndexOfRoutePoints = CursorUtil.getColumnIndexOrThrow(_cursor, "routePoints");
          final int _cursorIndexOfZones = CursorUtil.getColumnIndexOrThrow(_cursor, "zones");
          final List<DailyActivityEntity> _result = new ArrayList<DailyActivityEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyActivityEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDateEpochDay;
            _tmpDateEpochDay = _cursor.getLong(_cursorIndexOfDateEpochDay);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpLocationChanges;
            _tmpLocationChanges = _cursor.getInt(_cursorIndexOfLocationChanges);
            final int _tmpHomeTimeMinutes;
            _tmpHomeTimeMinutes = _cursor.getInt(_cursorIndexOfHomeTimeMinutes);
            final float _tmpMaxDistanceFromHome;
            _tmpMaxDistanceFromHome = _cursor.getFloat(_cursorIndexOfMaxDistanceFromHome);
            final List<RoutePoint> _tmpRoutePoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRoutePoints);
            _tmpRoutePoints = __converters.toRoutePoints(_tmp);
            final List<String> _tmpZones;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfZones);
            _tmpZones = __converters.toStringList(_tmp_1);
            _item = new DailyActivityEntity(_tmpId,_tmpDateEpochDay,_tmpSteps,_tmpDistanceMeters,_tmpActiveMinutes,_tmpLocationChanges,_tmpHomeTimeMinutes,_tmpMaxDistanceFromHome,_tmpRoutePoints,_tmpZones);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getActivityByDate(final long epochDay,
      final Continuation<? super DailyActivityEntity> $completion) {
    final String _sql = "SELECT * FROM daily_activity WHERE dateEpochDay = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, epochDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyActivityEntity>() {
      @Override
      @Nullable
      public DailyActivityEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpochDay");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfLocationChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "locationChanges");
          final int _cursorIndexOfHomeTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "homeTimeMinutes");
          final int _cursorIndexOfMaxDistanceFromHome = CursorUtil.getColumnIndexOrThrow(_cursor, "maxDistanceFromHome");
          final int _cursorIndexOfRoutePoints = CursorUtil.getColumnIndexOrThrow(_cursor, "routePoints");
          final int _cursorIndexOfZones = CursorUtil.getColumnIndexOrThrow(_cursor, "zones");
          final DailyActivityEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDateEpochDay;
            _tmpDateEpochDay = _cursor.getLong(_cursorIndexOfDateEpochDay);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpLocationChanges;
            _tmpLocationChanges = _cursor.getInt(_cursorIndexOfLocationChanges);
            final int _tmpHomeTimeMinutes;
            _tmpHomeTimeMinutes = _cursor.getInt(_cursorIndexOfHomeTimeMinutes);
            final float _tmpMaxDistanceFromHome;
            _tmpMaxDistanceFromHome = _cursor.getFloat(_cursorIndexOfMaxDistanceFromHome);
            final List<RoutePoint> _tmpRoutePoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRoutePoints);
            _tmpRoutePoints = __converters.toRoutePoints(_tmp);
            final List<String> _tmpZones;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfZones);
            _tmpZones = __converters.toStringList(_tmp_1);
            _result = new DailyActivityEntity(_tmpId,_tmpDateEpochDay,_tmpSteps,_tmpDistanceMeters,_tmpActiveMinutes,_tmpLocationChanges,_tmpHomeTimeMinutes,_tmpMaxDistanceFromHome,_tmpRoutePoints,_tmpZones);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyActivityEntity>> getRecentActivities(final int limit) {
    final String _sql = "SELECT * FROM daily_activity ORDER BY dateEpochDay DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_activity"}, new Callable<List<DailyActivityEntity>>() {
      @Override
      @NonNull
      public List<DailyActivityEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpochDay");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfLocationChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "locationChanges");
          final int _cursorIndexOfHomeTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "homeTimeMinutes");
          final int _cursorIndexOfMaxDistanceFromHome = CursorUtil.getColumnIndexOrThrow(_cursor, "maxDistanceFromHome");
          final int _cursorIndexOfRoutePoints = CursorUtil.getColumnIndexOrThrow(_cursor, "routePoints");
          final int _cursorIndexOfZones = CursorUtil.getColumnIndexOrThrow(_cursor, "zones");
          final List<DailyActivityEntity> _result = new ArrayList<DailyActivityEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyActivityEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDateEpochDay;
            _tmpDateEpochDay = _cursor.getLong(_cursorIndexOfDateEpochDay);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpLocationChanges;
            _tmpLocationChanges = _cursor.getInt(_cursorIndexOfLocationChanges);
            final int _tmpHomeTimeMinutes;
            _tmpHomeTimeMinutes = _cursor.getInt(_cursorIndexOfHomeTimeMinutes);
            final float _tmpMaxDistanceFromHome;
            _tmpMaxDistanceFromHome = _cursor.getFloat(_cursorIndexOfMaxDistanceFromHome);
            final List<RoutePoint> _tmpRoutePoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRoutePoints);
            _tmpRoutePoints = __converters.toRoutePoints(_tmp);
            final List<String> _tmpZones;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfZones);
            _tmpZones = __converters.toStringList(_tmp_1);
            _item = new DailyActivityEntity(_tmpId,_tmpDateEpochDay,_tmpSteps,_tmpDistanceMeters,_tmpActiveMinutes,_tmpLocationChanges,_tmpHomeTimeMinutes,_tmpMaxDistanceFromHome,_tmpRoutePoints,_tmpZones);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DailyActivityEntity>> getActivitiesInRange(final long startDay,
      final long endDay) {
    final String _sql = "SELECT * FROM daily_activity WHERE dateEpochDay BETWEEN ? AND ? ORDER BY dateEpochDay ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDay);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_activity"}, new Callable<List<DailyActivityEntity>>() {
      @Override
      @NonNull
      public List<DailyActivityEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpochDay");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfLocationChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "locationChanges");
          final int _cursorIndexOfHomeTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "homeTimeMinutes");
          final int _cursorIndexOfMaxDistanceFromHome = CursorUtil.getColumnIndexOrThrow(_cursor, "maxDistanceFromHome");
          final int _cursorIndexOfRoutePoints = CursorUtil.getColumnIndexOrThrow(_cursor, "routePoints");
          final int _cursorIndexOfZones = CursorUtil.getColumnIndexOrThrow(_cursor, "zones");
          final List<DailyActivityEntity> _result = new ArrayList<DailyActivityEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyActivityEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDateEpochDay;
            _tmpDateEpochDay = _cursor.getLong(_cursorIndexOfDateEpochDay);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpLocationChanges;
            _tmpLocationChanges = _cursor.getInt(_cursorIndexOfLocationChanges);
            final int _tmpHomeTimeMinutes;
            _tmpHomeTimeMinutes = _cursor.getInt(_cursorIndexOfHomeTimeMinutes);
            final float _tmpMaxDistanceFromHome;
            _tmpMaxDistanceFromHome = _cursor.getFloat(_cursorIndexOfMaxDistanceFromHome);
            final List<RoutePoint> _tmpRoutePoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRoutePoints);
            _tmpRoutePoints = __converters.toRoutePoints(_tmp);
            final List<String> _tmpZones;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfZones);
            _tmpZones = __converters.toStringList(_tmp_1);
            _item = new DailyActivityEntity(_tmpId,_tmpDateEpochDay,_tmpSteps,_tmpDistanceMeters,_tmpActiveMinutes,_tmpLocationChanges,_tmpHomeTimeMinutes,_tmpMaxDistanceFromHome,_tmpRoutePoints,_tmpZones);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLatestActivity(final Continuation<? super DailyActivityEntity> $completion) {
    final String _sql = "SELECT * FROM daily_activity ORDER BY dateEpochDay DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyActivityEntity>() {
      @Override
      @Nullable
      public DailyActivityEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateEpochDay = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEpochDay");
          final int _cursorIndexOfSteps = CursorUtil.getColumnIndexOrThrow(_cursor, "steps");
          final int _cursorIndexOfDistanceMeters = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceMeters");
          final int _cursorIndexOfActiveMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "activeMinutes");
          final int _cursorIndexOfLocationChanges = CursorUtil.getColumnIndexOrThrow(_cursor, "locationChanges");
          final int _cursorIndexOfHomeTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "homeTimeMinutes");
          final int _cursorIndexOfMaxDistanceFromHome = CursorUtil.getColumnIndexOrThrow(_cursor, "maxDistanceFromHome");
          final int _cursorIndexOfRoutePoints = CursorUtil.getColumnIndexOrThrow(_cursor, "routePoints");
          final int _cursorIndexOfZones = CursorUtil.getColumnIndexOrThrow(_cursor, "zones");
          final DailyActivityEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDateEpochDay;
            _tmpDateEpochDay = _cursor.getLong(_cursorIndexOfDateEpochDay);
            final int _tmpSteps;
            _tmpSteps = _cursor.getInt(_cursorIndexOfSteps);
            final float _tmpDistanceMeters;
            _tmpDistanceMeters = _cursor.getFloat(_cursorIndexOfDistanceMeters);
            final int _tmpActiveMinutes;
            _tmpActiveMinutes = _cursor.getInt(_cursorIndexOfActiveMinutes);
            final int _tmpLocationChanges;
            _tmpLocationChanges = _cursor.getInt(_cursorIndexOfLocationChanges);
            final int _tmpHomeTimeMinutes;
            _tmpHomeTimeMinutes = _cursor.getInt(_cursorIndexOfHomeTimeMinutes);
            final float _tmpMaxDistanceFromHome;
            _tmpMaxDistanceFromHome = _cursor.getFloat(_cursorIndexOfMaxDistanceFromHome);
            final List<RoutePoint> _tmpRoutePoints;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfRoutePoints);
            _tmpRoutePoints = __converters.toRoutePoints(_tmp);
            final List<String> _tmpZones;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfZones);
            _tmpZones = __converters.toStringList(_tmp_1);
            _result = new DailyActivityEntity(_tmpId,_tmpDateEpochDay,_tmpSteps,_tmpDistanceMeters,_tmpActiveMinutes,_tmpLocationChanges,_tmpHomeTimeMinutes,_tmpMaxDistanceFromHome,_tmpRoutePoints,_tmpZones);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageSteps(final long startDay, final long endDay,
      final Continuation<? super Float> $completion) {
    final String _sql = "SELECT AVG(steps) FROM daily_activity WHERE dateEpochDay BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDay);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM daily_activity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
