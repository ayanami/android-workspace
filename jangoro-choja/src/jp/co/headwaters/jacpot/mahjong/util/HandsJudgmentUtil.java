/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

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

    /** 聴牌形手牌枚数 */
    private static final int READY_HANDS_CNTS = 13;

    /** 上がり形手牌枚数 */
    private static final int COMPLETE_HANDS_CNTS = 14;

    /** 上がり形手牌最大添え字 */
    private static final int COMPLETE_HANDS_MAX_IDX = 13;

    /** 利用数配列 */
    private static final int[] USE_CNTS = new int[MahjongConst.TILE_TYPES];

    /**
     * 定数を初期化します。
     */
    static {
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
        boolean isReady = false;
        for (int i = 0; i < MahjongConst.TILE_TYPES; i++) {

            hands[COMPLETE_HANDS_MAX_IDX] = i;

            // あがり成立の場合は、あがり牌リソースIDリストに格納
            if (isCompleteHands(hands)) {
                isReady = true;
                ResourceUtil.winningResourceIds.addAll(ResourceUtil.getAvailableResourceIds(i));
            }
        }

        if (ResourceUtil.winningResourceIds.isEmpty()) {
            if (isReady) {
                ResourceUtil.isEmptyWinningTiles = true;
            }
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
    public static void analyzeCompleteHands(List<Integer> resourceIds, int resourceId,
                    HandsStatusDto dto) {

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

        // 待ちの判定
        analyzeWaitPattern(dto);

        // 九蓮宝燈の判定
        HandUtil.analyzeNineTresures(dto);

        // 四暗刻の判定
        HandUtil.analyzeFourConcealedTriples(dto);

        // 字一色の判定
        HandUtil.analyzeAllHonors(dto);

        // 大四喜の判定
        HandUtil.analyzeBigFourWinds(dto);

        // 小四喜の判定
        HandUtil.analyzeSmallFourWinds(dto);

        // 緑一色の判定
        HandUtil.analyzeAllGreen(dto);

        // 大三元の判定
        HandUtil.analyzeBigDragons(dto);

        // 清老頭の判定
        HandUtil.analyzeTerminals(dto);

        if (dto.grandSlamCounter == 0) {
            // 断ヤオの判定
            HandUtil.analyzeAllSimples(dto);

            // 役牌の判定
            HandUtil.analyzeValueTiles(dto);

            // 混老頭の判定
            HandUtil.analyzeAllTerminalsAndHonors(dto);

            // 小三元の判定
            HandUtil.analyzeLittleDragons(dto);

            // 混一色の判定
            HandUtil.analyzeHalfFlash(dto);

            // 清一色の判定
            HandUtil.analyzeFullFlash(dto);

            // 平和の判定
            HandUtil.analyzeAllRuns(dto);

            // 一盃口の判定
            HandUtil.analyzeDoubleRun(dto);

            // 全帯の判定
            HandUtil.analyzeMixedOutsideHand(dto);

            // 三色同順の判定
            HandUtil.analyzeThreeColorRuns(dto);

            // 一気通貫の判定
            HandUtil.analyzeFullStraight(dto);

            // 対々和の判定
            HandUtil.analyzeAllTriples(dto);

            // 三色同刻の判定
            HandUtil.analyzeThreeColorTriples(dto);

            // 二盃口の判定
            HandUtil.analyzeTwoDoubleRuns(dto);

            // 純全帯の判定
            HandUtil.analyzePureOutsideHand(dto);

            // 三暗刻の判定
            HandUtil.analyzeThreeConcealedTriples(dto);

            // 符の計算
            ScoreUtil.calculateFu(dto);
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
        for (int i = 0; i < MahjongConst.TILE_TYPES; i++) {

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

                        // 全帯+一盃口、または純全帯+一盃口の場合は再ループ
                        if (isOutsideHandWithDoubleRun(dto)) {
                            continue;
                        }

                        break;
                    case 1:
                        // 順子 -> 刻子のパターン
                        analyzeChowParts(clone, dto.chows);
                        analyzePungParts(clone, dto.pungs);
                        break;
                    case 2:
                        // 七対子のパターン
                        HandUtil.analyzeSevenPairs(dto);
                        break;
                    case 3:
                        // 国士無双のパターン
                        HandUtil.analyzeThirteenOrphans(dto);
                        break;
                    default:
                        break;
                }

                if (Arrays.equals(clone, USE_CNTS) || dto.isThirtheenOrphans) {

                    return true;
                }
            }
        }

        if (dto.isSevenPairs) {

            dto.eyes = -1;
            return true;
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

        for (int i = 0; i < MahjongConst.TILE_TYPES; i++) {

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
                Integer[] selects =
                    new Integer[] {useCnts[idx], useCnts[idx + 1], useCnts[idx + 2]};

                if (Arrays.asList(selects).contains(0)) {
                    // 利用回数が0の牌が含まれている場合
                    j++;
                } else {
                    // 全ての牌が1回以上利用可能な場合
                    useCnts[idx]--;
                    useCnts[idx + 1]--;
                    useCnts[idx + 2]--;
                    chows.add(new Integer[] {idx, idx + 1, idx + 2});
                }
            }
        }
    }

    /**
     * 
     * 全帯+一盃口、または純全帯+一盃口かを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     * @return 判定結果
     */
    private static boolean isOutsideHandWithDoubleRun(HandsStatusDto dto) {

        // 雀頭がヤオ九牌で構成されているかを判定
        if (!Arrays.asList(HandUtil.TERMINALS_AND_HONORS).contains(dto.eyes)) {
            return false;
        }

        // 順子が123、または789かを判定
        for (Integer[] chow : dto.chows) {
            if (!Arrays.asList(HandUtil.TERMINALS).contains(chow[0])
                && !Arrays.asList(HandUtil.TERMINALS).contains(chow[2])) {
                return false;
            }
        }

        // 刻子が3組以上あるかを判定
        if (dto.pungs.size() < 3) {
            return false;
        }

        // 刻子を取得
        int[] clone = USE_CNTS.clone();
        for (Integer pung : dto.pungs) {
            clone[pung]++;
        }

        // 同色の123,789があるかを判定
        for (int i = MahjongConst.MAN1; i <= MahjongConst.SOU9; i += 9) {
            if (clone[i] > 0 && clone[i + 1] > 0 && clone[i + 2] > 0) {
                for (int j = 0; j < 3; j++) {
                    clone[j]--;
                }
                if (clone[i + 8] > 0) {
                    clone[i + 8]--;
                }
            }
            if (clone[i + 6] > 0 && clone[i + 7] > 0 && clone[i + 8] > 0) {
                for (int j = 6; j < 9; j++) {
                    clone[j]--;
                }
                if (clone[i] > 0) {
                    clone[i]--;
                }
            }
        }

        if (Arrays.equals(clone, USE_CNTS)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 待ちの形を解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    private static void analyzeWaitPattern(HandsStatusDto dto) {

        // 単騎待ちを判定
        if (dto.winningTile == dto.eyes) {
            dto.isSingle = true;
        }

        // 両面、辺張、間張を判定
        for (Integer[] chow : dto.chows) {

            // 両面を判定
            if ((dto.winningTile == chow[0] && !Arrays.asList(HandUtil.TERMINALS).contains(chow[2]))
                || (dto.winningTile == chow[2] && !Arrays.asList(HandUtil.TERMINALS)
                                .contains(chow[0]))) {
                dto.isBothSides = true;
            }

            // 辺張を判定
            if ((dto.winningTile == chow[0] && Arrays.asList(HandUtil.TERMINALS).contains(chow[2]))
                || (dto.winningTile == chow[2] && Arrays.asList(HandUtil.TERMINALS)
                                .contains(chow[0]))) {
                dto.isSingleSide = true;
            }

            // 間張を判定
            if (dto.winningTile == chow[1]) {
                dto.isSpace = true;
            }
        }
    }

}
