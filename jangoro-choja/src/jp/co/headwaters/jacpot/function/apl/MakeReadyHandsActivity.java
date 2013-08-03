/**
 * 
 */
package jp.co.headwaters.jacpot.function.apl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import jp.co.headwaters.jacpot.MainActivity;
import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.function.mahjong.util.HandsJudgmentUtil;
import jp.co.headwaters.jacpot.function.mahjong.util.ImageResourceUtil;
import jp.co.headwaters.jacpot.function.mahjong.util.ResourceUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
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

    /** カウントダウンインターバル(ミリ秒) */
    private static final long COUNT_DONW_INTERVAL = 1000;
    
    /** 開始秒数配列(ミリ秒) */
    private static final long[] MILLIS_IN_FUTURES = new long[] {40000, 30000, 20000};

    /** 現在のステージ */
    private static int currentStage;

    /** 正解数 */
    private int correctAnswerCnt;

    /** 牌選択エリア{@link ImageView}リスト */
    private List<ImageView> selectTilesImageViews = new ArrayList<ImageView>();

    /** 牌選択エリアリソースIDリスト */
    private List<Integer> selectTilesResourceIds = new ArrayList<Integer>();

    /** 手牌エリア{@link ImageView}リスト */
    private List<ImageView> readyHandsImageViews = new ArrayList<ImageView>();

    /** 手牌エリアリソースIDリスト */
    private List<Integer> readyHandsResourceIds = new ArrayList<Integer>();

    /**
     * 牌選択エリア{@link ImageView}の{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener tilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            ImageView iv = (ImageView)v;
            // ---------------------------------------------
            // (1) 手牌エリアの設定
            // ---------------------------------------------
            int currentId = Integer.parseInt(iv.getTag().toString());
            boolean isReverse = ImageResourceUtil.isReverse(currentId);

            if (!isReverse && readyHandsResourceIds.size() >= READY_HANDS_AREA_IAMGE_RESOURCE_SIZE) {
                return;
            }

            MakeReadyHandsActivity.this.setReadyHandsResourceIds(isReverse, currentId);
            MakeReadyHandsActivity.this.setImageViews(readyHandsImageViews, readyHandsResourceIds);

            // ---------------------------------------------
            // (2) 牌選択エリアの設定
            // ---------------------------------------------
            int nextId = ImageResourceUtil.getReversedResourceId(currentId);
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

            MakeReadyHandsActivity.this.changeLayout();
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
        correctAnswerCnt = 0;
        this.setMainLayout();
    }

    /**
     * レイアウトを初期化します。
     * 
     */
    private void setMainLayout() {

        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_make_ready_hands_main);
        // ---------------------------------------------
        // (2) 局の設定
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
        this.countDown(MILLIS_IN_FUTURES[currentStage - 1]);
    }

    /**
     * 局を設定します。
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
        int resouceId = ImageResourceUtil.getRandomResourceId();
        // ---------------------------------------------
        // (3) イメージリソースの設定
        // ---------------------------------------------
        TableRow tr = new TableRow(this);
        tl.addView(tr);
        for (int i = 0; i < DISPLAY_DORAGON_AREA_TILES_CNT; i++) {

            ImageView iv = new ImageView(this);

            if (i == DISPLAY_DORAGON_POS) {
                iv.setImageResource(resouceId);
            } else {
                iv.setImageResource(R.drawable.p_bk_1);
            }
            tr.addView(iv);
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
        selectTilesResourceIds =
                        ImageResourceUtil
                                        .getRandomResourceIds(SELECT_TILES_AREA_IMAGE_RESOURCE_SIZE);
        // selectTilesResourceIds = Test.getJustAsWellResourceIds(SELECT_TILES_AREA_IMAGE_RESOURCE_SIZE);
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
            iv.setOnClickListener(tilesClickListener);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
        TableRow tr = (TableRow)findViewById(R.id.tableRowDisplayReadyHands);
        // ---------------------------------------------
        // (2) ImageViewの初期化
        // ---------------------------------------------
        readyHandsResourceIds.clear();
        readyHandsImageViews.clear();
        for (int i = 0; i < READY_HANDS_AREA_IAMGE_RESOURCE_SIZE; i++) {
            ImageView iv = new ImageView(this);
            tr.addView(iv);
            readyHandsImageViews.add(iv);
        }

    }

    /**
     * カウントダウンを開始します。
     * 
     * @param millisInFuture 開始秒数(ミリ秒)
     */
    private void countDown(long millisInFuture) {

        final TextView tv = (TextView)findViewById(R.id.textViewSecondToGo);

        CountDownTimer cd = new CountDownTimer(millisInFuture, COUNT_DONW_INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                tv.setText(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
            }

            @Override
            public void onFinish() {
                MakeReadyHandsActivity.this.changeLayout();
            }
        };

        cd.start();
    }

    /**
     * 手牌エリアのイメージIDリストを設定します。
     * 
     * @param isReverse イメージリソースが反転しているか
     * @param resourceId リソースID
     */
    private void setReadyHandsResourceIds(boolean isReverse, int resourceId) {

        if (isReverse) {
            readyHandsResourceIds.remove((Object)ImageResourceUtil
                            .getReversedResourceId(resourceId));
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

        String text = null;

        if (HandsJudgmentUtil.isReadyHands(readyHandsResourceIds)) {
            text = "正解！！";
            correctAnswerCnt++;
        } else {
            text = "残念！！";
        }

        currentStage++;

        this.setResultLayout(text);

        new Handler().postDelayed(autoChangeLayout, 1000);

    }

    /**
     * 
     * 結果レイアウトを設定します。
     * 
     * @param text テキスト
     */
    private void setResultLayout(String text) {

        setContentView(R.layout.ac_make_ready_hands_result);
        ((TextView)findViewById(R.id.textViewResult)).setText(text);
    }

    /**
     * 
     * 終了レイアウトを設定します。
     * 
     */
    private void setFinishLayout() {

        setContentView(R.layout.ac_make_ready_hands_finish);
        ((TextView)findViewById(R.id.textViewFinish)).setText(correctAnswerCnt + "/"
                        + STAGE_MAX_CNT);
        ((Button)findViewById(R.id.btnMakeReadyHandsRestart))
                        .setOnClickListener(restartClickListener);
    }
}
