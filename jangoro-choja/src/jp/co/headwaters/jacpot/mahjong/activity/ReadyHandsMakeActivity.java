/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.activity;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.CallbackListener;
import jp.co.headwaters.jacpot.mahjong.constant.CommonConst;
import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;
import jp.co.headwaters.jacpot.mahjong.entity.E001StatusEntity;
import jp.co.headwaters.jacpot.mahjong.entity.service.E001StatusService;
import jp.co.headwaters.jacpot.mahjong.message.Message;
import jp.co.headwaters.jacpot.mahjong.util.HandUtil;
import jp.co.headwaters.jacpot.mahjong.util.HandsJudgmentUtil;
import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import jp.co.headwaters.jacpot.mahjong.util.ScoreUtil;
import jp.co.headwaters.jacpot.mahjong.view.TilesSelectTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.WinningTilesSelectTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.CountDownTimerLinearLayout;
import jp.co.headwaters.jacpot.mahjong.view.DragonTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.FanTextView;
import jp.co.headwaters.jacpot.mahjong.view.HandTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.RoundTextView;
import jp.co.headwaters.jacpot.mahjong.view.ScoreTextView;
import jp.co.headwaters.jacpot.mahjong.view.ReadyHandsTilesTableLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
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
public class ReadyHandsMakeActivity extends Activity implements CallbackListener {

    /** 最大ステージカウント */
    private static final int STAGE_MAX_CNT = 3;

    /** 開始秒数配列(ミリ秒) */
    private static final long[] MILLIS_IN_FUTURES = new long[] {40000, 30000, 20000};

    /** 現在のステージ */
    private int currentStage;

    /** 総得点 */
    private int totalScore;

    /** {@link CountDownTimerLinearLayout} */
    private CountDownTimerLinearLayout countDownTimer;

    /** {@link DragonTableLayout} */
    private DragonTableLayout dragonTableLayout;

    /** ドラ表示エリア{@link TableRow} */
    private TableRow dragonTableRow;

    /** {@link TilesSelectTableLayout} */
    private TilesSelectTableLayout tilesSelectTableLayout;

    /** 手牌エリア{@link TableRow} */
    private TableRow readyHandsTilesTableRow;

    /** {@link WinningTilesSelectTableLayout} */
    private WinningTilesSelectTableLayout winningTilesSelectTableLayout;

    /** {@link HandsStatusDto} */
    private HandsStatusDto dto;

    /** 成功時{@link MediaPlayer} */
    private MediaPlayer win;

    /** 失敗時{@link MediaPlayer} */
    private MediaPlayer failure;

    /** 終了時{@link MediaPlayer} */
    private MediaPlayer finish;
    
    /** {@link E001StatusService} */
    private E001StatusService service;
    
    /** {@link E001StatusEntity} */
    private E001StatusEntity entity;

    /**
     * +@id/btnMakeReadyHandsMainClearの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener clearClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            tilesSelectTableLayout.setFromTilesImageViews();
            tilesSelectTableLayout.clearToTilesResources();
        }
    };

    /**
     * +@id/btnMakeReadyHandsMainOkの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener okClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!tilesSelectTableLayout.isSpecifiedSize()) {
                Toast.makeText(ReadyHandsMakeActivity.this, Message.W_MSG_SPECIFIED_SIZE,
                               Toast.LENGTH_SHORT).show();
                return;
            }

            // タイマーのキャンセル
            countDownTimer.cancel();

            // 結果表示
            displayResult();
        }
    };

    /**
     * +@id/btnMakeReadyHandsResultXxxNextの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener nextClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (win.isPlaying()) {
                win.pause();
            }

            if (failure.isPlaying()) {
                failure.pause();
            }

            if (currentStage < STAGE_MAX_CNT) {
                setMainLayout();
            } else {
                setFinishLayout();
            }
        }
    };

    /**
     * +@id/btnMakeReadyHandsFinishRestartの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener restartClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (finish.isPlaying()) {
                finish.pause();
            }

            init();
        }
    };

    /**
     * +@id/btnMakeReadyHandsFinishGotoMenuの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener gotoMenuClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (finish.isPlaying()) {
                finish.pause();
            }

            service.close();
            startActivity(new Intent(ReadyHandsMakeActivity.this, MenuActivity.class));
            finish();
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 戻るボタン押下時の処理
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // ダイアログの設定
            Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(CommonConst.MENU_TITLE_FINISH);
            builder.setMessage(Message.I_MSG_FINISH);

            // 「はい」が押下された場合の処理
            builder.setPositiveButton(CommonConst.TEXT_YES, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    countDownTimer.cancel();
                    service.close();
                    finish();
                }
            });

            // 「いいえ」が押下された場合の処理
            builder.setNegativeButton(CommonConst.TEXT_NO, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 何もしない。
                }
            });

            builder.show();
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        this.win.release();
        this.failure.release();
        this.finish.release();
        super.onDestroy();
    }

    /**
     * 
     * 初期化処理です。
     * 
     */
    private void init() {
        // ---------------------------------------------
        // (1) プロパティ初期化
        // ---------------------------------------------
        this.currentStage = 0;
        this.totalScore = 0;
        // ---------------------------------------------
        // (2) Entity取得
        // ---------------------------------------------
        this.service = new E001StatusService(this);
        this.service.open();
        this.entity = this.service.find();
        // ---------------------------------------------
        // (3) レイアウトの設定
        // ---------------------------------------------
        this.setMainLayout();
    }

