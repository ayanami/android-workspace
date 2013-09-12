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
import jp.co.headwaters.jacpot.mahjong.type.HandCompleteType;
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
                add(TITLE.toString() + ATTRIBUTE_TEXT_NOT_NULL);
                add(BEST_SCORE.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_SIMPLES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_RUNS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(DOUBLE_RUN.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SEVEN_PAIRS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(MIXED_OUTSIDE_HAND.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_RUNS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_STRAIGHT.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TRIPLES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_COLOR_TRIPLES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THREE_CONCEALED_TRIPLES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_TERMINALS_AND_HONORS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(LITTLE_DRAGONS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TWO_DOUBLE_RUNS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(PURE_OUTSIDE_HAND.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(HALF_FLASH.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FULL_FLASH.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(THIRTEEN_ORPHANS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(NINE_TRESURES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(FOUR_CONCEALED_TRIPLES.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_HONORS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_FOUR_WINDS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(SMALL_FOUR_WINDS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(ALL_GREEN.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(BIG_DRAGONS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
                add(TERMINALS.toString() + ATTRIBUTE_INTEGER_NOT_NULL);
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
        values.put(TITLE.toString(), entity.title);
        values.put(BEST_SCORE.toString(), entity.bestScore);
        values.put(ALL_SIMPLES.toString(), entity.allSimples);
        values.put(ALL_RUNS.toString(), entity.allRuns);
        values.put(DOUBLE_RUN.toString(), entity.doubleRun);
        values.put(SEVEN_PAIRS.toString(), entity.sevenPairs);
        values.put(MIXED_OUTSIDE_HAND.toString(), entity.mixedOutsideHand);
        values.put(THREE_COLOR_RUNS.toString(), entity.threeColorRuns);
        values.put(FULL_STRAIGHT.toString(), entity.fullStraight);
        values.put(ALL_TRIPLES.toString(), entity.allTriples);
        values.put(THREE_COLOR_TRIPLES.toString(), entity.threeColorTriples);
        values.put(THREE_CONCEALED_TRIPLES.toString(), entity.threeConcealedTriples);
        values.put(ALL_TERMINALS_AND_HONORS.toString(), entity.allTerminalsAndHonors);
        values.put(LITTLE_DRAGONS.toString(), entity.littleDragons);
        values.put(TWO_DOUBLE_RUNS.toString(), entity.twoDoubleRuns);
        values.put(PURE_OUTSIDE_HAND.toString(), entity.pureOutsideHand);
        values.put(HALF_FLASH.toString(), entity.halfFlash);
        values.put(FULL_FLASH.toString(), entity.fullFlash);
        values.put(THIRTEEN_ORPHANS.toString(), entity.thirteenOrphans);
        values.put(NINE_TRESURES.toString(), entity.nineTresures);
        values.put(FOUR_CONCEALED_TRIPLES.toString(), entity.fourConcealedTriples);
        values.put(ALL_HONORS.toString(), entity.allHonors);
        values.put(BIG_FOUR_WINDS.toString(), entity.bigFourWinds);
        values.put(SMALL_FOUR_WINDS.toString(), entity.smallFourWinds);
        values.put(ALL_GREEN.toString(), entity.allGreen);
        values.put(BIG_DRAGONS.toString(), entity.bigDragons);
        values.put(TERMINALS.toString(), entity.terminals);
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

        if (c.moveToFirst()) {
            entity.id = c.getLong(ID.getIndex());
            entity.title = c.getString(TITLE.getIndex());
            entity.bestScore = c.getInt(BEST_SCORE.getIndex());
            entity.allSimples = c.getInt(ALL_SIMPLES.getIndex());
            entity.allRuns = c.getInt(ALL_RUNS.getIndex());
            entity.doubleRun = c.getInt(DOUBLE_RUN.getIndex());
            entity.sevenPairs = c.getInt(SEVEN_PAIRS.getIndex());
            entity.mixedOutsideHand = c.getInt(MIXED_OUTSIDE_HAND.getIndex());
            entity.threeColorRuns = c.getInt(THREE_COLOR_RUNS.getIndex());
            entity.fullStraight = c.getInt(FULL_STRAIGHT.getIndex());
            entity.allTriples = c.getInt(ALL_TRIPLES.getIndex());
            entity.threeColorTriples = c.getInt(THREE_COLOR_TRIPLES.getIndex());
            entity.threeConcealedTriples = c.getInt(THREE_CONCEALED_TRIPLES.getIndex());
            entity.allTerminalsAndHonors = c.getInt(ALL_TERMINALS_AND_HONORS.getIndex());
            entity.littleDragons = c.getInt(LITTLE_DRAGONS.getIndex());
            entity.twoDoubleRuns = c.getInt(TWO_DOUBLE_RUNS.getIndex());
            entity.pureOutsideHand = c.getInt(PURE_OUTSIDE_HAND.getIndex());
            entity.halfFlash = c.getInt(HALF_FLASH.getIndex());
            entity.fullFlash = c.getInt(FULL_FLASH.getIndex());
            entity.thirteenOrphans = c.getInt(THIRTEEN_ORPHANS.getIndex());
            entity.nineTresures = c.getInt(NINE_TRESURES.getIndex());
            entity.fourConcealedTriples = c.getInt(FOUR_CONCEALED_TRIPLES.getIndex());
            entity.allHonors = c.getInt(ALL_HONORS.getIndex());
            entity.bigFourWinds = c.getInt(BIG_FOUR_WINDS.getIndex());
            entity.smallFourWinds = c.getInt(SMALL_FOUR_WINDS.getIndex());
            entity.allGreen = c.getInt(ALL_GREEN.getIndex());
            entity.bigDragons = c.getInt(BIG_DRAGONS.getIndex());
            entity.terminals = c.getInt(TERMINALS.getIndex());
            entity.lastUpdateDatetime = c.getString(LAST_UPDATE_DATETIME.getIndex());
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
                                      ScoreUtil.getFormatScore(entity.bestScore),
                                      HandCompleteType.NOTHING, null));
                add(createE001StatusDto(MahjongConst.ALL_SIMPLES, entity.allSimples));
                add(createE001StatusDto(MahjongConst.ALL_RUNS, entity.allRuns));
                add(createE001StatusDto(MahjongConst.DOUBLE_RUN, entity.doubleRun));
                add(createE001StatusDto(MahjongConst.SEVEN_PAIRS, entity.sevenPairs));
                add(createE001StatusDto(MahjongConst.MIXED_OUTSIDE_HAND, entity.mixedOutsideHand));
                add(createE001StatusDto(MahjongConst.THREE_COLOR_RUNS, entity.threeColorRuns));
                add(createE001StatusDto(MahjongConst.FULL_STRAIGHT, entity.fullStraight));
                add(createE001StatusDto(MahjongConst.THREE_COLOR_TRIPLES, entity.threeColorTriples));
                add(createE001StatusDto(MahjongConst.THREE_CONCEALED_TRIPLES,
                                        entity.threeConcealedTriples));
                add(createE001StatusDto(MahjongConst.ALL_TERMINALS_AND_HONORS,
                                        entity.allTerminalsAndHonors));
                add(createE001StatusDto(MahjongConst.LITTLE_DRAGONS, entity.littleDragons));
                add(createE001StatusDto(MahjongConst.TWO_DOUBLE_RUNS, entity.twoDoubleRuns));
                add(createE001StatusDto(MahjongConst.PURE_OUTSIDE_HAND, entity.pureOutsideHand));
                add(createE001StatusDto(MahjongConst.HALF_FLASH, entity.halfFlash));
                add(createE001StatusDto(MahjongConst.FULL_FLASH, entity.fullFlash));
                add(createE001StatusDto(MahjongConst.THIRTEEN_ORPHANS, entity.thirteenOrphans));
                add(createE001StatusDto(MahjongConst.NINE_TRESURES, entity.nineTresures));
                add(createE001StatusDto(MahjongConst.FOUR_CONCEALED_TRIPLES,
                                        entity.fourConcealedTriples));
                add(createE001StatusDto(MahjongConst.ALL_HONORS, entity.allHonors));
                add(createE001StatusDto(MahjongConst.BIG_FOUR_WINDS, entity.bigFourWinds));
                add(createE001StatusDto(MahjongConst.SMALL_FOUR_WINDS, entity.smallFourWinds));
                add(createE001StatusDto(MahjongConst.ALL_GREEN, entity.allGreen));
                add(createE001StatusDto(MahjongConst.BIG_DRAGONS, entity.bigDragons));
                add(createE001StatusDto(MahjongConst.TERMINALS, entity.terminals));
            }
        };

        return dataList;
    }

    /**
     * {@link E001StatusDto}を返却します。
     * 
     * @param hand 役
     * @param status ステータス
     * @return {@link E001StatusDto}
     */
    private E001StatusDto createE001StatusDto(String hand, int status) {

        String contents = null;
        HandCompleteType handCompleteType = null;
        String desc = null;

        if (status == 0) {
            contents = CommonConst.TEXT_HIDDNE;
            handCompleteType = HandCompleteType.NOT_COMPLETE;
            desc = HandCompleteType.NOT_COMPLETE.toString();
        } else {
            contents = hand;
            handCompleteType = HandCompleteType.COMPLETE;
            desc = HandCompleteType.COMPLETE.toString();
        }
        return new E001StatusDto(null, contents, handCompleteType, desc);
    }
}
