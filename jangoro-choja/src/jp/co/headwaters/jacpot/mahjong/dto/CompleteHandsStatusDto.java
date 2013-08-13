/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /** ドラ数 */
    public int dragonCnt;

    /** 手牌(利用数) */
    public int[] hands;

    /** 雀頭 */
    public int head;

    /** 順子リスト */
    public List<Integer[]> chows = new ArrayList<Integer[]>();

    /** 刻子リスト */
    public List<Integer> pungs = new ArrayList<Integer>();

    /** 七対子かを判定するフラグ */
    public boolean isSevenPairs;

    /** 役満かを判定するフラグ */
    public boolean isGrandSlam;

    /** 国士無双かを判定するフラグ */
    public boolean isThirtheenOrphans;

    /** 断ヤオかを判定するフラグ */
    public boolean isAllSimples;

    /** 翻牌数 */
    public int valueTilesCnt;

    /** 平和かを判定するフラグ */
    public boolean isAllRuns;

    /** 符 */
    public int fu;

    /** 翻 */
    public int fan;

    /** 点数 */
    public int score;

    /**
     * 
     * 初期化処理です。
     * 
     */
    public void clear() {
        Arrays.fill(hands, 0);
        chows.clear();
        pungs.clear();
        isSevenPairs = false;
        isGrandSlam = false;
        isThirtheenOrphans = false;
        isAllSimples = false;
        valueTilesCnt = 0;
        isAllRuns = false;
        fu = 0;
        fan = 0;
        score = 0;
    }
}
