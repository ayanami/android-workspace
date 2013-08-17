/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 手牌ステータス<code>Dto</code>クラスです。
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
public class HandsStatusDto {

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

    /** 手牌(インデックス) */
    public Integer[] hands;

    /** 手牌(利用回数) */
    public int[] useCnts = new int[34];

    /** 雀頭 */
    public int eyes;

    /** 順子リスト */
    public List<Integer[]> chows = new ArrayList<Integer[]>();

    /** 刻子リスト */
    public List<Integer> pungs = new ArrayList<Integer>();
    
    /** 面前かを判定するフラグ */
    public boolean conceal = true;

    /** 七対子かを判定するフラグ */
    public boolean isSevenPairs;

    /** 役満カウンター */
    public int grandSlamCounter;

    /** 国士無双かを判定するフラグ */
    public boolean isThirtheenOrphans;
    
    /** 九蓮宝燈かを判定するフラグ */
    public boolean isNineTreasures;
    
    /** 四暗刻かを判定するフラグ */
    public boolean isFourConcealedTriples;

    /** 字一色かを判定するフラグ */
    public boolean isAllHonors;

    /** 大四喜かを判定するフラグ */
    public boolean isBigFourWinds;

    /** 小四喜かを判定するフラグ */
    public boolean isSmallFourWinds;
    
    /** 緑一色かを判定するフラグ */
    public boolean isAllGreen;
    
    /** 大三元かを判定するフラグ */
    public boolean isBigDragons;
    
    /** 清老頭かを判定するフラグ */
    public boolean isTerminals;
    
    /** 断ヤオかを判定するフラグ */
    public boolean isAllSimples;

    /** 翻牌数 */
    public int valueTilesCnt;

    /** 混老頭かを判定するフラグ */
    public boolean isAllTerminalsAndHonors;

    /** 小三元かを判定するフラグ */
    public boolean isLittleDragons;

    /** 混一色かを判定するフラグ */
    public boolean isHalfFlash;

    /** 清一色かを判定するフラグ */
    public boolean isFullFlash;

    /** 平和かを判定するフラグ */
    public boolean isAllRuns;
    
    /** 一盃口かを判定するフラグ */
    public boolean isDoubleRun;

    /** 対々和かを判定するフラグ */
    public boolean isAllTriples;

    /** 二盃口かを判定するフラグ */
    public boolean isTwoDoubleRuns;

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
        Arrays.fill(useCnts, 0);
        chows.clear();
        pungs.clear();
        conceal = true;
        isSevenPairs = false;
        grandSlamCounter = 0;
        isThirtheenOrphans = false;
        isNineTreasures = false;
        isFourConcealedTriples = false;
        isAllHonors = false;
        isBigFourWinds = false;
        isSmallFourWinds = false;
        isAllGreen = false;
        isBigDragons = false;
        isTerminals = false;
        isAllSimples = false;
        valueTilesCnt = 0;
        isAllTerminalsAndHonors = false;
        isLittleDragons = false;
        isHalfFlash = false;
        isFullFlash = false;
        isAllRuns = false;
        isAllTriples = false;
        isDoubleRun = false;
        fu = 0;
        fan = 0;
        score = 0;
    }
}
