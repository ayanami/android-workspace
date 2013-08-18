/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;

/**
 * <p>
 * 点数ユーティリティクラスです。
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
public class ScoreUtil {

    /** 点数マップ */
    private static final Map<String, int[]> SCORES = new HashMap<String, int[]>();

    /**
     * 定数を初期化します。
     */
    static {

        SCORES.put("20|2", new int[] {1300, 2000});
        SCORES.put("20|3", new int[] {2600, 3900});
        SCORES.put("20|4", new int[] {5200, 7700});
        SCORES.put("25|2", new int[] {1600, 2400});
        SCORES.put("25|3", new int[] {3200, 4800});
        SCORES.put("25|4", new int[] {6400, 9600});
        SCORES.put("30|1", new int[] {1000, 1500});
        SCORES.put("30|2", new int[] {2000, 2900});
        SCORES.put("30|3", new int[] {3900, 5800});
        SCORES.put("40|1", new int[] {1300, 2000});
        SCORES.put("40|2", new int[] {2600, 3900});
        SCORES.put("40|3", new int[] {5200, 7700});
        SCORES.put("50|1", new int[] {1600, 2400});
        SCORES.put("50|2", new int[] {3200, 4800});
        SCORES.put("50|3", new int[] {6400, 9600});
        SCORES.put("60|1", new int[] {2000, 2900});
        SCORES.put("60|2", new int[] {3900, 5800});
        SCORES.put("70|1", new int[] {2300, 3400});
        SCORES.put("70|2", new int[] {4500, 6800});
        SCORES.put("80|1", new int[] {2600, 3900});
        SCORES.put("80|2", new int[] {5200, 7700});
        SCORES.put("90|1", new int[] {2900, 4400});
        SCORES.put("90|2", new int[] {5800, 8700});
        SCORES.put("100|1", new int[] {3200, 4800});
        SCORES.put("100|2", new int[] {6400, 9600});
        SCORES.put("110|2", new int[] {7100, 10600});
        SCORES.put("4", new int[] {8000, 12000});
        SCORES.put("5", new int[] {8000, 12000});
        SCORES.put("6", new int[] {12000, 18000});
        SCORES.put("7", new int[] {12000, 18000});
        SCORES.put("8", new int[] {16000, 24000});
        SCORES.put("9", new int[] {16000, 24000});
        SCORES.put("10", new int[] {16000, 24000});
        SCORES.put("11", new int[] {24000, 36000});
        SCORES.put("12", new int[] {24000, 36000});
        SCORES.put("13", new int[] {32000, 48000});
    }

    /**
     * 
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected ScoreUtil() {

    }

    /**
     * 
     * 符を計算します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void calculateFu(HandsStatusDto dto) {

        // 七対子
        if (dto.isSevenPairs) {
            dto.fu = 25;
            return;
        }

        // 副底
        dto.fu = 20;

        if (dto.isRon) {
            if (dto.conceal) {
                // 面前ロン
                dto.fu += 10;
            }
        } else {
            if (!dto.isAllRuns) {
                // 自摸
                dto.fu += 2;
            }
        }

        // 平和の場合はここで抜ける。
        if (dto.isAllRuns) {
            return;
        }

        if (dto.isSingle || dto.isSingleSide || dto.isSpace) {
            // 両面以外
            dto.fu += 2;
        }

        if (HandUtil.isValueTile(dto.eyes) || HandUtil.isWindTile(dto.eyes, dto)
            || HandUtil.isSelfWindTile(dto.eyes, dto)) {

            // 雀頭が役牌
            dto.fu += 2;
        }

        // あがり牌が順子に含まれているかを判定
        boolean contains = false;
        for (Integer[] chow : dto.chows) {
            if (Arrays.asList(chow).contains(dto.winningTile)) {
                contains = true;
            }
        }

        // 刻子を判定
        for (Integer pung : dto.pungs) {

            if (Arrays.asList(HandUtil.TERMINALS_AND_HONORS).contains(pung)) {
                if (!dto.isRon) {
                    // ヤオ九牌暗刻
                    dto.fu += 8;
                } else {
                    if (dto.winningTile == pung) {
                        if (contains) {
                            // ヤオ九牌暗刻
                            dto.fu += 8;
                        } else {
                            // ヤオ九牌明刻
                            dto.fu += 4;
                        }
                    } else {
                        // ヤオ九牌暗刻
                        dto.fu += 8;
                    }
                }
            } else {
                if (!dto.isRon) {
                    // 中張牌暗刻
                    dto.fu += 4;
                } else {
                    if (dto.winningTile == pung) {
                        if (contains) {
                            // 中張牌暗刻
                            dto.fu += 4;
                        } else {
                            // 中張牌明刻
                            dto.fu += 2;
                        }
                    } else {
                        // 中張牌暗刻
                        dto.fu += 4;
                    }
                }
            }
        }

        // 切り上げ
        dto.fu = (int)(Math.ceil((double)dto.fu / 10) * 10);
    }

    /**
     * 
     * 点数を設定します。
     * 
     * @param dto {@link HandsStatusDto}
     */
    public static void setScore(HandsStatusDto dto) {

        // 点数マップ値の添え字
        int idx = 0;
        if (dto.isDealer) {
            idx = 1;
        }

        // 役満
        if (dto.grandSlamCounter > 0) {
            dto.score = SCORES.get("13")[idx] * dto.grandSlamCounter;
            return;
        }

        String fan = String.valueOf(dto.fan);
        String key = String.valueOf(dto.fu) + "|" + fan;

        if (SCORES.containsKey(key)) {
            dto.score = SCORES.get(key)[idx];
            return;
        }

        if (SCORES.containsKey(fan)) {
            dto.score = SCORES.get(fan)[idx];
        }
    }
}
