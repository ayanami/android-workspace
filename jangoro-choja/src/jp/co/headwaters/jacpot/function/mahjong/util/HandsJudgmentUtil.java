/**
 * 
 */
package jp.co.headwaters.jacpot.function.mahjong.util;

import java.util.Arrays;
import java.util.List;

import android.util.SparseIntArray;

/**
 * <p>
 * 手牌判定ユーティリティクラスです。
 * </p>
 * 
 * 作成日：2013/07/22<br>
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
 * <td>2013/07/22</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class HandsJudgmentUtil {

    /** 麻雀牌種類 */
    private static final int TILE_TYPES = 34;

    /** 上がり形手牌枚数 */
    private static final int COMPLETE_HANDS_CNTS = 14;

    /** 上がり形手牌最大添え字 */
    private static final int COMPLETE_HANDS_MAX_IDX = 13;

    /** 利用数配列 */
    private static final int[] USE_CNTS = new int[TILE_TYPES];

    /** 国士無双利用牌配列 */
    private static Integer[] thirteenOrphans;
    /**
     * 定数を初期化します。
     */
    static {
        // 1萬, 9萬, 1筒, 9筒, 1索, 9索, 東, 南, 西, 北, 白, 発, 中の添え字
        thirteenOrphans = new Integer[]{0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33};

        // 全要素を0で初期化
        Arrays.fill(USE_CNTS, 0);
    }

    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected HandsJudgmentUtil() {

    }

    /**
     * 聴牌形かを判定します。
     * 
     * @param resourceIds リソースIDリスト
     * @return 判定結果
     */
    public static boolean isReadyHands(List<Integer> resourceIds) {

        int[] hands = new int[COMPLETE_HANDS_CNTS];
        SparseIntArray tiles = ResourceUtil.getTiles();

        for (int i = 0; i < resourceIds.size(); i++) {
            hands[i] = tiles.get(resourceIds.get(i));
        }

        for (int i = 0; i < TILE_TYPES; i++) {

            hands[COMPLETE_HANDS_MAX_IDX] = i;
            if (isCompleteHands(hands)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 上がり形かを判定します。
     * 
     * @param hands 手牌配列
     * @return 判定結果
     */
    private static boolean isCompleteHands(int[] hands) {

        // 各牌の利用数を算出
        int[] useCnts = USE_CNTS.clone();
        for (int i : hands) {
            useCnts[i]++;
        }

        // 全ての牌を判定する。
        // 雀頭、面子に利用した牌はマイナスし、cloneの要素が全て0になっていれば上がり形
        for (int i = 0; i < TILE_TYPES; i++) {

            // 雀頭の判定
            if (useCnts[i] < 2) {
                continue;
            }

            // j = 0の場合、刻子 -> 順子の順に判定
            // j = 1の場合、順子 -> 刻子の順に判定
            // j = 2の場合、七対子かを判定
            // j = 3の場合、国士無双かを判定
            for (int j = 0; j < 4; j++) {

                int[] clone = useCnts.clone();

                // 雀頭分マイナス
                clone[i] -= 2;

                switch (j) {
                    case 0:
                        // 刻子 -> 順子のパターン
                        analyzePungParts(clone);
                        analyzeChowParts(clone);
                        break;
                    case 1:
                        // 順子 -> 刻子のパターン
                        analyzeChowParts(clone);
                        analyzePungParts(clone);
                        break;
                    case 2:
                        // 七対子のパターン
                        analyzeSevenPairs(i + 1, clone);
                        break;
                    case 3:
                        // 国士無双のパターン
                        analyzeThirteenOrphans(i, clone);
                        break;
                    default:
                        break;
                }

                if (Arrays.equals(clone, USE_CNTS)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 刻子部を解析します。
     * 
     * @param useCnts 利用数配列
     */
    private static void analyzePungParts(int[] useCnts) {

        for (int i = 0; i < TILE_TYPES; i++) {

            if (useCnts[i] >= 3) {
                useCnts[i] -= 3;
            }
        }
    }

    /**
     * 順子部を解析します。
     * 
     * @param useCnts 利用数配列
     */
    private static void analyzeChowParts(int[] useCnts) {

        // 萬子 -> 筒子 -> 索子の順に判定
        for (int i = 0; i < 3; i++) {

            // 123 -> 234 -> 345 -> 456 -> 567 -> 678 -> 789の順に判定
            int j = 0;
            while (j < 7) {

                // 利用数配列の添え字
                int idx = 9 * i + j;
                Integer[] chows = new Integer[]{useCnts[idx], useCnts[idx + 1], useCnts[idx + 2]};

                if (Arrays.asList(chows).contains(0)) {
                    // 利用回数が0の牌が含まれている場合
                    j++;
                } else {
                    // 全ての牌が1回以上利用可能な場合
                    for (int k = 0; k < 3; k++) {
                        useCnts[idx++]--;
                    }
                }
            }
        }
    }

    /**
     * 七対子かを解析します。
     * 
     * @param start 解析を始める添え字
     * @param useCnts 利用数配列
     */
    private static void analyzeSevenPairs(int start, int[] useCnts) {

        for (int i = start; i < TILE_TYPES; i++) {
            if (useCnts[i] >= 2) {
                useCnts[i] -= 2;
            }
        }
    }

    /**
     * 国士無双かを解析します。
     * 
     * @param current 雀頭の添え字
     * @param useCnts 利用数配列
     */
    private static void analyzeThirteenOrphans(int current, int[] useCnts) {
        
        if (!Arrays.asList(thirteenOrphans).contains(current)) {
            // 雀頭が中張牌(2~8)の場合は処理を抜ける
            return;
        }

        for (int i = 0; i < thirteenOrphans.length; i++) {

            // 雀頭は除外
            if (thirteenOrphans[i] == current) {
                continue;
            }

            if (useCnts[thirteenOrphans[i]] >= 1) {
                useCnts[thirteenOrphans[i]]--;
            }
        }
    }
}
