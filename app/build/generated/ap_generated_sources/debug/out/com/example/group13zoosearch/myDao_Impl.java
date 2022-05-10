package com.example.group13zoosearch;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class myDao_Impl implements myDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ZooData> __insertionAdapterOfZooData;

  private final EntityDeletionOrUpdateAdapter<ZooData> __deletionAdapterOfZooData;

  private final EntityDeletionOrUpdateAdapter<ZooData> __updateAdapterOfZooData;

  public myDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfZooData = new EntityInsertionAdapter<ZooData>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `zoo_items` (`mid`,`id`,`kind`,`name`,`tags`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ZooData value) {
        stmt.bindLong(1, value.mid);
        if (value.id == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.id);
        }
        if (value.kind == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.kind);
        }
        if (value.name == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.name);
        }
        final String _tmp = Converters.fromListToJson(value.tags);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
      }
    };
    this.__deletionAdapterOfZooData = new EntityDeletionOrUpdateAdapter<ZooData>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `zoo_items` WHERE `mid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ZooData value) {
        stmt.bindLong(1, value.mid);
      }
    };
    this.__updateAdapterOfZooData = new EntityDeletionOrUpdateAdapter<ZooData>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `zoo_items` SET `mid` = ?,`id` = ?,`kind` = ?,`name` = ?,`tags` = ? WHERE `mid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ZooData value) {
        stmt.bindLong(1, value.mid);
        if (value.id == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.id);
        }
        if (value.kind == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.kind);
        }
        if (value.name == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.name);
        }
        final String _tmp = Converters.fromListToJson(value.tags);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        stmt.bindLong(6, value.mid);
      }
    };
  }

  @Override
  public long insert(final ZooData vertexInfo) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfZooData.insertAndReturnId(vertexInfo);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<ZooData> zooItems) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfZooData.insertAndReturnIdsList(zooItems);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final ZooData vertexInfo) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfZooData.handle(vertexInfo);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final ZooData vertexInfo) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfZooData.handle(vertexInfo);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public ZooData get(final long id) {
    final String _sql = "SELECT * FROM `zoo_items` WHERE `mid`=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMid = CursorUtil.getColumnIndexOrThrow(_cursor, "mid");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfKind = CursorUtil.getColumnIndexOrThrow(_cursor, "kind");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final ZooData _result;
      if(_cursor.moveToFirst()) {
        _result = new ZooData();
        _result.mid = _cursor.getLong(_cursorIndexOfMid);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getString(_cursorIndexOfId);
        }
        if (_cursor.isNull(_cursorIndexOfKind)) {
          _result.kind = null;
        } else {
          _result.kind = _cursor.getString(_cursorIndexOfKind);
        }
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfTags);
        }
        _result.tags = Converters.fromJsonToList(_tmp);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<ZooData> getAll() {
    final String _sql = "SELECT * FROM `zoo_items`";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfMid = CursorUtil.getColumnIndexOrThrow(_cursor, "mid");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfKind = CursorUtil.getColumnIndexOrThrow(_cursor, "kind");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
      final List<ZooData> _result = new ArrayList<ZooData>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final ZooData _item;
        _item = new ZooData();
        _item.mid = _cursor.getLong(_cursorIndexOfMid);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getString(_cursorIndexOfId);
        }
        if (_cursor.isNull(_cursorIndexOfKind)) {
          _item.kind = null;
        } else {
          _item.kind = _cursor.getString(_cursorIndexOfKind);
        }
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfTags)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfTags);
        }
        _item.tags = Converters.fromJsonToList(_tmp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
