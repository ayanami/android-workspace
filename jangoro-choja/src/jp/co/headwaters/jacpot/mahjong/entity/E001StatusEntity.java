/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity;

import jp.co.headwaters.jacpot.mahjong.common.AbstractEntity;

/**
 * <p>
 * ステータス<code>Entity</code>クラスです。
 * </p>
 * 
 * 作成日：2013/08/29<br>
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
 * <td>2013/08/29</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class E001StatusEntity extends AbstractEntity {

    /** 称号 */
    public String title;
    
    /** 最高得点 */
    public int bestScore;
    
    /** 断ヤオ */
    public int allSimples;

    /** 平和 */
    public int allRuns;

    /** 一盃口 */
    public int doubleRun;

    /** 七対子 */
    public int sevenPairs;

    /** 全帯 */
    public int mixedOutsideHand;

    /** 三色同順 */
    public int threeColorRuns;

    /** 一気通貫 */
    public int fullStraight;

    /** 対々和 */
    public int allTriples;

    /** 三色同刻 */
    public int threeColorTriples;

    /** 三暗刻 */
    public int threeConcealedTriples;
    
    /** 小三元 */
    public int littleDragons;

    /** 混老頭 */
    public int allTerminalsAndHonors;

    /** 二盃口 */
    public int twoDoubleRuns;

    /** 純全帯 */
    public int pureOutsideHand;

    /** 混一色 */
    public int halfFlash;

    /** 清一色 */
    public int fullFlash;

    /** 国士無双 */
    public int thirteenOrphans;
    
    /** 九蓮宝燈 */
    public int nineTresures;

    /** 四暗刻 */
    public int fourConcealedTriples;

    /** 字一色 */
    public int allHonors;

    /** 大四喜 */
    public int bigFourWinds;

    /** 小四喜 */
    public int smallFourWinds;

    /** 緑一色 */
    public int allGreen;

    /** 大三元 */
    public int bigDragons;

    /** 清老頭 */
    public int terminals;

    /**
     * 
     * 初期化処理です。
     *
     */
    public void init() {
        this.title = "";
        this.bestScore = 0;
        this.allSimples = 0;
        this.allRuns = 0;
        this.doubleRun = 0;
        this.sevenPairs = 0;
        this.mixedOutsideHand = 0;
        this.threeColorRuns = 0;
        this.fullStraight = 0;
        this.allTriples = 0;
        this.threeColorTriples = 0;
        this.threeConcealedTriples = 0;
        this.littleDragons = 0;
        this.allTerminalsAndHonors = 0;
        this.twoDoubleRuns = 0;
        this.pureOutsideHand = 0;
        this.halfFlash = 0;
        this.fullFlash = 0;
        this.thirteenOrphans = 0;
        this.nineTresures = 0;
        this.fourConcealedTriples = 0;
        this.allHonors = 0;
        this.bigFourWinds = 0;
        this.smallFourWinds = 0;
        this.allGreen = 0;
        this.bigDragons = 0;
        this.terminals = 0;
    }
}
