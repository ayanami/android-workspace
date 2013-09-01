/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.text.MessageFormat;
import java.text.NumberFormat;

import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * <p>
 * 得点を表示する{@link TextView}クラスです。
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
public class ScoreTextView extends TextView {

    /** 得点 */
    private static final String SCORE = "{0}点";

    /** 純カラの場合のテキスト */
    private static final String EMPTY = "(純カラ)";

    /** {@link Animation}のパラメータ配列 */
    private static final float[] ANIMATION_PARAMS = new float[] {0.0f, 1.0f};

    /** {@link Animation}の継続時間 */
    private static final long ANIMATION_DURATION = 2000L;

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public ScoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 
     * 編集後の得点を設定します。。
     * 
     * @param score 得点
     */
    public void setScore(int score) {

        String formatScore =
            MessageFormat.format(SCORE, new Object[] {NumberFormat.getInstance().format(score)});
        if (ResourceUtil.isEmptyWinningTiles) {
            formatScore += EMPTY;
            ResourceUtil.isEmptyWinningTiles = false;
        }
        super.setText(formatScore);
        this.startAnimation(this.getAlphaAnimation());
    }

    /**
     * 
     * {@link AlphaAnimation}を返却します。
     * 
     * @return {@link AlphaAnimation}
     */
    private Animation getAlphaAnimation() {
        Animation animation = new AlphaAnimation(ANIMATION_PARAMS[0], ANIMATION_PARAMS[1]);
        animation.setDuration(ANIMATION_DURATION);
        return animation;
    }
}
