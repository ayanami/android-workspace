/**
 * 
 */
package jp.co.headwaters.jacpot.function.apl;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.co.headwaters.jacpot.MainActivity;
import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.function.mahjong.util.HandsJudgmentUtil;
import jp.co.headwaters.jacpot.function.mahjong.util.ResourceUtil;
import jp.co.headwaters.jacpot.function.mahjong.util.Test;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>
 * 聴牌作成画面用<code>Activity</code>クラスです。
 * </p>
 * 
 * 作成日：2013/07/16<br>
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
 * <td>2013/07/16</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class MakeReadyHandsActivity extends Activity {

    /** ドラエリア牌数 */
    private static final int DISPLAY_DORAGON_AREA_TILES_CNT = 7;

    /** ドラ表示位置 */
    private static final int DISPLAY_DORAGON_POS = 2;

    /** 牌選択エリアイメージリソースサイズ */
    private static final int SELECT_TILES_AREA_IMAGE_RESOURCE_SIZE = 34;

    /** 牌選択エリア{@link ImageView}上限 */
    private static final int SELECT_TILES_AREA_IMAGE_VIEW_LIMIT = 9;

    /** 手牌エリア{@link TableRow}イメージリソースサイズ */
    private static final int READY_HANDS_AREA_IAMGE_RESOURCE_SIZE = 13;

    /** 最大ステージカウント */
    private static final int STAGE_MAX_CNT = 3;

    /** レイアウト切替インターバル(ミリ秒) */
    private static final long CHANGE_LAYOUT_INTERVAL = 2000;

    /** カウントダウンインターバル(ミリ秒) */
    private static final long COUNT_DONW_INTERVAL = 1000;

    /** 開始秒数配列(ミリ秒) */
    private static final long[] MILLIS_IN_FUTURES = new long[]{40000, 30000, 20000};

    /** 役表示エリア{@link TextView}上限 */
    private static final int DISPLAY_YAKU_AREA_TEXT_VIEW_LIMIT = 2;
    
    /** 得点 */
    private static final String SCORE = "{0}点";

    /** 飜 */
    private static final String FAN = "{0}符{1}飜";

    /** 現在のステージ */
    private int currentStage;

    /** 総得点 */
    private int totalScore;

    /** ドラ表示エリア{@link TableRow} */
    private TableRow tableRowDisplayDoragon;

    /** 牌選択エリア{@link ImageView}リスト */
    private List<ImageView> selectTilesImageViews = new ArrayList<ImageView>();

    /** 牌選択エリアリソースIDリスト */
    private List<Integer> selectTilesResourceIds = new ArrayList<Integer>();

    /** 手牌エリア{@link TableRow} */
    private TableRow tableRowDisplayReadyHands;

    /** 手牌エリア{@link ImageView}リスト */
    private List<ImageView> readyHandsImageViews = new ArrayList<ImageView>();

    /** 手牌エリアリソースIDリスト */
    private List<Integer> readyHandsResourceIds = new ArrayList<Integer>();

    /** {@link CountDownTimer} */
    private CountDownTimer countDownTimer;

    /** あがり牌が既に選択されたかを判定するフラグ */
    private boolean isSelect = false;

    /**
     * 牌選択エリア{@link ImageView}の{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener selectTilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            ImageView iv = (ImageView)v;
            // ---------------------------------------------
            // (1) 手牌エリアの設定
            // ---------------------------------------------
            int currentId = Integer.parseInt(iv.getTag().toString());
            boolean isReverse = ResourceUtil.isReverse(currentId);

            if (!isReverse && readyHandsResourceIds.size() >= READY_HANDS_AREA_IAMGE_RESOURCE_SIZE) {
                return;
            }

            MakeReadyHandsActivity.this.setReadyHandsResourceIds(isReverse, currentId);
            MakeReadyHandsActivity.this.setImageViews(readyHandsImageViews, readyHandsResourceIds);

            // ---------------------------------------------
            // (2) 牌選択エリアの設定
            // ---------------------------------------------
            int nextId = ResourceUtil.getReversedResourceId(currentId);
            iv.setImageResource(nextId);
            iv.setTag(nextId);
        }
    };

    /**
     * +@id/btnMakeReadyHandsClearの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener clearClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            // ---------------------------------------------
            // (1) 牌選択エリアの初期化
            // ---------------------------------------------
            setImageViews(selectTilesImageViews, selectTilesResourceIds);

            // ---------------------------------------------
            // (2) 手牌エリアの初期化
            // ---------------------------------------------
            MakeReadyHandsActivity.this.cleanImageViews(readyHandsImageViews);
            readyHandsResourceIds.clear();
        }
    };

    /**
     * +@id/btnMakeReadyHandsOkの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener okClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (readyHandsResourceIds.size() < READY_HANDS_AREA_IAMGE_RESOURCE_SIZE) {
                Toast.makeText(MakeReadyHandsActivity.this, "手牌は13枚で構成してください。", Toast.LENGTH_SHORT)
                                .show();
                return;
            }

            // タイマーのキャンセル
            countDownTimer.cancel();

            // レイアウト変更
            MakeReadyHandsActivity.this.changeLayout();
        }
    };

    /**
     * あがり牌選択エリア{@link ImageView}の{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener winningTilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (isSelect) {
                return;
            }
            isSelect = true;

            ImageView iv = (ImageView)v;
            int selectResourceId = Integer.parseInt(iv.getTag().toString());

            iv.setImageResource(selectResourceId);
            readyHandsResourceIds.add(selectResourceId);

            MakeReadyHandsActivity.this.analyzeCompleteHands();
        }
    };

    /**
     * +@id/btnMakeReadyHandsRestartの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener restartClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MakeReadyHandsActivity.this, MainActivity.class));
        }
    };

    /**
     * 一定時間後にレイアウトを切り替えるための{@link Runnable}匿名クラスです。
     */
    private Runnable autoChangeLayout = new Runnable() {

        @Override
        public void run() {
            if (currentStage <= STAGE_MAX_CNT) {
                MakeReadyHandsActivity.this.setMainLayout();
            } else {
                MakeReadyHandsActivity.this.setFinishLayout();
            }
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentStage = 1;
        totalScore = 0;
        this.setMainLayout();
    }

    /**
     * メインレイアウトを設定します。
     * 
     */
    private void setMainLayout() {

        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_make_ready_hands_main);
        // ---------------------------------------------
        // (2) 場の設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (3) ドラ表示エリア設定
        // ---------------------------------------------
        this.setDisplayDoragonLayout();
        // ---------------------------------------------
        // (4) 牌選択エリア設定
        // ---------------------------------------------
        this.setSelectTilesLayout();
        // ---------------------------------------------
        // (5) 手牌エリア設定
        // ---------------------------------------------
        this.setReadyHands();
        // ---------------------------------------------
        // (6) 利用回数初期化
        // ---------------------------------------------
        ResourceUtil.initTilesStatus();
        // ---------------------------------------------
        // (7) クリアボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsClear)).setOnClickListener(clearClickListener);
        // ---------------------------------------------
        // (8) OKボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsOk)).setOnClickListener(okClickListener);
        // ---------------------------------------------
        // (9) カウントダウン開始
        // ---------------------------------------------
        countDownTimer = this.getCountDownTimer(MILLIS_IN_FUTURES[currentStage - 1]);
        countDownTimer.start();
    }

    /**
     * 場を設定します。
     */
    private void setRound() {

        TextView tv = (TextView)findViewById(R.id.textViewRound);
        tv.setText(ResourceUtil.rounds.get(currentStage - 1));
    }

    /**
     * ドラ表示エリアを設定します。
     */
    private void setDisplayDoragonLayout() {

        // ---------------------------------------------
        // (1) ドラ表示エリアのTableLayoutの取得
        // ---------------------------------------------
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayoutDisplayDoragon);
        // ---------------------------------------------
        // (2) リソースIDの取得
        // ---------------------------------------------
        int resouceId = ResourceUtil.getRandomResourceId();
        // ---------------------------------------------
        // (3) イメージリソースの設定
        // ---------------------------------------------
        tableRowDisplayDoragon = new TableRow(this);
        tl.addView(tableRowDisplayDoragon);
        for (int i = 0; i < DISPLAY_DORAGON_AREA_TILES_CNT; i++) {

            ImageView iv = new ImageView(this);

            if (i == DISPLAY_DORAGON_POS) {
                iv.setImageResource(resouceId);
            } else {
                iv.setImageResource(R.drawable.p_bk_1);
            }
            tableRowDisplayDoragon.addView(iv);
        }
    }

    /**
     * 牌選択エリアを設定します。
     */
    private void setSelectTilesLayout() {

        // ---------------------------------------------
        // (1) 牌選択エリアのTableLayoutの取得
        // ---------------------------------------------
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayoutDisplaySelectTiles);
        // ---------------------------------------------
        // (2) リソースIDリストの取得
        // ---------------------------------------------
        selectTilesResourceIds.clear();
        // selectTilesResourceIds =
        // ResourceUtil.getRandomResourceIds(SELECT_TILES_AREA_IMAGE_RESOURCE_SIZE);
        selectTilesResourceIds =
                        Test.getJustAsWellResourceIds(SELECT_TILES_AREA_IMAGE_RESOURCE_SIZE);
        // ---------------------------------------------
        // (3) イメージリソースの設定
        // ---------------------------------------------
        selectTilesImageViews.clear();
        TableRow tr = null;
        for (int i = 0; i < selectTilesResourceIds.size(); i++) {

            // TableRowに設定するImageViewの個数を判定
            if (i % SELECT_TILES_AREA_IMAGE_VIEW_LIMIT == 0) {
                tr = new TableRow(this);
                tl.addView(tr);
            }

            ImageView iv = new ImageView(this);
            iv.setImageResource(selectTilesResourceIds.get(i));
            iv.setTag(selectTilesResourceIds.get(i));
            iv.setOnClickListener(selectTilesClickListener);
            selectTilesImageViews.add(iv);
            tr.addView(iv);
        }
    }

    /**
     * 手牌エリアを設定します。
     */
    private void setReadyHands() {
        // ---------------------------------------------
        // (1) 牌選択エリアのTableRowの取得
        // ---------------------------------------------
        tableRowDisplayReadyHands = (TableRow)findViewById(R.id.tableRowDisplayReadyHands);
        // ---------------------------------------------
        // (2) ImageViewの初期化
        // ---------------------------------------------
        readyHandsResourceIds.clear();
        readyHandsImageViews.clear();
        for (int i = 0; i < READY_HANDS_AREA_IAMGE_RESOURCE_SIZE; i++) {
            ImageView iv = new ImageView(this);
            tableRowDisplayReadyHands.addView(iv);
            readyHandsImageViews.add(iv);
        }

    }

    /**
     * {@link CountDownTimer}を返却します。
     * 
     * @param millisInFuture 開始秒数(ミリ秒)
     * @return {@link CountDownTimer}
     */
    private CountDownTimer getCountDownTimer(long millisInFuture) {

        final TextView tv = (TextView)findViewById(R.id.textViewSecondToGo);

        return new CountDownTimer(millisInFuture, COUNT_DONW_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                MakeReadyHandsActivity.this.changeLayout();
            }
        };

    }

    /**
     * 手牌エリアのイメージIDリストを設定します。
     * 
     * @param isReverse イメージリソースが反転しているか
     * @param resourceId リソースID
     */
    private void setReadyHandsResourceIds(boolean isReverse, int resourceId) {

        if (isReverse) {
            readyHandsResourceIds.remove((Object)ResourceUtil.getReversedResourceId(resourceId));
        } else {
            readyHandsResourceIds.add(resourceId);
        }

        Collections.sort(readyHandsResourceIds);

    }

    /**
     * {@link ImageView}リストを初期化します。
     * 
     * @param imageViews {@link ImageView}リスト
     */
    private void cleanImageViews(List<ImageView> imageViews) {

        for (ImageView iv : imageViews) {
            iv.setImageDrawable(null);
        }
    }

    /**
     * 
     * {@link ImageView}リストを設定します。
     * 
     * @param imageViews {@link ImageView}リスト
     * @param resourceIds リソースIDリスト
     */
    private void setImageViews(List<ImageView> imageViews, List<Integer> resourceIds) {

        this.cleanImageViews(imageViews);

        for (int i = 0; i < resourceIds.size(); i++) {
            imageViews.get(i).setImageResource(resourceIds.get(i));
            imageViews.get(i).setTag(resourceIds.get(i));
        }
    }

    /**
     * 
     * レイアウトを切り替えます。
     * 
     */
    private void changeLayout() {

        if (HandsJudgmentUtil.isReadyHands(readyHandsResourceIds)) {
            isSelect = false;
            this.setResultSuccessLayout();
        } else {
            this.setResultFailureLayout();
            new Handler().postDelayed(autoChangeLayout, CHANGE_LAYOUT_INTERVAL);
        }

        currentStage++;
    }

    /**
     * 
     * 結果レイアウト(成功)を設定します。
     * 
     */
    private void setResultSuccessLayout() {
        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_make_ready_hands_result_success);
        // ---------------------------------------------
        // (2) 場設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (3) ドラ表示エリア設定
        // ---------------------------------------------
        this.resetView(findViewById(R.id.tableLayoutDisplayDoragon), tableRowDisplayDoragon);
        // ---------------------------------------------
        // (4) 手牌エリア設定
        // ---------------------------------------------
        this.resetView(findViewById(R.id.tableLayoutDisplayReadyHands), tableRowDisplayReadyHands);
        // ---------------------------------------------
        // (5) あがり牌選択エリア設定
        // ---------------------------------------------
        TableRow tr = (TableRow)findViewById(R.id.tableRowDisplaySelectWinningTiles);
        for (int resourceId : ResourceUtil.winningResourceIds) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.p_bk_1);
            iv.setTag(resourceId);
            iv.setOnClickListener(winningTilesClickListener);
            tr.addView(iv);
        }
    }

    /**
     * 
     * 手役を解析します。
     * 
     */
    private void analyzeCompleteHands() {
        TableLayout tl = (TableLayout)findViewById(R.id.tableLayoutDisplayYaku);
        TableRow tr = null;
        for (int i = 0; i < 8; i++) {

            // TableRowに設定するTextViewの個数を判定
            if (i % DISPLAY_YAKU_AREA_TEXT_VIEW_LIMIT == 0) {
                tr = new TableRow(this);
                tl.addView(tr);
            }

            TextView tv = new TextView(this);
            tv.setText("hoge");
            tv.setTextSize(20);
            tr.addView(tv);
        }
        
        ((TextView)findViewById(R.id.textViewFan)).setText(FAN);
        ((TextView)findViewById(R.id.textViewScore)).setText(this.getFormatScore(12000));
        new Handler().postDelayed(autoChangeLayout, CHANGE_LAYOUT_INTERVAL);
    }

    /**
     * 
     * 結果レイアウト(失敗)を設定します。
     * 
     */
    private void setResultFailureLayout() {
        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_make_ready_hands_result_failure);
        // ---------------------------------------------
        // (2) 場設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (3) ドラ表示エリア設定
        // ---------------------------------------------
        this.resetView(findViewById(R.id.tableLayoutDisplayDoragon), tableRowDisplayDoragon);
        // ---------------------------------------------
        // (4) 得点設定
        // ---------------------------------------------
        ((TextView)findViewById(R.id.textViewScore)).setText(this.getFormatScore(0));

    }

    /**
     * 子{@link View}を親{@link View}に再設定します。
     * 
     * @param parent 親{@link View}
     * @param child 子{@link View}
     */
    private void resetView(View parent, View child) {

        ((ViewGroup)child.getParent()).removeAllViews();
        ((ViewGroup)parent).addView(child);
    }

    /**
     * 編集後の得点を返却します。
     * 
     * @param score 得点
     * @return 編集後の得点
     */
    private String getFormatScore(int score) {
        return MessageFormat.format(SCORE, new Object[]{NumberFormat.getInstance().format(score)});
    }

    /**
     * 
     * 終了レイアウトを設定します。
     * 
     */
    private void setFinishLayout() {

        setContentView(R.layout.ac_make_ready_hands_finish);
        ((TextView)findViewById(R.id.textViewFinish)).setText(String.valueOf(totalScore));
        ((Button)findViewById(R.id.btnMakeReadyHandsRestart))
                        .setOnClickListener(restartClickListener);
    }

}
