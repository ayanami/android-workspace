/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.message;


/**
 * <p>
 * メッセージ定義クラスです。
 * </p>
 * 
 * 作成日：2013/09/03<br>
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
 * <td>2013/09/03</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class Message {
    
    /** INFO(雀ゴロ長者を終了しますか?) */
    public static final String I_MSG_FINISH = "雀ゴロ長者を終了しますか?";

    /** INFO({0}点) */
    public static final String I_MSG_SCORE = "{0}点";
    
    /** WARNING(手牌は13枚で構成してください。) */
    public static final String W_MSG_SPECIFIED_SIZE = "手牌は13枚で構成してください。";

    /**
     * メッセージクラスのため、コンストラクタをプロテクトします。
     */
    protected Message() {
        
    }

}
