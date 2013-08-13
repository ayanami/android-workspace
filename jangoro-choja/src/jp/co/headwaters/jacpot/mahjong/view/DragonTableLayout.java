/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * <p>
 * ドラ表示エリア{@link TableLayout}クラスです。
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
public class DragonTableLayout extends TableLayout {

    /** ドラ表示エリア{@link ImageView}上限 */
    private static final int DISPLAY_DORAGON_AREA_IMAGE_VIEW_LIMIT = 7;

    /** ドラ表示位置 */
    private static final int DISPLAY_DORAGON_POS = 2;

    /** 再利用するための{@link TableRow} */
    private TableRow recycleTableRow;
    
    /** 表ドラ表示牌リソースID */
    private int dragonId;

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public DragonTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 
     * 初期化処理です。
     */
    public void init() {

        // 表ドラ表示牌
        this.dragonId = ResourceUtil.getRandomResourceId();
        ResourceUtil.setDragon(dragonId);

        this.recycleTableRow = new TableRow(getContext());
        super.addView(this.recycleTableRow);

        for (int i = 0; i < DISPLAY_DORAGON_AREA_IMAGE_VIEW_LIMIT; i++) {

            ImageView iv = new ImageView(getContext());

            if (i == DISPLAY_DORAGON_POS) {
                iv.setImageResource(this.dragonId);
            } else {
                iv.setImageResource(R.drawable.p_bk_1);
            }
            
            this.recycleTableRow.addView(iv);
        }
    }
    
    /**
     * 
     * 再利用するための{@link TableRow}を返却します。
     * 
     * @return 再利用するための{@link TableRow}
     */
    public TableRow getRecycleTableRow() {
        return this.recycleTableRow;
    }
    
    /**
     * 
     * {@link TableRow}を再設定します。
     * 
     * @param tr 再利用するための{@link TableRow}
     */
    public void resetView(TableRow tr) {
        ((ViewGroup)tr.getParent()).removeAllViews();
        this.addView(tr);
    }
}
