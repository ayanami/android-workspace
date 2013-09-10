/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity.dto;


/**
 * <p>
 * ステータス<code>Dto</code>クラスです。
 * </p>
 * 
 * 作成日：2013/09/04<br>
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
 * <td>2013/09/04</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class E001StatusDto {

    /** ヘッダー */
    public String header;
    
    /** コンテンツ */
    public String contents;
    
    /** ディスクリプション */
    public String desc;
    
    /**
     * コンストラクタです。
     * @param header ヘッダー
     * @param contents コンテンツ
     * @param desc ディスクリプション
     */
    public E001StatusDto(String header, String contents, String desc) {
        this.header = header;
        this.contents = contents;
        this.desc = desc;
    }
    /**
     * コンストラクタです。
     * @param args {0:コンテンツ, 1:ディスクリプション}
     */
    public E001StatusDto(String[] args) {
        this.contents = args[0];
        this.desc = args[1];
    }
    
}
