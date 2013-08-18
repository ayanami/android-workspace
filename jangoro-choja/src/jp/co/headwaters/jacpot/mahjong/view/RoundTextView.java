/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.text.MessageFormat;

import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <p>
 * 場を表示する{@link TextView}クラスです。
 * </p>
 * 
 * 作成日：2013/08/06<br>
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
 * <td>2013/08/06</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class RoundTextView extends TextView {

    /** 場 */
    private static final String ROUND = "{0}{1}局{2}家";

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 
     * 編集後の場を設定します。
     * 
     * @param idx 局リストの添え字
     */
    public void setRound(int idx) {
        super.setText(MessageFormat.format(ROUND, ResourceUtil.rounds.get(idx)));
    }

}
