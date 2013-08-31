/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity.service;

import static jp.co.headwaters.jacpot.mahjong.entity.names.E001StatusEntityNames.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import jp.co.headwaters.jacpot.mahjong.common.AbstractDataAccessService;
import jp.co.headwaters.jacpot.mahjong.entity.E001StatusEntity;

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
public class E001StatusService extends AbstractDataAccessService<E001StatusEntity> {

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param entity {@link E001StatusEntity}
     */
    public E001StatusService(Context context, E001StatusEntity entity) {
        super(context, entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentValues getContentValues(E001StatusEntity entity) {
        ContentValues values = new ContentValues();
        values.put(THIRTEEN_ORPHANS, entity.thirteenOrphans);
        values.put(NINE_TRESURES, entity.nineTresures);
        values.put(FOUR_CONCEALED_TRIPLES, entity.fourConcealedTriples);
        values.put(ALL_HONORS, entity.allHonors);
        values.put(BIG_FOUR_WINDS, entity.bigFourWinds);
        values.put(SMALL_FOUR_WINDS, entity.smallFourWinds);
        values.put(ALL_GREEN, entity.allGreen);
        values.put(BIG_DRAGONS, entity.bigDragons);
        values.put(TERMINALS, entity.terminals);
        values.put(SEVEN_PAIRS, entity.sevenPairs);
        values.put(ALL_SIMPLES, entity.allSimples);
        values.put(ALL_TERMINALS_AND_HONORS, entity.allTerminalsAndHonors);
        values.put(LITTLE_DRAGONS, entity.littleDragons);
        values.put(HALF_FLASH, entity.halfFlash);
        values.put(FULL_FLASH, entity.fullFlash);
        values.put(ALL_RUNS, entity.allRuns);
        values.put(DOUBLE_RUN, entity.doubleRun);
        values.put(MIXED_OUTSIDE_HAND, entity.mixedOutsideHand);
        values.put(THREE_COLOR_RUNS, entity.threeColorRuns);
        values.put(FULL_STRAIGHT, entity.fullStraight);
        values.put(ALL_TRIPLES, entity.allTriples);
        values.put(THREE_COLOR_TRIPLES, entity.threeColorTriples);
        values.put(TWO_DOUBLE_RUNS, entity.twoDoubleRuns);
        values.put(PURE_OUTSIDE_HAND, entity.pureOutsideHand);
        values.put(THREE_CONCEALED_TRIPLES, entity.threeConcealedTriples);
        values.put(BEST_SCORE, entity.bestScore);
        values.put(TITLE, entity.title);
        return values;
    }

    /**
     * 
     * {@link E001StatusEntity}を返却します。
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
            entity.thirteenOrphans = c.getInt(i++);
            entity.nineTresures = c.getInt(i++);
            entity.fourConcealedTriples = c.getInt(i++);
            entity.allHonors = c.getInt(i++);
            entity.bigFourWinds = c.getInt(i++);
            entity.smallFourWinds = c.getInt(i++);
            entity.allGreen = c.getInt(i++);
            entity.bigDragons = c.getInt(i++);
            entity.terminals = c.getInt(i++);
            entity.sevenPairs = c.getInt(i++);
            entity.allSimples = c.getInt(i++);
            entity.allTerminalsAndHonors = c.getInt(i++);
            entity.littleDragons = c.getInt(i++);
            entity.halfFlash = c.getInt(i++);
            entity.fullFlash = c.getInt(i++);
            entity.allRuns = c.getInt(i++);
            entity.doubleRun = c.getInt(i++);
            entity.mixedOutsideHand = c.getInt(i++);
            entity.threeColorRuns = c.getInt(i++);
            entity.fullStraight = c.getInt(i++);
            entity.allTriples = c.getInt(i++);
            entity.threeColorTriples = c.getInt(i++);
            entity.twoDoubleRuns = c.getInt(i++);
            entity.pureOutsideHand = c.getInt(i++);
            entity.threeConcealedTriples = c.getInt(i++);
            entity.bestScore = c.getInt(i++);
            entity.title = c.getString(i++);
            entity.lastUpdateDatetime = c.getString(i++);
        }
        c.close();

        return entity;
    }
}
