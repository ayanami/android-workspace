/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.util.concurrent.TimeUnit;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.CallbackListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * <p>
 * カウントダウンタイマー用{@link LinearLayout}クラスです。
 * </p>
 * 
 * 作成日：2013/08/07<br>
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
 * <td>2013/08/07</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class CountDownTimerLinearLayout extends LinearLayout {

    /** テキスト(残り) */
    private static final String TEXT_TO_GO = "残り";

    /** テキスト(秒) */
    private static final String TEXT_SECOND = "秒";

    /** <code>secondToGo</code>のテキストサイズ */
    private static final float TEXT_SIZE_SECOND_TO_GO = 20;

    /** カウントダウンインターバル(ミリ秒) */
    private static final long COUNT_DONW_INTERVAL = 1000;

    /** 音楽切替時上限(ミリ秒) */
    private static final long SWITCH_MUSIC_UPPER_LIMIT = 11000;

    /** 音楽切替時下限(ミリ秒) */
    private static final long SWITCH_MUSIC_LOWER_LIMIT = 10000;

    /** 残り秒数を表示する{@link TextView} */
    private TextView secondToGo;

    /** {@link CountDownTimer} */
    private CountDownTimer countDownTimer;
    
    /** <code>countDownTimer</code>が作動中かを判定するフラグ */
    private boolean works;
    
    /** 対象の{@link Activity} */
    private Activity target;

    /** 通常時{@link MediaPlayer} */
    private MediaPlayer main;

    /** 残り10秒を切った場合{@link MediaPlayer} */
    private MediaPlayer quick;

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public CountDownTimerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
        this.target = (Activity)context;
    }

    /**
     * 
     * 初期化処理です。
     * 
     * @param context {@link Context}
     */
    private void init(Context context) {
        TextView tv = new TextView(context);
        tv.setText(TEXT_TO_GO);
        this.addView(tv);

        this.secondToGo = new TextView(context);
        this.secondToGo.setTextColor(Color.RED);
        this.secondToGo.setTextSize(TEXT_SIZE_SECOND_TO_GO);
        this.secondToGo.setTypeface(Typeface.DEFAULT_BOLD);
        this.addView(this.secondToGo);

        tv = new TextView(context);
        tv.setText(TEXT_SECOND);
        this.addView(tv);

        this.main = MediaPlayer.create(context, R.raw.main);

        this.quick = MediaPlayer.create(context, R.raw.quick);
    }

    /**
     * 
     * カウントダウンを開始します。
     * 
     * @param millisInFuture 開始秒数(ミリ秒)
     */
    public void start(long millisInFuture) {
        this.countDownTimer = this.getCountDownTimer(millisInFuture);
        this.countDownTimer.start();
        this.works = true;
        this.startMain();
    }

    /**
     * 
     * カウントダウンをキャンセルします。
     * 
     */
    public void cancel() {
        if (!this.works) {
            return;
        }
        this.countDownTimer.cancel();
        this.works = false;
        this.stopMain();
        this.stopQuick();
    }

    /**
     * {@link CountDownTimer}を返却します。
     * 
     * @param millisInFuture 開始秒数(ミリ秒)
     * @return {@link CountDownTimer}
     */
    private CountDownTimer getCountDownTimer(long millisInFuture) {

        return new CountDownTimer(millisInFuture, COUNT_DONW_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished < SWITCH_MUSIC_UPPER_LIMIT
                    && millisUntilFinished > SWITCH_MUSIC_LOWER_LIMIT) {
                    stopMain();
                    startQuick();
                }
                secondToGo.setText(String.valueOf(TimeUnit.MILLISECONDS
                                .toSeconds(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                stopQuick();
                works = false;
                ((CallbackListener)target).callback(CountDownTimerLinearLayout.this);
            }
        };
    }
    
    /**
     * 
     * main{@link MediaPlayer}をスタートします。
     * 
     */
    private void startMain() {
        this.main.seekTo(0);
        this.main.start();
    }
    
    /**
     * 
     * main{@link MediaPlayer}をストップします。
     * 
     */
    private void stopMain() {

        if (this.main.isPlaying()) {
            this.main.pause();
        }
    }

    /**
     * 
     * quick{@link MediaPlayer}をスタートします。
     * 
     */
    private void startQuick() {
        this.quick.seekTo(0);
        this.quick.start();
    }
    
    /**
     * 
     * quick{@link MediaPlayer}をストップします。
     * 
     */
    private void stopQuick() {
        if (this.quick.isPlaying()) {
            this.quick.pause();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDetachedFromWindow() {
        this.main.release();
        this.quick.release();
        super.onDetachedFromWindow();
    }

}
