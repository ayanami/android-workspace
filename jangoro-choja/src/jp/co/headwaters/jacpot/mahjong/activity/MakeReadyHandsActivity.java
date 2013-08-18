/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.activity;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.CallbackListener;
import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;
import jp.co.headwaters.jacpot.mahjong.util.HandUtil;
import jp.co.headwaters.jacpot.mahjong.util.HandsJudgmentUtil;
import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import jp.co.headwaters.jacpot.mahjong.util.ScoreUtil;
import jp.co.headwaters.jacpot.mahjong.view.ChooseTilesTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.ChooseWinningTilesTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.CountDownTimerLinearLayout;
import jp.co.headwaters.jacpot.mahjong.view.DragonTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.FanTextView;
import jp.co.headwaters.jacpot.mahjong.view.HandTableLayout;
import jp.co.headwaters.jacpot.mahjong.view.RoundTextView;
import jp.co.headwaters.jacpot.mahjong.view.ScoreTextView;
import jp.co.headwaters.jacpot.mahjong.view.SelectedTilesTableLayout;
import android.app.Activity;
import android.os.Bundle;
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
public class MakeReadyHandsActivity extends Activity implements CallbackListener {

    /** 最大ステージカウント */
    private static final int STAGE_MAX_CNT = 3;

    /** 開始秒数配列(ミリ秒) */
    private static final long[] MILLIS_IN_FUTURES = new long[] {40000, 30000, 20000};

    /** Warningメッセージ(手牌は13枚で構成してください。) */
    private static final String W_MSG_SPECIFIED_SIZE = "手牌は13枚で構成してください。";

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

    /** {@link ChooseTilesTableLayout} */
    private ChooseTilesTableLayout chooseTilesTableLayout;

    /** 手牌エリア{@link TableRow} */
    private TableRow selectedTilesTableRow;

    /** {@link ChooseWinningTilesTableLayout} */
    private ChooseWinningTilesTableLayout chooseWinningTilesTableLayout;

    /**
     * +@id/btnMakeReadyHandsMainClearの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener clearClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            chooseTilesTableLayout.setChooseTilesImageViews();
            chooseTilesTableLayout.clearSelectedTilesResources();
        }
    };

    /**
     * +@id/btnMakeReadyHandsMainOkの{@link OnClickListener}匿名クラスです。
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
            init();
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
     * 
     * 初期化処理です。
     * 
     */
    private void init() {
        currentStage = 0;
        totalScore = 0;
        this.setMainLayout();
    }

    /**
     * メインレイアウトを設定します。
     * 
     */
    private void setMainLayout() {

        currentStage++;
        // ---------------------------------------------
        // (1) 利用回数初期化
        // ---------------------------------------------
        ResourceUtil.init();
        // ---------------------------------------------
        // (2) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_make_ready_hands_main);
        // ---------------------------------------------
        // (3) 場の設定
        // ---------------------------------------------
        this.setRound();
        // ---------------------------------------------
        // (4) ドラ表示エリア設定
        // ---------------------------------------------
        dragonTableLayout = (DragonTableLayout)findViewById(R.id.tableLayoutDragon);
        dragonTableLayout.init();
        this.dragonTableRow = dragonTableLayout.getRecycleTableRow();
        // ---------------------------------------------
        // (5) 牌選択エリア設定
        // ---------------------------------------------
        this.chooseTilesTableLayout =
            (ChooseTilesTableLayout)findViewById(R.id.tableLayoutChooseTiles);
        this.chooseTilesTableLayout.init();
        // ---------------------------------------------
        // (6) 手牌エリア設定
        // ---------------------------------------------
        SelectedTilesTableLayout sttl =
            (SelectedTilesTableLayout)findViewById(R.id.tableLayoutSelectedTiles);
        sttl.init(this.chooseTilesTableLayout.getSelectedTilesImageViews());
        this.selectedTilesTableRow = sttl.getRecycleTableRow();
        // ---------------------------------------------
        // (7) クリアボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsMainClear))
                        .setOnClickListener(clearClickListener);
        // ---------------------------------------------
        // (8) OKボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsMainOk)).setOnClickListener(okClickListener);
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
        // ---------------------------------------------
        // (6) 次へボタン設定
        // ---------------------------------------------
        Button btn = (Button)findViewById(R.id.btnMakeReadyHandsResultSuccessNext);
        btn.setOnClickListener(nextClickListener);
        btn.setEnabled(false);
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
        // (4) 手牌エリア設定
        // ---------------------------------------------
        ((SelectedTilesTableLayout)findViewById(R.id.tableLayoutSelectedTiles))
                        .resetView(this.selectedTilesTableRow);
        // ---------------------------------------------
        // (5) 得点設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(0);
        // ---------------------------------------------
        // (6) 次へボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsResultFailureNext))
                        .setOnClickListener(nextClickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void callback(View v) {
        if (v instanceof CountDownTimerLinearLayout) {
            this.displayResult();
        }
        if (v instanceof ChooseWinningTilesTableLayout) {
            this.setYakuInfo();
        }
    }

    /**
     * 
     * 結果レイアウトを表示します。
     * 
     */
    private void displayResult() {

        if (HandsJudgmentUtil.isReadyHands(this.chooseTilesTableLayout
                        .getSelectedTilesResourceIds())) {
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
    private void setYakuInfo() {
        HandsStatusDto dto = new HandsStatusDto();
        // ---------------------------------------------
        // (1) あがりタイプ設定(ツモ固定)
        // ---------------------------------------------
        dto.isRon = false;
        // ---------------------------------------------
        // (2) 局情報設定
        // ---------------------------------------------
        ResourceUtil.setDragon(ResourceUtil.resourceIdToIdx.get(dragonTableLayout.getDragonId()),
                               dto);
        ResourceUtil.setCurrentRoundInfo(currentStage - 1, dto);
        // ---------------------------------------------
        // (3) あがり形解析
        // ---------------------------------------------
        HandsJudgmentUtil.analyzeCompleteHands(this.chooseTilesTableLayout
                        .getSelectedTilesResourceIds(), this.chooseWinningTilesTableLayout
                        .getWinningTileResourceId(), dto);

        // ---------------------------------------------
        // (4) 役設定
        // ---------------------------------------------
        ((HandTableLayout)findViewById(R.id.tableLayoutYaku)).setHands(HandUtil.createHands(dto));

        // ---------------------------------------------
        // (5) 符、翻の設定
        // ---------------------------------------------
        if (dto.grandSlamCounter > 0) {
            ((FanTextView)findViewById(R.id.textViewFan)).setGrandSlam();
        } else {
            ((FanTextView)findViewById(R.id.textViewFan)).setFan(dto.fu, dto.fan);
        }
        // ---------------------------------------------
        // (6) 点数設定
        // ---------------------------------------------
        ScoreUtil.setScore(dto);
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(dto.score);
        totalScore += dto.score;
        // ---------------------------------------------
        // (7) 次へボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsResultSuccessNext)).setEnabled(true);
    }

    /**
     * 
     * 終了レイアウトを設定します。
     * 
     */
    private void setFinishLayout() {

        setContentView(R.layout.ac_make_ready_hands_finish);
        // ---------------------------------------------
        // (1) 総得点点設定
        // ---------------------------------------------
        ((ScoreTextView)findViewById(R.id.textViewScore)).setScore(totalScore);
        // ---------------------------------------------
        // (2) 次へボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMakeReadyHandsFinishRestart))
                        .setOnClickListener(restartClickListener);
    }

}
