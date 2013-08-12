/**
 * 
 */
package jp.co.headwaters.jacpot.function.apl;

import jp.co.headwaters.jacpot.MainActivity;
import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.common.CallbackListener;
import jp.co.headwaters.jacpot.function.mahjong.util.HandsJudgmentUtil;
import jp.co.headwaters.jacpot.function.mahjong.util.ResourceUtil;
import jp.co.headwaters.jacpot.view.ChooseTilesTableLayout;
import jp.co.headwaters.jacpot.view.ChooseWinningTilesTableLayout;
import jp.co.headwaters.jacpot.view.CountDownTimerLinearLayout;
import jp.co.headwaters.jacpot.view.DragonTableLayout;
import jp.co.headwaters.jacpot.view.FanTextView;
import jp.co.headwaters.jacpot.view.RoundTextView;
import jp.co.headwaters.jacpot.view.ScoreTextView;
import jp.co.headwaters.jacpot.view.SelectedTilesTableLayout;
import jp.co.headwaters.jacpot.view.YakuTableLayout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
public class MakeReadyHandsActivity extends Activity implements CallbackListener {

    /** 最大ステージカウント */
    private static final int STAGE_MAX_CNT = 3;

    /** レイアウト切替インターバル(ミリ秒) */
    private static final long CHANGE_LAYOUT_INTERVAL = 2000;

    /** 開始秒数配列(ミリ秒) */
    private static final long[] MILLIS_IN_FUTURES = new long[]{30000, 20000, 10000};

    /** Warningメッセージ(手牌は13枚で構成してください。) */
    private static final String W_MSG_SPECIFIED_SIZE = "手牌は13枚で構成してください。";

    /** 現在のステージ */
    private int currentStage;

    /** 総得点 */
    private int totalScore;

    /** {@link CountDownTimerLinearLayout} */
    private CountDownTimerLinearLayout countDownTimer;

    /** ドラ表示エリア{@link TableRow} */
    private TableRow dragonTableRow;

    /** {@link ChooseTilesTableLayout} */
    private ChooseTilesTableLayout chooseTilesTableLayout;

    /** 手牌エリア{@link TableRow} */
    private TableRow selectedTilesTableRow;

    /** {@link ChooseWinningTilesTableLayout} */
    private ChooseWinningTilesTableLayout chooseWinningTilesTableLayout;

