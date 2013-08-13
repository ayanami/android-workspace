/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.common;

import android.view.View;


/**
 * <p>
 * コールバックリスナーを実装するためのインターフェースです。
 * </p>
 * 
 * 作成日：2013/08/11<br>
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
 * <td>2013/08/11</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public interface CallbackListener {

    /**
     * 
     * コールバックメソッドです。
     * @param v 呼出し元{@link View}クラス
     */
    void callback(View v);
}
