/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.text.MessageFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <p>
 * 符、翻を表示する{@link TextView}クラスです。
 * </p>
 * 
 * 作成日：2013/08/05<br>
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
 * <td>2013/08/05</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class FanTextView extends TextView {

    /** 飜 */
    private static final String FAN = "{0}符{1}飜";

    /** 役満 */
    private static final String GRAND_SLAM = "役満";
    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public FanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    /**
     * 
     * 符、翻を設定します。
     * 
     * @param fu 符
     * @param fan 翻
     */
    public void setFan(int fu, int fan) {
        super.setText(MessageFormat.format(FAN, new Object[]{fu, fan}));
    }

    /**
     * 
     * 役満を設定します。
     *
     */
    public void setGrandSlam() {
        super.setText(GRAND_SLAM);
    }

}
