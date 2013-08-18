/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.CallbackListener;
import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * <p>
 * あがり牌選択{@link TableLayout}クラスです。
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
public class ChooseWinningTilesTableLayout extends TableLayout {

    /** 対象の{@link Activity} */
    private Activity target;

    /** あがり牌が既に選択されたかを判定するフラグ */
    private boolean isSelect = false;

    /** あがり牌リソースID */
    private int winningTileResourceId;

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
            winningTileResourceId = Integer.parseInt(iv.getTag().toString());

            iv.setImageResource(winningTileResourceId);

            ((CallbackListener)target).callback(ChooseWinningTilesTableLayout.this);
        }
    };

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public ChooseWinningTilesTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.target = (Activity)context;
    }

    /**
     * 
     * 初期化処理です。
     * 
     */
    public void init() {
        TableRow tr = new TableRow(getContext());
        super.addView(tr);
        for (int resourceId : ResourceUtil.winningResourceIds) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(R.drawable.p_bk_1);
            iv.setTag(resourceId);
            iv.setOnClickListener(winningTilesClickListener);
            tr.addView(iv);
        }
    }

    /**
     * 
     * あがり牌リソースIDを返却します。
     * 
     * @return あがり牌リソースID
     */
    public int getWinningTileResourceId() {
        return this.winningTileResourceId;
    }

}
