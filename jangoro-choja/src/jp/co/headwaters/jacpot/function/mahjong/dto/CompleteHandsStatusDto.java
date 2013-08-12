/**
 * 
 */
package jp.co.headwaters.jacpot.function.mahjong.dto;

/**
 * <p>
 * あがり手牌ステータス<code>Dto</code>クラスです。
 * </p>
 * 
 * 作成日：2013/08/12<br>
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
 * <td>2013/08/12</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class CompleteHandsStatusDto {

    /** 場 {0:東, 1:南} */
    public int round;

    /** 風{0:東, 1:南, 2:西, 3:北} */
    public int wind;

    /** ドラ(リソースID) */
    public int dragon;
    
    /** 親かを判定するフラグ */
    public boolean isDealer;

    /** ロンかを判定するフラグ */
    public boolean isRon;
    
    /** あがり牌 */
    public int winningTile;
    
    /** 役満かを判定するフラグ */
    public boolean isGrandSlam;
    
    /** 符 */
    public int fu;
    
    /** 翻 */
    public int fan;
    
    /** ドラ数 */
    public int dragonCnt;
}
