package com.beeo.app.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class HabitAlertDao_Impl implements HabitAlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HabitAlertEntity> __insertionAdapterOfHabitAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsRead;

  private final SharedSQLiteStatement __preparedStmtOfMarkAllAsRead;

  public HabitAlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHabitAlertEntity = new EntityInsertionAdapter<HabitAlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `habit_alert` (`id`,`timestamp`,`type`,`message`,`severity`,`isRead`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HabitAlertEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindString(3, entity.getType());
        statement.bindString(4, entity.getMessage());
        statement.bindString(5, entity.getSeverity());
        final int _tmp = entity.isRead() ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__preparedStmtOfMarkAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE habit_alert SET isRead = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAllAsRead = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE habit_alert SET isRead = 1";
        return _query;
      }
    };
  }

  @Override
  public Object insertAlert(final HabitAlertEntity alert,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfHabitAlertEntity.insertAndReturnId(alert);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsRead(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsRead.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markAllAsRead(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAllAsRead.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAllAsRead.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HabitAlertEntity>> getAllAlerts() {
    final String _sql = "SELECT * FROM habit_alert ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"habit_alert"}, new Callable<List<HabitAlertEntity>>() {
      @Override
      @NonNull
      public List<HabitAlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<HabitAlertEntity> _result = new ArrayList<HabitAlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitAlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final String _tmpSeverity;
            _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            final boolean _tmpIsRead;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp != 0;
            _item = new HabitAlertEntity(_tmpId,_tmpTimestamp,_tmpType,_tmpMessage,_tmpSeverity,_tmpIsRead);
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
  public Flow<List<HabitAlertEntity>> getUnreadAlerts() {
    final String _sql = "SELECT * FROM habit_alert WHERE isRead = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"habit_alert"}, new Callable<List<HabitAlertEntity>>() {
      @Override
      @NonNull
      public List<HabitAlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfIsRead = CursorUtil.getColumnIndexOrThrow(_cursor, "isRead");
          final List<HabitAlertEntity> _result = new ArrayList<HabitAlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HabitAlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpMessage;
            _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            final String _tmpSeverity;
            _tmpSeverity = _cursor.getString(_cursorIndexOfSeverity);
            final boolean _tmpIsRead;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRead);
            _tmpIsRead = _tmp != 0;
            _item = new HabitAlertEntity(_tmpId,_tmpTimestamp,_tmpType,_tmpMessage,_tmpSeverity,_tmpIsRead);
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
  public Flow<Integer> getUnreadCount() {
    final String _sql = "SELECT COUNT(*) FROM habit_alert WHERE isRead = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"habit_alert"}, new Callable<Integer>() {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
