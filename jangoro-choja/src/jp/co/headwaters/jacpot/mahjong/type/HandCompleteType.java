/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.type;


/**
 * <p>
 * 役コンプリート<code>Type</code>クラスです。
 * </p>
 * 
 * 作成日：2013/09/12<br>
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
 * <td>2013/09/12</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public enum HandCompleteType {

    /**
     * ステータス:COMPLETE
     */
    COMPLETE,
    
    /**
     * ステータス:NOT COMPLETE
     */
    NOT_COMPLETE,
    
    /**
     * ステータス:なし
     */
    NOTHING;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    };
}