    /**
     * +@id/btnMakeReadyHandsClearの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener clearClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            chooseTilesTableLayout.setChooseTilesImageViews();
            chooseTilesTableLayout.clearSelectedTilesResources();
        }
    };

    /**
     * +@id/btnMakeReadyHandsOkの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener okClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!chooseTilesTableLayout.isSpecifiedSize()) {
                Toast.makeText(MakeReadyHandsActivity.this, W_MSG_SPECIFIED_SIZE,
                               Toast.LENGTH_SHORT).show();
                return;
            }

            // タイマーのキャンセル
            countDownTimer.cancel();

            // レイアウト変更
            changeLayout();
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
                setMainLayout();
            } else {
                setFinishLayout();
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
        DragonTableLayout dtl = (DragonTableLayout)findViewById(R.id.tableLayoutDragon);
        dtl.init();
        this.dragonTableRow = dtl.getRecycleTableRow();
        // ---------------------------------------------
        // (4) 牌選択エリア設定
        // ---------------------------------------------
        this.chooseTilesTableLayout =
                        (ChooseTilesTableLayout)findViewById(R.id.tableLayoutChooseTiles);
        this.chooseTilesTableLayout.init();
        // ---------------------------------------------
        // (5) 手牌エリア設定
        // ---------------------------------------------
        SelectedTilesTableLayout sttl =
                        (SelectedTilesTableLayout)findViewById(R.id.tableLayoutSelectedTiles);
        sttl.init(this.chooseTilesTableLayout.getSelectedTilesImageViews());
        this.selectedTilesTableRow = sttl.getRecycleTableRow();
        // ---------------------------------------------
        // (6) 利用回数初期化
        // ---------------------------------------------
        ResourceUtil.initResourceIdIncRedToUseCnt();
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
        this.countDownTimer =
                        (CountDownTimerLinearLayout)findViewById(R.id.linearLayoutCountDownTimer);
        this.countDownTimer.start(MILLIS_IN_FUTURES[currentStage - 1]);
    }

    /**
     * 場を設定します。
     */
    private void setRound() {

        ((RoundTextView)findViewById(R.id.textViewRound)).setRound(currentStage - 1);
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
        ((DragonTableLayout)findViewById(R.id.tableLayoutDragon)).resetView(this.dragonTableRow);
        // ---------------------------------------------
        // (4) 手牌エリア設定
        // ---------------------------------------------
        ((SelectedTilesTableLayout)findViewById(R.id.tableLayoutSelectedTiles))
                        .resetView(this.selectedTilesTableRow);
        // ---------------------------------------------
        // (5) あがり牌選択エリア設定
        // ---------------------------------------------
        this.chooseWinningTilesTableLayout =
                        (ChooseWinningTilesTableLayout)findViewById(R.id.tableLayoutChooseWinningTiles);
        this.chooseWinningTilesTableLayout.init();
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
        ((DragonTableLayout)findViewById(R.id.tableLayoutDragon)).resetView(dragonTableRow);
        // ---------------------------------------------
        // (4) 得点設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(0);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void callback(View v) {
        if (v instanceof CountDownTimerLinearLayout) {
            this.changeLayout();
        }
        if (v instanceof ChooseWinningTilesTableLayout) {
            this.setYakuInfo();
        }
    }

    /**
     * 
     * レイアウトを変更します。
     * 
     */
    private void changeLayout() {

        if (HandsJudgmentUtil.isReadyHands(this.chooseTilesTableLayout
                        .getSelectedTilesResourceIds())) {
            this.setResultSuccessLayout();
        } else {
            this.setResultFailureLayout();
            new Handler().postDelayed(autoChangeLayout, CHANGE_LAYOUT_INTERVAL);
        }

        currentStage++;
    }

    /**
     * 
     * 役情報を設定します。
     * 
     */
    private void setYakuInfo() {
        // ---------------------------------------------
        // (1) あがりタイプ設定(ツモ固定)
        // ---------------------------------------------
        ResourceUtil.completeHandsStatusDto.isRon = false;
        // ---------------------------------------------
        // (2) 局情報設定
        // ---------------------------------------------
        ResourceUtil.setCurrentRoundInfo(currentStage - 1);
        // ---------------------------------------------
        // (3) あがり形解析
        // ---------------------------------------------
        HandsJudgmentUtil.analyzeCompleteHands(this.chooseTilesTableLayout
                        .getSelectedTilesResourceIds(), this.chooseWinningTilesTableLayout
                        .getWinningTileResourceId());

        // ---------------------------------------------
        // (4) 役設定
        // ---------------------------------------------
        ((YakuTableLayout)findViewById(R.id.tableLayoutYaku))
                        .setYaku(ResourceUtil.completeHandsStatusDto);

        // ---------------------------------------------
        // (5) 符、翻の設定
        // ---------------------------------------------
        if (ResourceUtil.completeHandsStatusDto.isGrandSlam) {
            ((FanTextView)findViewById(R.id.textViewFan)).setGrandSlam();
        } else {
            ((FanTextView)findViewById(R.id.textViewFan))
                            .setFan(ResourceUtil.completeHandsStatusDto.fu,
                                    ResourceUtil.completeHandsStatusDto.fan);
        }
        // ---------------------------------------------
        // (6) 点数設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(12000);
        new Handler().postDelayed(autoChangeLayout, CHANGE_LAYOUT_INTERVAL);
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
