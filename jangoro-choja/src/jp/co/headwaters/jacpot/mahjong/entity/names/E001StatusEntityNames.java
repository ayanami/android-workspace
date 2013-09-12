/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity.names;

/**
 * <p>
 * ステータス<code>Entity</code>名称クラスです。
 * </p>
 * 
 * 作成日：2013/08/31<br>
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
 * <td>2013/08/31</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public enum E001StatusEntityNames {

    /** カラム名(ID) */
    ID(0),

    /** カラム名(称号) */
    TITLE(1),

    /** カラム名(最高得点) */
    BEST_SCORE(2),

    /** カラム名(断ヤオ) */
    ALL_SIMPLES(3),

    /** カラム名(平和) */
    ALL_RUNS(4),

    /** カラム名(一盃口) */
    DOUBLE_RUN(5),

    /** カラム名(七対子) */
    SEVEN_PAIRS(6),

    /** カラム名(全帯) */
    MIXED_OUTSIDE_HAND(7),

    /** カラム名(三色同順) */
    THREE_COLOR_RUNS(8),

    /** カラム名(一気通貫) */
    FULL_STRAIGHT(9),

    /** カラム名(対々和) */
    ALL_TRIPLES(10),

    /** カラム名(三色同刻) */
    THREE_COLOR_TRIPLES(11),

    /** カラム名(三暗刻) */
    THREE_CONCEALED_TRIPLES(12),

    /** カラム名(混老頭) */
    ALL_TERMINALS_AND_HONORS(13),

    /** カラム名(小三元) */
    LITTLE_DRAGONS(14),

    /** カラム名(二盃口) */
    TWO_DOUBLE_RUNS(15),

    /** カラム名(純全帯) */
    PURE_OUTSIDE_HAND(16),

    /** カラム名(混一色) */
    HALF_FLASH(17),

    /** カラム名(清一色) */
    FULL_FLASH(18),

    /** カラム名(国士無双) */
    THIRTEEN_ORPHANS(19),

    /** カラム名(九蓮宝燈) */
    NINE_TRESURES(20),

    /** カラム名(四暗刻) */
    FOUR_CONCEALED_TRIPLES(21),

    /** カラム名(字一色) */
    ALL_HONORS(22),

    /** カラム名(大四喜) */
    BIG_FOUR_WINDS(23),

    /** カラム名(小四喜) */
    SMALL_FOUR_WINDS(24),

    /** カラム名(緑一色) */
    ALL_GREEN(25),

    /** カラム名(大三元) */
    BIG_DRAGONS(26),

    /** カラム名(清老頭) */
    TERMINALS(27),
    
    /** カラム名(最新更新日時) */
    LAST_UPDATE_DATETIME(28);
    
    /** カラムの添え字 */
    private int index;
    
    /**
     * コンストラクタです。
     * @param index カラムの添え字
     */
    private E001StatusEntityNames(int index) {
        this.index = index;
    }
    
    /**
     * カラムの添え字を返却します。
     * 
     * @return カラムの添え字
     */
    public int getIndex() {
        return this.index;
    }
}
