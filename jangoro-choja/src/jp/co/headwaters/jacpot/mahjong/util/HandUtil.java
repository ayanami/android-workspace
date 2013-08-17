/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.headwaters.jacpot.mahjong.constant.MahjongConst;
import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;

/**
 * <p>
 * 役ユーティリティクラスです。
 * </p>
 * 
 * 作成日：2013/08/17<br>
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
 * <td>2013/08/17</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class HandUtil {

    /** 国士無双配列 */
    private static Integer[] thirteenOrphans;

    /** 三元牌配列 */
    private static Integer[] dragons;

    /** 風牌配列 */
    private static Integer[] winds;

    /** 字牌配列 */
    private static Integer[] honors;

    /** 緑一色配列 */
    private static Integer[] allGreen;

    /** 清老頭配列 */
    private static Integer[] terminals;

    /** 萬子配列 */
    private static Integer[] characters;

    /** 筒子配列 */
    private static Integer[] circles;

    /** 索子配列 */
    private static Integer[] bamboos;

    /** 数牌リスト */
    private static List<Integer[]> suits = new ArrayList<Integer[]>();

    /**
     * クラス変数を初期化します。
     */
    static {

        thirteenOrphans =
                        new Integer[]{MahjongConst.MAN1, MahjongConst.MAN9, MahjongConst.PIN1,
                                      MahjongConst.PIN9, MahjongConst.SOU1, MahjongConst.SOU9,
                                      MahjongConst.EAST, MahjongConst.SOUTH, MahjongConst.WEST,
                                      MahjongConst.NORTH, MahjongConst.WHITE, MahjongConst.GREEN,
                                      MahjongConst.RED};

        dragons = new Integer[]{MahjongConst.WHITE, MahjongConst.GREEN, MahjongConst.RED};

        winds =
                        new Integer[]{MahjongConst.EAST, MahjongConst.SOUTH, MahjongConst.WEST,
                                      MahjongConst.NORTH};

        honors =
                        new Integer[]{MahjongConst.WHITE, MahjongConst.GREEN, MahjongConst.RED,
                                      MahjongConst.EAST, MahjongConst.SOUTH, MahjongConst.WEST,
                                      MahjongConst.NORTH};

        terminals =
                        new Integer[]{MahjongConst.MAN1, MahjongConst.MAN9, MahjongConst.PIN1,
                                      MahjongConst.PIN9, MahjongConst.SOU1, MahjongConst.SOU9};

        allGreen =
                        new Integer[]{MahjongConst.SOU2, MahjongConst.SOU3, MahjongConst.SOU4,
                                      MahjongConst.SOU6, MahjongConst.SOU8, MahjongConst.GREEN};

        characters =
                        new Integer[]{MahjongConst.MAN1, MahjongConst.MAN2, MahjongConst.MAN3,
                                      MahjongConst.MAN4, MahjongConst.MAN5, MahjongConst.MAN6,
                                      MahjongConst.MAN7, MahjongConst.MAN8, MahjongConst.MAN9};

        circles =
                        new Integer[]{MahjongConst.PIN1, MahjongConst.PIN2, MahjongConst.PIN3,
                                      MahjongConst.PIN4, MahjongConst.PIN5, MahjongConst.PIN6,
                                      MahjongConst.PIN7, MahjongConst.PIN8, MahjongConst.PIN9};

        bamboos =
                        new Integer[]{MahjongConst.SOU1, MahjongConst.SOU2, MahjongConst.SOU3,
                                      MahjongConst.SOU4, MahjongConst.SOU5, MahjongConst.SOU6,
                                      MahjongConst.SOU7, MahjongConst.SOU8, MahjongConst.SOU9};

        suits.add(characters);
        suits.add(circles);
        suits.add(bamboos);
    }

    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected HandUtil() {

    }

    /**
     * 国士無双かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeThirteenOrphans(HandsStatusDto dto) {

        int[] zero = new int[MahjongConst.TILE_TYPES];
        Arrays.fill(zero, 0);

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

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

        if (Arrays.equals(clone, zero)) {
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
    public static void analyzeNineTresures(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 数牌を判定
        for (Integer[] suit : suits) {

            // 1,9が3枚以上あるかを判定
            if (dto.useCnts[suit[0]] < 3 || dto.useCnts[suit[8]] < 3) {
                continue;
            }

            // 2~8が最低1枚ずつあるかを判定
            boolean contains = true;
            for (int i = suit[1]; i <= suit[7]; i++) {
                if (!Arrays.asList(dto.hands).contains(i)) {
                    contains = false;
                    break;
                }
            }

            if (contains) {
                dto.isNineTreasures = true;
                dto.grandSlamCounter++;
                return;
            }
        }

    }

    /**
     * 
     * 四暗刻かを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeFourConcealedTriples(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 全て刻子かを判定
        if (!dto.chows.isEmpty()) {
            return;
        }

        // 門前自摸の場合
        if (!dto.isRon) {
            dto.isFourConcealedTriples = true;
            dto.grandSlamCounter++;
            return;
        }

        // 単騎待ちの場合
        if (dto.winningTile == dto.eyes) {
            dto.isFourConcealedTriples = true;
            dto.grandSlamCounter++;
        }
    }

    /**
     * 
     * 字一色かを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllHonors(HandsStatusDto dto) {

        // 全て刻子かを判定
        if (!dto.chows.isEmpty()) {
            return;
        }

        // 全て字牌かを判定
        for (Integer pung : dto.pungs) {
            if (!Arrays.asList(honors).contains(pung)) {
                return;
            }
        }

        dto.isAllHonors = true;
        dto.grandSlamCounter++;
    }

    /**
     * 大四喜かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeBigFourWinds(HandsStatusDto dto) {
        
        // 全て刻子かを判定
        if (!dto.chows.isEmpty()) {
            return;
        }

        // 全ての刻子が風牌で構成されているかを判定
        for (Integer pung : dto.pungs) {
            if (!Arrays.asList(winds).contains(pung)) {
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
    public static void analyzeSmallFourWinds(HandsStatusDto dto) {

        // 雀頭が風牌かを判定
        if (!Arrays.asList(winds).contains(dto.eyes)) {
            return;
        }

        // 風牌の刻子数をカウント
        int cnt = 0;
        for (Integer pung : dto.pungs) {
            if (Arrays.asList(winds).contains(pung)) {
                cnt++;
            }
        }

        if (cnt == 3) {
            dto.isSmallFourWinds = true;
            dto.grandSlamCounter++;
        }
    }

    /**
     * 
     * 緑一色かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllGreen(HandsStatusDto dto) {

        // 2索,3索,4索,6索,8索,発のみで構成されているかを判定
        for (int hand : dto.hands) {
            if (!Arrays.asList(allGreen).contains(hand)) {
                return;
            }
        }

        dto.isAllGreen = true;
        dto.grandSlamCounter++;
    }

    /**
     * 
     * 大三元かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeBigDragons(HandsStatusDto dto) {

        // 三元牌をカウント
        int cnt = 0;
        for (Integer pung : dto.pungs) {
            if (Arrays.asList(dragons).contains(pung)) {
                cnt++;
            }
        }

        if (cnt == 3) {
            dto.isBigDragons = true;
            dto.grandSlamCounter++;
        }
    }

    /**
     * 
     * 清老頭かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeTerminals(HandsStatusDto dto) {

        // 1萬,9萬,1筒,9筒,1索,9索のみで構成されているかを判定
        for (int hand : dto.hands) {
            if (!Arrays.asList(terminals).contains(hand)) {
                return;
            }
        }

        dto.isTerminals = true;
        dto.grandSlamCounter++;
    }

    /**
     * 七対子かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeSevenPairs(HandsStatusDto dto) {

        int[] zero = new int[MahjongConst.TILE_TYPES];
        Arrays.fill(zero, 0);

        if (!dto.conceal) {
            return;
        }

        int[] clone = dto.useCnts.clone();

        for (int i = dto.eyes; i < MahjongConst.TILE_TYPES; i++) {
            if (clone[i] >= 2) {
                clone[i] -= 2;
            }
        }

        if (Arrays.equals(clone, zero)) {
            dto.isSevenPairs = true;
        }
    }

    /**
     * 
     * 断ヤオかを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllSimples(HandsStatusDto dto) {

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
    public static void analyzeValueTiles(HandsStatusDto dto) {

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
    public static boolean isValueTile(int idx) {

        // 白、発、中
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
    public static boolean isWindTile(int idx, HandsStatusDto dto) {

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
    public static boolean isSelfWind(int idx, HandsStatusDto dto) {

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
     * 混老頭かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllTerminalsAndHonors(HandsStatusDto dto) {

        // 全て刻子かを判定
        if (!dto.chows.isEmpty()) {
            return;
        }

        // 字牌が含まれているかを判定
        for (int hand : dto.hands) {
            if (!Arrays.asList(honors).contains(hand)) {
                return;
            }
        }

        // ヤオ九牌で構成されているかを判定
        for (int hand : dto.hands) {
            if (!Arrays.asList(thirteenOrphans).contains(hand)) {
                return;
            }
        }

        dto.isAllTerminalsAndHonors = true;
    }

    /**
     * 小三元かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeLittleDragons(HandsStatusDto dto) {

        // 雀頭が三元牌かを判定
        if (!Arrays.asList(dragons).contains(dto.eyes)) {
            return;
        }

        // 三元牌の刻子数をカウント
        int cnt = 0;
        for (Integer pung : dto.pungs) {
            if (Arrays.asList(dragons).contains(pung)) {
                cnt++;
            }
        }

        if (cnt == 3) {
            dto.isLittleDragons = true;
        }
    }

    /**
     * 
     * 混一色かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeHalfFlash(HandsStatusDto dto) {

        // 字牌が含まれているかを判定
        for (int hand : dto.hands) {
            if (!Arrays.asList(honors).contains(hand)) {
                return;
            }
        }

        // 数牌を判定
        for (Integer[] suit : suits) {

            boolean contains = true;
            for (int hand : dto.hands) {
                if (!Arrays.asList(suit).contains(hand)) {
                    if (!Arrays.asList(honors).contains(hand)) {
                        contains = false;
                        break;
                    }
                }
            }

            if (contains) {
                dto.isHalfFlash = true;
                return;
            }
        }
    }

    /**
     * 
     * 清一色かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeFullFlash(HandsStatusDto dto) {

        // 数牌を判定
        for (Integer[] suit : suits) {

            boolean contains = true;
            for (int hand : dto.hands) {
                if (!Arrays.asList(suit).contains(hand)) {
                    contains = false;
                    break;
                }
            }

            if (contains) {
                dto.isFullFlash = true;
                return;
            }
        }
    }

    /**
     * 
     * 平和かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllRuns(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 全てが順子かを判定
        if (!dto.pungs.isEmpty()) {
            return;
        }

        // 雀頭が翻牌かを判定
        if (isValueTile(dto.eyes) || isWindTile(dto.eyes, dto) || isSelfWind(dto.eyes, dto)) {
            return;
        }

        // 両面待ちかを判定
        if (isBothSides(dto)) {
            dto.isAllRuns = true;
        }
    }

    /**
     * 一盃口かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeDoubleRun(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 同一の順子があるかを判定
        for (int i = 0; i < dto.chows.size(); i++) {

            Integer[] source = dto.chows.get(i);

            for (int j = i + 1; j < dto.chows.size(); j++) {

                if (Arrays.equals(source, dto.chows.get(j))) {
                    dto.isDoubleRun = true;
                    return;
                }
            }
        }
    }

    /**
     * 
     * 対々和かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeAllTriples(HandsStatusDto dto) {

        // 四暗刻の場合、処理を抜ける。
        if (dto.isFourConcealedTriples) {
            return;
        }

        if (dto.pungs.size() == 4) {
            dto.isAllTriples = true;
        }
    }

    /**
     * 二盃口かを解析します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void analyzeTwoDoubleRuns(HandsStatusDto dto) {

        // 面前かを判定
        if (!dto.conceal) {
            return;
        }

        // 順子の有無を判定
        if (dto.chows.isEmpty()) {
            return;
        }

        int[] zero = new int[MahjongConst.TILE_TYPES];
        Arrays.fill(zero, 0);

        int[] clone = dto.useCnts.clone();

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < MahjongConst.TILE_TYPES; j++) {
                if (clone[j] >= 2) {
                    clone[j] -= 2;
                }
            }
        }

        if (Arrays.equals(clone, zero)) {
            dto.isTwoDoubleRuns = true;
            dto.isDoubleRun = false;
        }
    }

    /**
     * 
     * 両面待ちかを判定します。
     * 
     * @param dto {@link HandsStatusDto}
     * @return 判定結果
     */
    private static boolean isBothSides(HandsStatusDto dto) {

        for (Integer[] chow : dto.chows) {
            if (dto.winningTile == chow[0] || dto.winningTile == chow[2]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * 役リストを生成します。
     * 
     * @param dto {@link HandsStatusDto}
     * @return 役リスト
     */
    public static List<String> createHands(HandsStatusDto dto) {

        List<String> hands = new ArrayList<String>();

        if (dto.isThirtheenOrphans) {
            hands.add(MahjongConst.THIRTEEN_ORPHANS);
            return hands;
        }
        if (dto.isNineTreasures) {
            hands.add(MahjongConst.NINE_TRESURES);
            return hands;
        }
        if (dto.isFourConcealedTriples) {
            hands.add(MahjongConst.FOUR_CONCEALED_TRIPLES);
        }
        if (dto.isAllHonors) {
            hands.add(MahjongConst.ALL_HONORS);
        }
        if (dto.isBigFourWinds) {
            hands.add(MahjongConst.BIG_FOUR_WINDS);
            return hands;
        }
        if (dto.isSmallFourWinds) {
            hands.add(MahjongConst.SMALL_FOUR_WINDS);
            return hands;
        }
        if (dto.isAllGreen) {
            hands.add(MahjongConst.ALL_GREEN);
            return hands;
        }
        if (dto.isBigDragons) {
            hands.add(MahjongConst.BIG_DRAGONS);
            return hands;
        }
        if (dto.isTerminals) {
            hands.add(MahjongConst.TERMINALS);
            return hands;
        }
        if (!dto.isRon) {
            hands.add(MahjongConst.SELF_DRAW + MahjongConst.FAN1);
            dto.fan += 1;
        }
        if (dto.isSevenPairs) {
            hands.add(MahjongConst.SEVEN_PAIRS + MahjongConst.FAN2);
            dto.fan += 2;
        }
        if (dto.isAllSimples) {
            hands.add(MahjongConst.ALL_SIMPLES + MahjongConst.FAN1);
            dto.fan += 1;
        }
        if (dto.isAllTerminalsAndHonors) {
            hands.add(MahjongConst.ALL_TERMINALS_AND_HONORS + MahjongConst.FAN2);
            dto.fan += 2;
        }
        if (dto.isLittleDragons) {
            hands.add(MahjongConst.LITTLE_DRAGONS + MahjongConst.FAN2);
            dto.fan += 2;
        }
        if (dto.isHalfFlash) {
            if (dto.conceal) {
                hands.add(MahjongConst.HALF_FLASH + MahjongConst.FAN3);
                dto.fan += 3;
            } else {
                hands.add(MahjongConst.HALF_FLASH + MahjongConst.FAN2);
                dto.fan += 2;
            }

        }
        if (dto.isFullFlash) {
            if (dto.conceal) {
                hands.add(MahjongConst.FULL_FLASH + MahjongConst.FAN6);
                dto.fan += 6;
            } else {
                hands.add(MahjongConst.FULL_FLASH + MahjongConst.FAN5);
                dto.fan += 5;
            }

        }
        if (dto.isLittleDragons) {
            hands.add(MahjongConst.LITTLE_DRAGONS + MahjongConst.FAN2);
            dto.fan += 2;
        }
        if (dto.isAllRuns) {
            hands.add(MahjongConst.ALL_RUNS + MahjongConst.FAN1);
            dto.fan += 1;
        }
        if (dto.isDoubleRun) {
            hands.add(MahjongConst.DOUBLE_RUN + MahjongConst.FAN1);
            dto.fan += 1;
        }
        if (dto.isAllTriples) {
            hands.add(MahjongConst.ALL_TRIPLES + MahjongConst.FAN2);
            dto.fan += 2;
        }
        if (dto.isTwoDoubleRuns) {
            hands.add(MahjongConst.TWO_DOUBLE_RUNS + MahjongConst.FAN3);
            dto.fan += 3;
        }
        if (dto.valueTilesCnt > 0) {
            hands.add(MahjongConst.VALUE_TILES + " " + dto.valueTilesCnt);
            dto.fan = dto.valueTilesCnt;
        }
        if (dto.dragonCnt > 0) {
            hands.add(MahjongConst.DRAGON + " " + dto.dragonCnt);
            dto.fan += dto.dragonCnt;
        }

        return hands;
    }

}