    /**
     * メインレイアウトを設定します。
     * 
     */
    private void setMainLayout() {

        this.currentStage++;
        // ---------------------------------------------
        // (1) 利用回数初期化
        // ---------------------------------------------
        ResourceUtil.init();
        // ---------------------------------------------
        // (2) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_ready_hands_make_main);
        // ---------------------------------------------
        // (3) 場の設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (4) ドラ表示エリア設定
        // ---------------------------------------------
        this.dragonTableLayout = (DragonTableLayout)findViewById(R.id.tableLayoutDragon);
        this.dragonTableLayout.init();
        this.dragonTableRow = this.dragonTableLayout.getRecycleTableRow();
        // ---------------------------------------------
        // (5) 牌選択エリア設定
        // ---------------------------------------------
        this.tilesSelectTableLayout =
            (TilesSelectTableLayout)findViewById(R.id.tableLayoutTilesSelect);
        this.tilesSelectTableLayout.init();
        // ---------------------------------------------
        // (6) 手牌エリア設定
        // ---------------------------------------------
        ReadyHandsTilesTableLayout sttl =
            (ReadyHandsTilesTableLayout)findViewById(R.id.tableLayoutReadyHandsTiles);
        sttl.init(this.tilesSelectTableLayout.getToTilesImageViews());
        this.readyHandsTilesTableRow = sttl.getRecycleTableRow();
        // ---------------------------------------------
        // (7) クリアボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeMainClear))
                        .setOnClickListener(this.clearClickListener);
        // ---------------------------------------------
        // (8) OKボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeMainOk))
                        .setOnClickListener(this.okClickListener);
        // ---------------------------------------------
        // (9) 音源設定
        // ---------------------------------------------
        this.win = MediaPlayer.create(this, R.raw.win);
        this.failure = MediaPlayer.create(this, R.raw.failure);
        this.finish = MediaPlayer.create(this, R.raw.finish);
        // ---------------------------------------------
        // (10) カウントダウン開始
        // ---------------------------------------------
        this.countDownTimer =
            (CountDownTimerLinearLayout)findViewById(R.id.linearLayoutCountDownTimer);
        this.countDownTimer.start(MILLIS_IN_FUTURES[this.currentStage - 1]);
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
        setContentView(R.layout.ac_ready_hands_make_result_success);
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
        ((ReadyHandsTilesTableLayout)findViewById(R.id.tableLayoutReadyHandsTiles))
                        .resetView(this.readyHandsTilesTableRow);
        // ---------------------------------------------
        // (5) あがり牌選択エリア設定
        // ---------------------------------------------
        this.winningTilesSelectTableLayout =
            (WinningTilesSelectTableLayout)findViewById(R.id.tableLayoutWinningTilesSelect);
        this.winningTilesSelectTableLayout.init();
        // ---------------------------------------------
        // (6) 次へボタン設定
        // ---------------------------------------------
        Button btn = (Button)findViewById(R.id.btnReadyHandsMakeResultSuccessNext);
        btn.setOnClickListener(this.nextClickListener);
        btn.setEnabled(false);
        // ---------------------------------------------
        // (7) 音源設定
        // ---------------------------------------------
        this.win.seekTo(0);
        this.win.start();
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
        setContentView(R.layout.ac_ready_hands_make_result_failure);
        // ---------------------------------------------
        // (2) 場設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (3) ドラ表示エリア設定
        // ---------------------------------------------
        ((DragonTableLayout)findViewById(R.id.tableLayoutDragon)).resetView(dragonTableRow);
        // ---------------------------------------------
        // (4) 手牌エリア設定
        // ---------------------------------------------
        ((ReadyHandsTilesTableLayout)findViewById(R.id.tableLayoutReadyHandsTiles))
                        .resetView(this.readyHandsTilesTableRow);
        // ---------------------------------------------
        // (5) 得点設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(0);
        // ---------------------------------------------
        // (6) 次へボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeResultFailureNext))
                        .setOnClickListener(this.nextClickListener);
        // ---------------------------------------------
        // (7) 音源設定
        // ---------------------------------------------
        this.failure.seekTo(0);
        this.failure.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void callback(View v) {
        if (v instanceof CountDownTimerLinearLayout) {
            this.displayResult();
        }
        if (v instanceof WinningTilesSelectTableLayout) {
            this.setHandInfo();
        }
        if (v instanceof HandTableLayout) {
            this.setScoreInfo();
        }
    }

    /**
     * 
     * 結果レイアウトを表示します。
     * 
     */
    private void displayResult() {

        if (HandsJudgmentUtil.isReadyHands(this.tilesSelectTableLayout
                        .getToTilesResourceIds())) {
            this.setResultSuccessLayout();
        } else {
            this.setResultFailureLayout();
        }
    }

    /**
     * 
     * 役情報を設定します。
     * 
     */
    private void setHandInfo() {
        this.dto = new HandsStatusDto();
        // ---------------------------------------------
        // (1) あがりタイプ設定(ツモ固定)
        // ---------------------------------------------
        this.dto.isRon = false;
        // ---------------------------------------------
        // (2) 局情報設定
        // ---------------------------------------------
        ResourceUtil.setDragon(ResourceUtil.resourceIdToIdx.get(this.dragonTableLayout
                        .getDragonId()), this.dto);
        ResourceUtil.setCurrentRoundInfo(this.currentStage - 1, this.dto);
        // ---------------------------------------------
        // (3) あがり形解析
        // ---------------------------------------------
        HandsJudgmentUtil.analyzeCompleteHands(this.tilesSelectTableLayout
                        .getToTilesResourceIds(), this.winningTilesSelectTableLayout
                        .getWinningTileResourceId(), this.dto);

        // ---------------------------------------------
        // (4) 役設定
        // ---------------------------------------------
        ((HandTableLayout)findViewById(R.id.tableLayoutHand)).setHands(HandUtil
                        .createHands(this.dto, this.entity));
    }

    /**
     * 得点情報を設定します。
     */
    private void setScoreInfo() {
        // ---------------------------------------------
        // (1) 符、翻の設定
        // ---------------------------------------------
        if (this.dto.grandSlamCounter > 0) {
            ((FanTextView)findViewById(R.id.textViewFan)).setGrandSlam();
        } else {
            ((FanTextView)findViewById(R.id.textViewFan)).setFan(this.dto.fu, this.dto.fan);
        }
        // ---------------------------------------------
        // (2) 点数設定
        // ---------------------------------------------
        ScoreUtil.setScore(this.dto);
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(this.dto.score);
        this.totalScore += this.dto.score;
        // ---------------------------------------------
        // (3) 次へボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeResultSuccessNext)).setEnabled(true);
    }

    /**
     * 
     * 終了レイアウトを設定します。
     * 
     */
    private void setFinishLayout() {

        setContentView(R.layout.ac_ready_hands_make_finish);
        // ---------------------------------------------
        // (1) 総得点点設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(this.totalScore);
        // ---------------------------------------------
        // (2) もう一回ボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeFinishRestart))
                        .setOnClickListener(this.restartClickListener);
        // ---------------------------------------------
        // (3) メニューへボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnReadyHandsMakeFinishGotoMenu))
                        .setOnClickListener(this.gotoMenuClickListener);
        // ---------------------------------------------
        // (4) 音源設定
        // ---------------------------------------------
        this.finish.seekTo(0);
        this.finish.start();
        // ---------------------------------------------
        // (5) DB更新
        // ---------------------------------------------
        if (this.entity.bestScore < this.totalScore) {
            this.entity.bestScore = this.totalScore;
        }
        this.service.update(entity);
    }

}
