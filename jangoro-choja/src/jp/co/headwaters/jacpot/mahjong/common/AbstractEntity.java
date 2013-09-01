/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.common;


/**
 * <p>
 * <code>Entity</code>抽象クラスです。
 * </p>
 * 
 * 作成日：2013/08/30<br>
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
 * <td>2013/08/30</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public abstract class AbstractEntity {

    /** {@link android.database.sqlite.SQLiteDatabase}の<code>primary key</code> */
    public long id;

    /** 最新更新日時 */
    public String lastUpdateDatetime;

}
