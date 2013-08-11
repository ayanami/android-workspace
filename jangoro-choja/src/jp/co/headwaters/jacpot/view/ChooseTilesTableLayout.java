/**
 * 
 */
package jp.co.headwaters.jacpot.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.headwaters.jacpot.function.mahjong.util.ResourceUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * <p>
 * 牌選択エリア{@link TableLayout}クラスです。
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
public class ChooseTilesTableLayout extends TableLayout {

    /** 牌選択エリアイメージリソースサイズ */
    private static final int CHOOSE_TILES_AREA_IMAGE_RESOURCE_SIZE = 34;

    /** 牌選択エリア{@link ImageView}上限 */
    private static final int CHOOSE_TILES_AREA_IMAGE_VIEW_LIMIT = 9;

    /** 手牌エリア{@link TableRow}イメージリソースサイズ */
    private static final int SELECTED_TILES_AREA_IAMGE_RESOURCE_SIZE = 13;

    /** 牌選択エリアリソースIDリスト */
    private List<Integer> chooseTilesResourceIds = new ArrayList<Integer>();

    /** 牌選択エリア{@link ImageView}リスト */
    private List<ImageView> chooseTilesImageViews = new ArrayList<ImageView>();

    /** 選択後牌エリアリソースIDリスト */
    private List<Integer> selectedTilesResourceIds = new ArrayList<Integer>();

    /** 選択後牌エリア{@link ImageView}リスト */
    private List<ImageView> selectedTilesImageViews = new ArrayList<ImageView>();

    /**
     * 牌選択エリア{@link ImageView}の{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener selectTilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            ImageView iv = (ImageView)v;
            int currentId = Integer.parseInt(iv.getTag().toString());
            boolean isReverse = ResourceUtil.isReverse(currentId);

            if (!isReverse
                            && selectedTilesResourceIds.size() >= SELECTED_TILES_AREA_IAMGE_RESOURCE_SIZE) {
                return;
            }

            setSelectedTilesResourceIds(isReverse, currentId);
            setImageViews(selectedTilesImageViews, selectedTilesResourceIds);

            // ---------------------------------------------
            // (2) 牌選択エリアの設定
            // ---------------------------------------------
            int nextId = ResourceUtil.getReversedResourceId(currentId);
            iv.setImageResource(nextId);
            iv.setTag(nextId);
        }
    };

    /**
     * コンストラクタです。
     * 
     * @param context {@link Context}
     * @param attrs {@link AttributeSet}
     */
    public ChooseTilesTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初期化処理です。
     */
    public void init() {
        this.chooseTilesResourceIds =
                        ResourceUtil.getRandomResourceIds(CHOOSE_TILES_AREA_IMAGE_RESOURCE_SIZE);

        TableRow tr = null;
        for (int i = 0; i < this.chooseTilesResourceIds.size(); i++) {

            // TableRowに設定するImageViewの個数を判定
            if (i % CHOOSE_TILES_AREA_IMAGE_VIEW_LIMIT == 0) {
                tr = new TableRow(getContext());
                super.addView(tr);
            }

            ImageView iv = new ImageView(getContext());
            iv.setImageResource(this.chooseTilesResourceIds.get(i));
            iv.setTag(this.chooseTilesResourceIds.get(i));
            iv.setOnClickListener(this.selectTilesClickListener);
            this.chooseTilesImageViews.add(iv);
            tr.addView(iv);
        }
    }

    /**
     * 選択後牌エリアのイメージIDリストを設定します。
     * 
     * @param isReverse イメージリソースが反転しているか
     * @param resourceId リソースID
     */
    private void setSelectedTilesResourceIds(boolean isReverse, int resourceId) {

        if (isReverse) {
            this.selectedTilesResourceIds.remove((Object)ResourceUtil
                            .getReversedResourceId(resourceId));
        } else {
            this.selectedTilesResourceIds.add(resourceId);
        }

        Collections.sort(this.selectedTilesResourceIds);

    }

    /**
     * 
     * 牌選択エリア{@link ImageView}リストを設定します。
     * 
     */
    public void setChooseTilesImageViews() {

        this.setImageViews(this.chooseTilesImageViews, this.chooseTilesResourceIds);
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
     * 選択後牌エリアのリソースを初期化します。
     * 
     */
    public void clearSelectedTilesResources() {
        this.cleanImageViews(this.selectedTilesImageViews);
        this.selectedTilesResourceIds.clear();
    }

    /**
     * 
     * 選択後牌エリアリソースIDリストを返却します。
     * 
     * @return 選択後牌エリアリソースIDリスト
     */
    public List<Integer> getSelectedTilesResourceIds() {
        return this.selectedTilesResourceIds;
    }

    /**
     * 
     * 選択後牌エリア{@link ImageView}リストを返却します。
     * 
     * @return 選択後牌エリア{@link ImageView}リスト
     */
    public List<ImageView> getSelectedTilesImageViews() {
        return this.selectedTilesImageViews;
    }

    /**
     * 
     * 選択後牌が規定されたサイズかを判定します。
     * 
     * @return 判定結果
     */
    public boolean isSpecifiedSize() {
        return this.selectedTilesResourceIds.size() == SELECTED_TILES_AREA_IAMGE_RESOURCE_SIZE;
    }

}
