/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.co.headwaters.jacpot.mahjong.constant.MahjongConst;
import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;

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
        thirteenOrphans =
                        new Integer[]{MahjongConst.MAN1, MahjongConst.MAN9, MahjongConst.PIN1,
                                      MahjongConst.PIN9, MahjongConst.SOU1, MahjongConst.SOU9,
                                      MahjongConst.EAST, MahjongConst.SOUTH, MahjongConst.WEST,
                                      MahjongConst.NORTH, MahjongConst.WHITE, MahjongConst.GREEN,
                                      MahjongConst.RED};

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

        Integer[] hands = getHands(resourceIds);

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
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeCompleteHands(List<Integer> resourceIds, int resourceId, HandsStatusDto dto) {

        // あがり牌の設定
        dto.winningTile = ResourceUtil.resourceIdToIdx.get(resourceId);

        // ドラ設定
        resourceIds.add(resourceId);
        Collections.sort(resourceIds);
        ResourceUtil.setDragonCnt(resourceIds, dto);

        // 手牌情報の初期化
        dto.clear();

        // 手牌情報の抽出
        dto.hands = getHands(resourceIds);
        isCompleteHands(dto);

        // 九蓮宝燈の判定
        analyzeNineTresures(dto);

        // 大四喜の判定
        analyzeBigFourWinds(dto);

        // 小四喜の判定
        analyzeSmallFourWinds(dto);

        if (dto.grandSlamCounter == 0) {
            // 断ヤオの判定
            analyzeAllSimples(dto);

            // 役牌の判定
            analyzeValueTiles(dto);

            // 平和の判定
            analyzeAllRuns(dto);

            // 一盃口の判定
            analyzeDoubleRun(dto);

            // 二盃口の判定
            analyzeTwoDoubleRuns(dto);

            // 符の計算
            calculateFu(dto);
        }
    }
    


    /**
     * 
     * 牌インデックス配列を返却します。
     * 
     * @param resourceIds リソースIDリスト
     * @return 牌インデックス配列
     */
    private static Integer[] getHands(List<Integer> resourceIds) {

        Integer[] hands = new Integer[COMPLETE_HANDS_CNTS];

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
    private static boolean isCompleteHands(Integer[] hands) {

        HandsStatusDto dto = new HandsStatusDto();
        dto.hands = hands;
        return isCompleteHands(dto);
    }

    /**
     * 上がり形かを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     * @return 判定結果
     */
    private static boolean isCompleteHands(HandsStatusDto dto) {

        // 各牌の利用数を算出
        for (int i : dto.hands) {
            dto.useCnts[i]++;
        }

        // 全ての牌を判定する。
        // 雀頭、面子に利用した牌はマイナスし、cloneの要素が全て0になっていれば上がり形
        for (int i = 0; i < TILE_TYPES; i++) {

            // 雀頭の判定
            if (dto.useCnts[i] < 2) {
                continue;
            }

            // j = 0の場合、刻子 -> 順子の順に判定
            // j = 1の場合、順子 -> 刻子の順に判定
            // j = 2の場合、七対子かを判定
            // j = 3の場合、国士無双かを判定
            for (int j = 0; j < 4; j++) {

                int[] clone = dto.useCnts.clone();
                dto.chows.clear();
                dto.pungs.clear();
                dto.eyes = -1;

                // 雀頭分マイナス
                clone[i] -= 2;
                dto.eyes = i;

                switch (j) {
                    case 0:
                        // 刻子 -> 順子のパターン
                        analyzePungParts(clone, dto.pungs);
                        analyzeChowParts(clone, dto.chows);
                        break;
                    case 1:
                        // 順子 -> 刻子のパターン
                        analyzeChowParts(clone, dto.chows);
                        analyzePungParts(clone, dto.pungs);
                        break;
                    case 2:
                        // 七対子のパターン
                        analyzeSevenPairs(dto);
                        break;
                    case 3:
                        // 国士無双のパターン
                        analyzeThirteenOrphans(dto);
                        break;
                    default:
                        break;
                }

                if (Arrays.equals(clone, USE_CNTS) || dto.isSevenPairs || dto.isThirtheenOrphans) {

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
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeSevenPairs(HandsStatusDto dto) {

        int[] clone = dto.useCnts.clone();

        // 既に面子が確定している場合は処理を抜ける。
        if (Arrays.equals(clone, USE_CNTS)) {
            return;
        }

        for (int i = dto.eyes + 1; i < TILE_TYPES; i++) {
            if (clone[i] >= 2) {
                clone[i] -= 2;
            }
        }

        if (Arrays.equals(clone, USE_CNTS)) {
            dto.isSevenPairs = true;
        }
    }

    /**
     * 国士無双かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeThirteenOrphans(HandsStatusDto dto) {

        int[] clone = dto.useCnts.clone();

        if (!Arrays.asList(thirteenOrphans).contains(dto.eyes)) {
            // 雀頭が中張牌(2~8)の場合は処理を抜ける
            return;
        }

        for (int i = 0; i < thirteenOrphans.length; i++) {

            // 雀頭は除外
            if (thirteenOrphans[i] == dto.eyes) {
                continue;
            }

            if (clone[thirteenOrphans[i]] >= 1) {
                clone[thirteenOrphans[i]]--;
            }
        }

        if (Arrays.equals(clone, USE_CNTS)) {
            dto.isThirtheenOrphans = true;
            dto.grandSlamCounter++;
        }
    }

    /**
     * 
     * 九蓮宝燈かを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    @SuppressWarnings("serial")
    private static void analyzeNineTresures(HandsStatusDto dto) {

        List<int[]> params = new ArrayList<int[]>() {

            {
                add(new int[]{MahjongConst.MAN1, MahjongConst.MAN2, MahjongConst.MAN8,
                              MahjongConst.MAN9});
                add(new int[]{MahjongConst.PIN1, MahjongConst.PIN2, MahjongConst.PIN8,
                              MahjongConst.PIN9});
                add(new int[]{MahjongConst.SOU1, MahjongConst.SOU2, MahjongConst.SOU8,
                              MahjongConst.SOU9});
            }
        };

        for (int[] param : params) {

            // 1,9が3枚以上あるかを判定
            if (dto.useCnts[param[0]] < 3 || dto.useCnts[param[3]] < 3) {
                continue;
            }

            // 2~8が最低1枚ずつあるかを判定
            for (int i = param[1]; i <= param[2]; i++) {
                if (!Arrays.asList(dto.hands).contains(i)) {
                    continue;
                }
            }

            dto.isNineTreasures = true;
            dto.grandSlamCounter++;
        }

    }

    /**
     * 大四喜かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeBigFourWinds(HandsStatusDto dto) {

        for (int i = MahjongConst.EAST; i <= MahjongConst.NORTH; i++) {
            if (dto.useCnts[i] < 3) {
                return;
            }
        }
        dto.isBigFourWinds = true;
        dto.grandSlamCounter++;
    }

    /**
     * 小四喜かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeSmallFourWinds(HandsStatusDto dto) {

        Integer[] winds =
                        new Integer[]{MahjongConst.EAST, MahjongConst.SOUTH, MahjongConst.WEST,
                                      MahjongConst.NORTH};

        if (!Arrays.asList(winds).contains(dto.eyes)) {
            return;
        }

        for (Integer pung : dto.pungs) {
            if (!Arrays.asList(winds).contains(pung)) {
                return;
            }
        }

        dto.isSmallFourWinds = true;
        dto.grandSlamCounter++;
    }

    /**
     * 
     * 断ヤオかを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeAllSimples(HandsStatusDto dto) {

        for (int hand : dto.hands) {
            if (Arrays.asList(thirteenOrphans).contains(hand)) {
                return;
            }
        }
        dto.isAllSimples = true;
    }

    /**
     * 
     * 翻牌数を解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeValueTiles(HandsStatusDto dto) {

        for (Integer pung : dto.pungs) {
            if (isValueTile(pung)) {
                dto.valueTilesCnt++;
            }
            if (isWindTile(pung, dto)) {
                dto.valueTilesCnt++;
            }
            if (isSelfWind(pung, dto)) {
                dto.valueTilesCnt++;
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
        Integer[] dragons = new Integer[]{MahjongConst.WHITE, MahjongConst.GREEN, MahjongConst.RED};
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
     * @param dto {@link HandsStatusDto}
     * @return 判定結果
     */
    private static boolean isWindTile(int idx, HandsStatusDto dto) {

        // 場風(初期値:東)
        int wind = MahjongConst.EAST;
        if (dto.round == 1) {
            wind = MahjongConst.SOUTH;
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
     * @param dto {@link HandsStatusDto}
     * @return 判定結果
     */
    private static boolean isSelfWind(int idx, HandsStatusDto dto) {

        // 自風(初期値:東)
        int selfWind = MahjongConst.EAST;
        switch (dto.wind) {
            case 1:
                selfWind = MahjongConst.SOUTH;
                break;
            case 2:
                selfWind = MahjongConst.WEST;
                break;
            case 3:
                selfWind = MahjongConst.NORTH;
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
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeAllRuns(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 全てが順子かを判定
        if (!dto.pungs.isEmpty()) {
            return;
        }

        // 雀頭が翻牌かを判定
        int eyes = dto.eyes;
        if (isValueTile(eyes) || isWindTile(eyes, dto) || isSelfWind(eyes, dto)) {
            return;
        }

        // 両面待ちかを判定
        int win = dto.winningTile;
        for (Integer[] chow : dto.chows) {
            if (win == chow[0] || win == chow[2]) {
                dto.isAllRuns = true;
            }
        }

    }

    /**
     * 一盃口かを解析します。
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeDoubleRun(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 同一の順子があるかを判定
        List<Integer[]> chows = dto.chows;
        for (int i = 0; i < chows.size(); i++) {

            Integer[] source = chows.get(i);

            for (int j = i + 1; j < chows.size(); j++) {

                if (Arrays.equals(source, chows.get(j))) {
                    dto.isDoubleRun = true;
                    return;
                }
            }
        }
    }

    /**
     * 二盃口かを解析します。
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeTwoDoubleRuns(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 順子の有無を判定
        if (dto.chows.isEmpty()) {
            return;
        }

        // 七対子の形かを判定(槓子も考慮し、2回呼出す。)
        analyzeSevenPairs(dto);
        analyzeSevenPairs(dto);

        if (dto.isSevenPairs) {
            dto.isTwoDoubleRuns = true;
            dto.isSevenPairs = false;
            dto.isDoubleRun = false;
        }
    }

    /**
     * 
     * 符を計算します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void calculateFu(HandsStatusDto dto) {

        if (dto.isSevenPairs) {
            dto.fu = 25;
            return;
        }

        if (dto.isAllRuns) {
            if (dto.isRon) {
                dto.fu = 30;
            } else {
                dto.fu = 20;
            }
            return;
        }
    }

}
