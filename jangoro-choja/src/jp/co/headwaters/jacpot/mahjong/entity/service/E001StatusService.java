/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity.service;

import static jp.co.headwaters.jacpot.mahjong.entity.names.E001StatusEntityNames.*;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.mahjong.common.AbstractDataAccessService;
import jp.co.headwaters.jacpot.mahjong.constant.CommonConst;
import jp.co.headwaters.jacpot.mahjong.constant.MahjongConst;
import jp.co.headwaters.jacpot.mahjong.entity.E001StatusEntity;
import jp.co.headwaters.jacpot.mahjong.entity.dto.E001StatusDto;
import jp.co.headwaters.jacpot.mahjong.util.ScoreUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * <p>
 * ステータス<code>Service</code>クラスです。
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
public class E001StatusService extends AbstractDataAccessService<E001StatusEntity, E001StatusDto> {

    /** テーブル名 */
    private static final String TABLE_NAME = "E001_STATUS";

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     */
    public E001StatusService(Context context) {
        super(context, TABLE_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("serial")
    @Override
    protected List<String> getDataDefs() {

        List<String> dataDefs = new ArrayList<String>() {

            {
                add(TITLE + ATTRIBUTE_TEXT_NOT_NULL);
                add(BEST_SCORE + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_SIMPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(DOUBLE_RUN + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SEVEN_PAIRS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(MIXED_OUTSIDE_HAND + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_STRAIGHT + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_CONCEALED_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TERMINALS_AND_HONORS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(LITTLE_DRAGONS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TWO_DOUBLE_RUNS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(PURE_OUTSIDE_HAND + ATTRIBUTE_INTEGER_NOT_NULL);
                add(HALF_FLASH + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_FLASH + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THIRTEEN_ORPHANS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(NINE_TRESURES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FOUR_CONCEALED_TRIPLES + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_HONORS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_FOUR_WINDS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SMALL_FOUR_WINDS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_GREEN + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_DRAGONS + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TERMINALS + ATTRIBUTE_INTEGER_NOT_NULL);
            }
        };

        return dataDefs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentValues getContentValues(E001StatusEntity entity) {
        ContentValues values = new ContentValues();
        values.put(TITLE, entity.title);
        values.put(BEST_SCORE, entity.bestScore);
        values.put(ALL_SIMPLES, entity.allSimples);
        values.put(ALL_RUNS, entity.allRuns);
        values.put(DOUBLE_RUN, entity.doubleRun);
        values.put(SEVEN_PAIRS, entity.sevenPairs);
        values.put(MIXED_OUTSIDE_HAND, entity.mixedOutsideHand);
        values.put(THREE_COLOR_RUNS, entity.threeColorRuns);
        values.put(FULL_STRAIGHT, entity.fullStraight);
        values.put(ALL_TRIPLES, entity.allTriples);
        values.put(THREE_COLOR_TRIPLES, entity.threeColorTriples);
        values.put(THREE_CONCEALED_TRIPLES, entity.threeConcealedTriples);
        values.put(ALL_TERMINALS_AND_HONORS, entity.allTerminalsAndHonors);
        values.put(LITTLE_DRAGONS, entity.littleDragons);
        values.put(TWO_DOUBLE_RUNS, entity.twoDoubleRuns);
        values.put(PURE_OUTSIDE_HAND, entity.pureOutsideHand);
        values.put(HALF_FLASH, entity.halfFlash);
        values.put(FULL_FLASH, entity.fullFlash);
        values.put(THIRTEEN_ORPHANS, entity.thirteenOrphans);
        values.put(NINE_TRESURES, entity.nineTresures);
        values.put(FOUR_CONCEALED_TRIPLES, entity.fourConcealedTriples);
        values.put(ALL_HONORS, entity.allHonors);
        values.put(BIG_FOUR_WINDS, entity.bigFourWinds);
        values.put(SMALL_FOUR_WINDS, entity.smallFourWinds);
        values.put(ALL_GREEN, entity.allGreen);
        values.put(BIG_DRAGONS, entity.bigDragons);
        values.put(TERMINALS, entity.terminals);
        return values;
    }

    /**
     * 
     * データを設定した{@link E001StatusEntity}を返却します。
     * 
     * 
     * @return {@link E001StatusEntity}
     */
    public E001StatusEntity find() {

        E001StatusEntity entity = new E001StatusEntity();
        Cursor c = super.getAllResult();

        int i = 0;
        if (c.moveToFirst()) {
            entity.id = c.getLong(i++);
            entity.title = c.getString(i++);
            entity.bestScore = c.getInt(i++);
            entity.allSimples = c.getInt(i++);
            entity.allRuns = c.getInt(i++);
            entity.doubleRun = c.getInt(i++);
            entity.sevenPairs = c.getInt(i++);
            entity.mixedOutsideHand = c.getInt(i++);
            entity.threeColorRuns = c.getInt(i++);
            entity.fullStraight = c.getInt(i++);
            entity.allTriples = c.getInt(i++);
            entity.threeColorTriples = c.getInt(i++);
            entity.threeConcealedTriples = c.getInt(i++);
            entity.allTerminalsAndHonors = c.getInt(i++);
            entity.littleDragons = c.getInt(i++);
            entity.twoDoubleRuns = c.getInt(i++);
            entity.pureOutsideHand = c.getInt(i++);
            entity.halfFlash = c.getInt(i++);
            entity.fullFlash = c.getInt(i++);
            entity.thirteenOrphans = c.getInt(i++);
            entity.nineTresures = c.getInt(i++);
            entity.fourConcealedTriples = c.getInt(i++);
            entity.allHonors = c.getInt(i++);
            entity.bigFourWinds = c.getInt(i++);
            entity.smallFourWinds = c.getInt(i++);
            entity.allGreen = c.getInt(i++);
            entity.bigDragons = c.getInt(i++);
            entity.terminals = c.getInt(i++);
            entity.lastUpdateDatetime = c.getString(i++);
        }
        c.close();

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("serial")
    @Override
    public List<E001StatusDto> toDisplay(final E001StatusEntity entity) {
        List<E001StatusDto> dataList = new ArrayList<E001StatusDto>() {

            {
                // ID、称号、対々和以外を表示
                add(new E001StatusDto(CommonConst.TEXT_BEST_SCORE,
                                      ScoreUtil.getFormatScore(entity.bestScore), null));
                add(new E001StatusDto(getArgs(MahjongConst.ALL_SIMPLES, entity.allSimples)));
                add(new E001StatusDto(getArgs(MahjongConst.ALL_RUNS, entity.allRuns)));
                add(new E001StatusDto(getArgs(MahjongConst.DOUBLE_RUN, entity.doubleRun)));
                add(new E001StatusDto(getArgs(MahjongConst.SEVEN_PAIRS, entity.sevenPairs)));
                add(new E001StatusDto(getArgs(MahjongConst.MIXED_OUTSIDE_HAND,
                                              entity.mixedOutsideHand)));
                add(new E001StatusDto(getArgs(MahjongConst.THREE_COLOR_RUNS, entity.threeColorRuns)));
                add(new E001StatusDto(getArgs(MahjongConst.FULL_STRAIGHT, entity.fullStraight)));
                add(new E001StatusDto(getArgs(MahjongConst.THREE_COLOR_TRIPLES,
                                              entity.threeColorTriples)));
                add(new E001StatusDto(getArgs(MahjongConst.THREE_CONCEALED_TRIPLES,
                                              entity.threeConcealedTriples)));
                add(new E001StatusDto(getArgs(MahjongConst.ALL_TERMINALS_AND_HONORS,
                                              entity.allTerminalsAndHonors)));
                add(new E001StatusDto(getArgs(MahjongConst.LITTLE_DRAGONS, entity.littleDragons)));
                add(new E001StatusDto(getArgs(MahjongConst.TWO_DOUBLE_RUNS, entity.twoDoubleRuns)));
                add(new E001StatusDto(getArgs(MahjongConst.PURE_OUTSIDE_HAND,
                                              entity.pureOutsideHand)));
                add(new E001StatusDto(getArgs(MahjongConst.HALF_FLASH, entity.halfFlash)));
                add(new E001StatusDto(getArgs(MahjongConst.FULL_FLASH, entity.fullFlash)));
                add(new E001StatusDto(
                                      getArgs(MahjongConst.THIRTEEN_ORPHANS, entity.thirteenOrphans)));
                add(new E001StatusDto(getArgs(MahjongConst.NINE_TRESURES, entity.nineTresures)));
                add(new E001StatusDto(getArgs(MahjongConst.FOUR_CONCEALED_TRIPLES,
                                              entity.fourConcealedTriples)));
                add(new E001StatusDto(getArgs(MahjongConst.ALL_HONORS, entity.allHonors)));
                add(new E001StatusDto(getArgs(MahjongConst.BIG_FOUR_WINDS, entity.bigFourWinds)));
                add(new E001StatusDto(getArgs(MahjongConst.SMALL_FOUR_WINDS, entity.smallFourWinds)));
                add(new E001StatusDto(getArgs(MahjongConst.ALL_GREEN, entity.allGreen)));
                add(new E001StatusDto(getArgs(MahjongConst.BIG_DRAGONS, entity.bigDragons)));
                add(new E001StatusDto(getArgs(MahjongConst.TERMINALS, entity.terminals)));
            }
        };

        return dataList;
    }

    /**
     * コンテンツとディスクリプションの配列を返却します。
     * 
     * @param contents コンテンツ
     * @param status ステータス
     * @return コンテンツとディスクリプションの配列
     */
    private String[] getArgs(String contents, int status) {

        String[] args = new String[2];
        if (status == 0) {
            args[0] = CommonConst.TEXT_HIDDNE;
            args[1] = CommonConst.TEXT_NOT_CLEAR;
        } else {
            args[0] = contents;
            args[1] = CommonConst.TEXT_CLEAR;
        }
        return args;
    }
}
