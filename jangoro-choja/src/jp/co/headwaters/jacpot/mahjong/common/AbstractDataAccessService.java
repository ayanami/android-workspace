/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>
 * {@link SQLiteOpenHelper}をラップしたデータアクセス<code>Service</code>抽象クラスです。
 * </p>
 * 
 * 作成日：2013/08/27<br>
 * 
 * <b>更新履歴</b><br>
 * <table border bgcolor="#ffffff">
 * <tr bgcolor="#ccccff">
 * <td>日付</td>
 * <td>欠陥管理番号</td>
 * <td>担当</td>
 * <td>変更点</td>
 * </tr>
 * <tr>
 * <td>2013/08/27</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 * @param <E> {@link AbstractEntity}型の<code>Entity</code>クラス。
 */
public abstract class AbstractDataAccessService<E> extends SQLiteOpenHelper {

    /** データベース名 */
    private static final String DATABASE_NAME = "jacpot.db";

    /** データベースバージョン */
    private static final int DATABASE_VERSION = 1;

    /** <code>column</code>(_id) */
    private static final String ID = "_id";

    /** <code>column</code>(last_update_datetime) */
    private static final String LAST_UPDATE_DATETIME = "last_update_datetime";

    /** 属性(<code>PRIMARY KEY</code>) */
    private static final String ATTRIBUTE_PRIMARY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT";

    /** <code>datetime</code>フォーマッタ */
    private static final SimpleDateFormat DATE_FORMAT_DATETIME =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** {@link SQLiteDatabase} */
    private SQLiteDatabase db;

    /** <code>Entity</code> */
    private E entity;

    /** テーブル名 */
    private String tableName;

    /**
     * 
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param entity <code>Entity</code>
     */
    public AbstractDataAccessService(Context context, E entity) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.entity = entity;
        this.tableName = ((AbstractEntity)this.entity).getTableName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE");
        query.append(" " + this.tableName + " (");
        query.append(ID + ATTRIBUTE_PRIMARY_KEY);
        for (String dataDef : ((AbstractEntity)this.entity).getDataDefs()) {
            query.append(", " + dataDef);
        }
        query.append(", " + LAST_UPDATE_DATETIME + AbstractEntity.ATTRIBUTE_TEXT_NOT_NULL);
        query.append(");");
        db.execSQL(query.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        StringBuilder query = new StringBuilder();
        query.append("DROP TABLE IF EXISTS");
        query.append(" " + this.tableName);
        db.execSQL(query.toString());

        this.onCreate(db);
    }

    /**
     * 
     * {@link SQLiteDatabase}をオープンします。
     * 
     */
    public void open() {
        this.db = this.getWritableDatabase();
    }

    /**
     * {@link SQLiteDatabase}をクローズします。
     */
    public void close() {
        this.close();
    }

    /**
     * 
     * レコード件数を返却します。
     * 
     * @return レコード件数
     */
    public int getCount() {
        Cursor c = this.getAllResult();
        int count = c.getCount();
        c.close();
        return count;
    }

    /**
     * 
     * 全レコードを格納した{@link Cursor}を返却します。
     * 
     * @return 全レコードを格納した{@link Cursor}
     */
    protected Cursor getAllResult() {
        return this.getResult(null, null);
    }

    /**
     * 
     * 検索条件に合致した{@link Cursor}を返却します。
     * 
     * @param selection SQL条件(hoge = ?の形式で指定)
     * @param selectionArgs バインドパラメータ配列
     * @return 検索条件に合致した{@link Cursor}
     */
    protected Cursor getResult(String selection, String[] selectionArgs) {
        return this.db.query(this.tableName, null, selection, selectionArgs, null, null, null);
    }

    /**
     * 
     * レコードを挿入します。
     * 
     * @param entity <code>Entity</code>
     * @return 挿入レコードのID
     */
    public long insert(E entity) {

        ContentValues values = getContentValues(entity);
        values.put(LAST_UPDATE_DATETIME, DATE_FORMAT_DATETIME.format(new Date()));
        return this.db.insert(this.tableName, null, values);
    }

    /**
     * 
     * レコードを更新します。
     * 
     * @param entity <code>Entity</code>
     * @return 挿入レコードのID
     */
    public int update(E entity) {

        ContentValues values = getContentValues(entity);
        values.put(LAST_UPDATE_DATETIME, DATE_FORMAT_DATETIME.format(new Date()));
        return this.db.update(this.tableName, values, ID + " = ?",
                              new String[] {String.valueOf(((AbstractEntity)entity).id)});
    }

    /**
     * 
     * {@link ContentValues}を返却します。
     * 
     * @param entity <code>Entity</code>
     * @return {@link ContentValues}
     */
    protected abstract ContentValues getContentValues(E entity);

}
