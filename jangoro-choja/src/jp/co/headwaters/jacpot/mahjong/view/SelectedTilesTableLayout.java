/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * <p>
 * 選択後牌エリア{@link TableLayout}クラスです。
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
public class SelectedTilesTableLayout extends TableLayout {

    /** 手牌エリア{@link TableRow}イメージリソースサイズ */
    private static final int SELECTED_TILES_AREA_IAMGE_RESOURCE_SIZE = 13;

    /** 再利用するための{@link TableRow} */
    private TableRow recycleTableRow;

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public SelectedTilesTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 
     * 初期化処理です。
     * 
     * @param imageViews {@link ImageView}リスト
     */
    public void init(List<ImageView> imageViews) {

        this.recycleTableRow = new TableRow(getContext());
        super.addView(this.recycleTableRow);
        for (int i = 0; i < SELECTED_TILES_AREA_IAMGE_RESOURCE_SIZE; i++) {
            ImageView iv = new ImageView(getContext());
            this.recycleTableRow.addView(iv);
            imageViews.add(iv);
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
