/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    /** 聴牌形手牌枚数 */
    private static final int READY_HANDS_CNTS = 13;

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
     * @param resourceIds 手牌リソースIDリスト
     * @return 判定結果
     */
    public static boolean isReadyHands(List<Integer> resourceIds) {

        if (resourceIds.size() < READY_HANDS_CNTS) {
            return false;
        }

        int[] hands = getHands(resourceIds);

        // あがり牌リソースIDリストを初期化
        ResourceUtil.winningResourceIds.clear();

        // 1萬から順に判定
        for (int i = 0; i < TILE_TYPES; i++) {

            hands[COMPLETE_HANDS_MAX_IDX] = i;

            // あがり成立の場合は、あがり牌リソースIDリストに格納
            if (isCompleteHands(hands)) {
                ResourceUtil.winningResourceIds.addAll(ResourceUtil.getAvailableResourceIds(i));
            }
        }

        if (ResourceUtil.winningResourceIds.isEmpty()) {
            return false;
        }

        // あがり牌リソースIDリストをシャッフル
        Collections.shuffle(ResourceUtil.winningResourceIds);

        return true;
    }

    /**
     * 
     * あがり手牌を解析します。
     * 
     * @param resourceIds 聴牌リソースIDリスト
     * @param resourceId あがり牌リソースID
     */
    public static void analyzeCompleteHands(List<Integer> resourceIds, int resourceId) {

        // あがり牌の設定
        ResourceUtil.completeHandsStatusDto.winningTile =
                        ResourceUtil.resourceIdToIdx.get(resourceId);

        // ドラ設定
        resourceIds.add(resourceId);
        Collections.sort(resourceIds);
        ResourceUtil.setDragonCnt(resourceIds);

        // 手牌情報の初期化
        ResourceUtil.completeHandsStatusDto.clear();

        // 手牌情報の抽出
        int[] hands = getHands(resourceIds);
        isCompleteHands(hands);
        
        // 役牌の判定
        analyzeValueTiles();
        
        // 平和の判定
        analyzeAllRuns();
    }

    /**
     * 
     * 牌インデックス配列を返却します。
     * 
     * @param resourceIds リソースIDリスト
     * @return 牌インデックス配列
     */
    private static int[] getHands(List<Integer> resourceIds) {

        int[] hands = new int[COMPLETE_HANDS_CNTS];

        for (int i = 0; i < resourceIds.size(); i++) {
            hands[i] = ResourceUtil.resourceIdToIdx.get(resourceIds.get(i));
        }

        return hands;
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
        
        int head = 0;
        List<Integer[]> chows = new ArrayList<Integer[]>();
        List<Integer> pungs = new ArrayList<Integer>();

        // 全ての牌を判定する。
        // 雀頭、面子に利用した牌はマイナスし、cloneの要素が全て0になっていれば上がり形
        for (int i = 0; i < TILE_TYPES; i++) {

            chows.clear();
            pungs.clear();

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
                head = i;

                switch (j) {
                    case 0:
                        // 刻子 -> 順子のパターン
                        analyzePungParts(clone, pungs);
                        analyzeChowParts(clone, chows);
                        break;
                    case 1:
                        // 順子 -> 刻子のパターン
                        analyzeChowParts(clone, chows);
                        analyzePungParts(clone, pungs);
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
                    switch (j) {
                        case 0:
                        case 1:
                            ResourceUtil.completeHandsStatusDto.head = head;
                            ResourceUtil.completeHandsStatusDto.chows = chows;
                            ResourceUtil.completeHandsStatusDto.pungs = pungs;
                            break;
                        case 2:
                            ResourceUtil.completeHandsStatusDto.isSevenPairs = true;
                            break;
                        case 3:
                            ResourceUtil.completeHandsStatusDto.isThirtheenOrphans = true;
                            break;
                        default:
                            break;
                    }
                    ResourceUtil.completeHandsStatusDto.hands = useCnts;
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
     * @param pungs 刻子リスト
     */
    private static void analyzePungParts(int[] useCnts, List<Integer> pungs) {

        for (int i = 0; i < TILE_TYPES; i++) {

            if (useCnts[i] >= 3) {
                useCnts[i] -= 3;
                pungs.add(i);
            }
        }
    }

    /**
     * 順子部を解析します。
     * 
     * @param useCnts 利用数配列
     * @param chows 順子リスト
     */
    private static void analyzeChowParts(int[] useCnts, List<Integer[]> chows) {

        // 萬子 -> 筒子 -> 索子の順に判定
        for (int i = 0; i < 3; i++) {

            // 123 -> 234 -> 345 -> 456 -> 567 -> 678 -> 789の順に判定
            int j = 0;
            while (j < 7) {

                // 利用数配列の添え字
                int idx = 9 * i + j;
                Integer[] selects = new Integer[]{useCnts[idx], useCnts[idx + 1], useCnts[idx + 2]};

                if (Arrays.asList(selects).contains(0)) {
                    // 利用回数が0の牌が含まれている場合
                    j++;
                } else {
                    // 全ての牌が1回以上利用可能な場合
                    useCnts[idx]--;
                    useCnts[idx + 1]--;
                    useCnts[idx + 2]--;
                    chows.add(new Integer[]{idx, idx + 1, idx + 2});
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

    /**
     * 
     * 役牌があるかを解析します。
     * 
     */
    private static void analyzeValueTiles() {
        
        for (Integer pung : ResourceUtil.completeHandsStatusDto.pungs) {
            if (isValueTile(pung)) {
                ResourceUtil.completeHandsStatusDto.valueTilesCnt++;
            }
            if (isWindTile(pung)) {
                ResourceUtil.completeHandsStatusDto.valueTilesCnt++;
            }
            if (isSelfWind(pung)) {
                ResourceUtil.completeHandsStatusDto.valueTilesCnt++;
            }
        }
    }

    /**
     * 
     * 役牌かを判定します。
     * 
     * @param idx 牌インデックス
     * @return 判定結果
     */
    private static boolean isValueTile(int idx) {

        // 白、発、中
        Integer[] dragons = new Integer[]{32, 33, 34};
        if (Arrays.asList(dragons).contains(idx)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * 場風牌かを判定します。
     * 
     * @param idx 牌インデックス
     * @return 判定結果
     */
    private static boolean isWindTile(int idx) {
        
        // 場風(初期値:東)
        int wind = 28;
        if (ResourceUtil.completeHandsStatusDto.round == 1) {
            wind = 29;
        }
        
        if (idx == wind) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 自風牌かを判定します。
     * 
     * @param idx 牌インデックス
     * @return 判定結果
     */
    private static boolean isSelfWind(int idx) {
        
        // 自風(初期値:東)
        int selfWind = 28;
        switch (ResourceUtil.completeHandsStatusDto.wind) {
            case 1:
                selfWind = 29;
                break;
            case 2:
                selfWind = 30;
                break;
            case 3:
                selfWind = 31;
                break;
            default:
                break;
        }
        
        if (idx == selfWind) {
            return true;
        }
        
        return false;
    }

    /**
     * 
     * 平和かを解析します。
     * 
     */
    private static void analyzeAllRuns() {
        
        // 全てが順子かを判定
        if (!ResourceUtil.completeHandsStatusDto.pungs.isEmpty()) {
            return;
        }
        
        // 雀頭が役牌かを判定
        int head = ResourceUtil.completeHandsStatusDto.head;
        if (isValueTile(head) || isWindTile(head) || isSelfWind(head))  {
            return;
        }
        
        // 両面待ちかを判定
        int win = ResourceUtil.completeHandsStatusDto.winningTile;
        for (Integer[] chow : ResourceUtil.completeHandsStatusDto.chows) {
            if (win == chow[0] || win == chow[2]) {
                ResourceUtil.completeHandsStatusDto.isAllRuns = true;
            }
        }
        
    }
}
