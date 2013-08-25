/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.util.Arrays;
import java.util.List;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.AnimationListenerAdapter;
import jp.co.headwaters.jacpot.mahjong.common.CallbackListener;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * <p>
 * 役表示エリア{@link TableLayout}クラスです。
 * </p>
 * 
 * 作成日：2013/08/11<br>
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
 * <td>2013/08/11</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class HandTableLayout extends TableLayout {

    /** 役表示エリア{@link TextView}上限 */
    private static final int DISPLAY_HAND_AREA_TEXT_VIEW_LIMIT = 2;

    /** 役のテキストサイズ */
    private static final float TEXT_SIZE_HAND = 20;

    /** {@link Animation}のインターバル */
    private static final long ANIMATION_INTERVAL = 500L;

    /** {@link Animation}のパラメータ配列 */
    private static final float[] ANIMATION_PARAMS = new float[] {1000.0f, 0.0f, 0.0f, 0.0f};

    /** {@link Animation}の継続時間 */
    private static final long ANIMATION_DURATION = 500L;

    /** {@link Animation}のステータス(終了) */
    private static final int ANIMATION_STATUS_FINISH = 1;

    /** {@link SoundPool}のパラメータ配列 */
    private static final float[] SOUNDPOOL_PARAMS = new float[] {1.0f, 1.0f, 1.0f};

    /** {@link Animation}のステータス配列 */
    private int[] animationStatus;

    /** 対象の{@link Activity} */
    private Activity target;

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
    public HandTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.target = (Activity)context;
        this.soundPool = new SoundPool(0, AudioManager.STREAM_MUSIC, 0);
        this.soundId = this.soundPool.load(context, R.raw.hand, 1);
    }

    /**
     * 
     * 役を設定します。
     * 
     * @param hands 役リスト
     */
    public void setHands(List<String> hands) {

        this.animationStatus = new int[hands.size()];
        Arrays.fill(this.animationStatus, 0);

        TableRow tr = null;
        long delayMillis = 0L;

        for (int i = 0; i < hands.size(); i++) {

            // TableRowに設定するTextViewの個数を判定
            if (i % DISPLAY_HAND_AREA_TEXT_VIEW_LIMIT == 0) {
                tr = new TableRow(getContext());
                super.addView(tr);
            }

            final TextView tv = new TextView(getContext());
            tr.addView(tv);
            tv.setTextSize(TEXT_SIZE_HAND);

            // アニメーションの設定
            final String hand = hands.get(i);
            final int idx = i;
            Runnable doAnimation = new Runnable() {

                @Override
                public void run() {
                    tv.setText(hand);
                    tv.startAnimation(getTranslateAnimation(idx));
                }
            };

            new Handler().postDelayed(doAnimation, delayMillis);
            delayMillis += ANIMATION_INTERVAL;
        }

    }

    /**
     * 
     * {@link TranslateAnimation}を返却します。
     * 
     * @param idx <code>animationStatus</code>の添え字
     * @return {@link TranslateAnimation}
     */
    private Animation getTranslateAnimation(final int idx) {
        Animation animation =
            new TranslateAnimation(ANIMATION_PARAMS[0], ANIMATION_PARAMS[1], ANIMATION_PARAMS[2],
                                   ANIMATION_PARAMS[3]);
        animation.setDuration(ANIMATION_DURATION);

        // アニメーション終了時の設定
        animation.setAnimationListener(new AnimationListenerAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                animationStatus[idx] = ANIMATION_STATUS_FINISH;
                soundPool.play(soundId, SOUNDPOOL_PARAMS[0], SOUNDPOOL_PARAMS[1], 0, 0,
                               SOUNDPOOL_PARAMS[2]);

                int[] finishes = new int[animationStatus.length];
                Arrays.fill(finishes, ANIMATION_STATUS_FINISH);

                if (Arrays.equals(animationStatus, finishes)) {
                    ((CallbackListener)target).callback(HandTableLayout.this);
                }
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
