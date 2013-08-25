/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.text.MessageFormat;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.AnimationListenerAdapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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

    /** {@link Animation}のパラメータ配列 */
    private static final float[] ANIMATION_PARAMS = new float[] {0.0f, 1.0f};

    /** {@link Animation}の継続時間 */
    private static final long ANIMATION_DURATION = 2000L;

    /** {@link SoundPool}のパラメータ配列 */
    private static final float[] SOUNDPOOL_PARAMS = new float[] {1.0f, 1.0f, 1.0f};

    /** {@link SoundPool} */
    private SoundPool soundPool;

    /** 効果音ID(hand) */
    private int soundId;

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public FanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.soundPool = new SoundPool(0, AudioManager.STREAM_MUSIC, 0);
        this.soundId = this.soundPool.load(context, R.raw.fan, 1);
    }

    /**
     * 
     * 符、翻を設定します。
     * 
     * @param fu 符
     * @param fan 翻
     */
    public void setFan(int fu, int fan) {
        super.setText(MessageFormat.format(FAN, new Object[] {fu, fan}));
        this.startAnimation(this.getAlphaAnimation());
    }

    /**
     * 
     * 役満を設定します。
     * 
     */
    public void setGrandSlam() {
        super.setText(GRAND_SLAM);
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

        // アニメーション終了時の設定
        animation.setAnimationListener(new AnimationListenerAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                soundPool.play(soundId, SOUNDPOOL_PARAMS[0], SOUNDPOOL_PARAMS[1], 0, 0,
                               SOUNDPOOL_PARAMS[2]);
            }
        });
        return animation;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDetachedFromWindow() {
        this.soundPool.release();
        super.onDetachedFromWindow();
    }
}
