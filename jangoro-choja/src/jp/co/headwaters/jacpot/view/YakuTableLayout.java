/**
 * 
 */
package jp.co.headwaters.jacpot.view;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.function.mahjong.dto.CompleteHandsStatusDto;
import android.content.Context;
import android.util.AttributeSet;
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
public class YakuTableLayout extends TableLayout {

    /** 役表示エリア{@link TextView}上限 */
    private static final int DISPLAY_YAKU_AREA_TEXT_VIEW_LIMIT = 2;

    /** 役のテキストサイズ */
    private static final float TEXT_SIZE_YAKU = 20;
    
    /** テキスト(門前自摸) */
    private static final String SELF_DRAW = "門前自摸 1翻";
    
    /** テキスト(ドラ) */
    private static final String DRAGON = "ドラ";

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public YakuTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 
     * 役を設定します。
     * 
     * @param dto {@link CompleteHandsStatusDto}
     */
    public void setYaku(CompleteHandsStatusDto dto) {

        List<String> yakus = this.createYakus(dto);

        TableRow tr = null;
        for (int i = 0; i < yakus.size(); i++) {

            // TableRowに設定するTextViewの個数を判定
            if (i % DISPLAY_YAKU_AREA_TEXT_VIEW_LIMIT == 0) {
                tr = new TableRow(getContext());
                super.addView(tr);
            }

            TextView tv = new TextView(getContext());
            tv.setText(yakus.get(i));
            tv.setTextSize(TEXT_SIZE_YAKU);
            tr.addView(tv);
        }
    }

    /**
     * 
     * 役リストを生成します。
     * 
     * @param dto {@link CompleteHandsStatusDto}
     * @return 役リスト
     */
    private List<String> createYakus(CompleteHandsStatusDto dto) {

        List<String> yakus = new ArrayList<String>();

        if (!dto.isRon) {
            yakus.add(SELF_DRAW);
            dto.fan += 1;
        }
        if (dto.dragonCnt > 0) {
            yakus.add(DRAGON + " " + dto.dragonCnt);
            dto.fan += dto.dragonCnt;
        }
        return yakus;
    }

}
