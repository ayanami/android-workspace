/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity;

import static jp.co.headwaters.jacpot.mahjong.entity.names.E001StatusEntityNames.*;

import java.util.ArrayList;
import java.util.List;

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

    /** テーブル名 */
    private static final String TABLE_NAME = "E001_STATUS";

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

    /** 七対子 */
    public int sevenPairs;

    /** 断ヤオ */
    public int allSimples;

    /** 混老頭 */
    public int allTerminalsAndHonors;

    /** 小三元 */
    public int littleDragons;

    /** 混一色 */
    public int halfFlash;

    /** 清一色 */
    public int fullFlash;

    /** 平和 */
    public int allRuns;

    /** 一盃口 */
    public int doubleRun;

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

    /** 二盃口 */
    public int twoDoubleRuns;

    /** 純全帯 */
    public int pureOutsideHand;

    /** 三暗刻 */
    public int threeConcealedTriples;
    
    /** 最高得点 */
    public int bestScore;
    
    /** 称号 */
    public String title;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("serial")
    @Override
    protected List<String> getDataDefs() {

        List<String> dataDefs = new ArrayList<String>() {

            {
                add(THIRTEEN_ORPHANS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(NINE_TRESURES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FOUR_CONCEALED_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_HONORS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_FOUR_WINDS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SMALL_FOUR_WINDS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_GREEN + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_DRAGONS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TERMINALS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SEVEN_PAIRS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_SIMPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TERMINALS_AND_HONORS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(LITTLE_DRAGONS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(HALF_FLASH + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_FLASH + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(DOUBLE_RUN + ATTRIBUTE_INTEGER_NOT_NULL);
                add(MIXED_OUTSIDE_HAND + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_STRAIGHT + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TWO_DOUBLE_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(PURE_OUTSIDE_HAND + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_CONCEALED_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BEST_SCORE + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TITLE + ATTRIBUTE_TEXT_NOT_NULL);
            }
        };

        return dataDefs;
    }

}
