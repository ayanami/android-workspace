/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
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
public class TilesSelectTableLayout extends TableLayout {

    /** 牌選択エリアイメージリソースサイズ */
    private static final int FROM_TILES_AREA_IMAGE_RESOURCE_SIZE = 34;

    /** 牌選択エリア{@link ImageView}上限 */
    private static final int FROM_TILES_AREA_IMAGE_VIEW_LIMIT = 9;

    /** 手牌エリア{@link TableRow}イメージリソースサイズ */
    private static final int TO_TILES_AREA_IAMGE_RESOURCE_SIZE = 13;

    /** 牌選択エリアリソースIDリスト */
    private List<Integer> fromTilesResourceIds = new ArrayList<Integer>();

    /** 牌選択エリア{@link ImageView}リスト */
    private List<ImageView> fromTilesImageViews = new ArrayList<ImageView>();

    /** 選択後牌エリアリソースIDリスト */
    private List<Integer> toTilesResourceIds = new ArrayList<Integer>();

    /** 選択後牌エリア{@link ImageView}リスト */
    private List<ImageView> toTilesImageViews = new ArrayList<ImageView>();

    /**
     * 牌選択エリア{@link ImageView}の{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener fromTilesClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            ImageView iv = (ImageView)v;
            int currentId = Integer.parseInt(iv.getTag().toString());
            boolean isReverse = ResourceUtil.isReverse(currentId);

            if (!isReverse
                && toTilesResourceIds.size() >= TO_TILES_AREA_IAMGE_RESOURCE_SIZE) {
                return;
            }

            setToTilesResourceIds(isReverse, currentId);
            setImageViews(toTilesImageViews, toTilesResourceIds);

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
    public TilesSelectTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初期化処理です。
     */
    public void init() {
        this.fromTilesResourceIds =
            ResourceUtil.getRandomResourceIds(FROM_TILES_AREA_IMAGE_RESOURCE_SIZE);

        TableRow tr = null;
        for (int i = 0; i < this.fromTilesResourceIds.size(); i++) {

            // TableRowに設定するImageViewの個数を判定
            if (i % FROM_TILES_AREA_IMAGE_VIEW_LIMIT == 0) {
                tr = new TableRow(getContext());
                super.addView(tr);
            }

            ImageView iv = new ImageView(getContext());
            iv.setImageResource(this.fromTilesResourceIds.get(i));
            iv.setTag(this.fromTilesResourceIds.get(i));
            iv.setOnClickListener(this.fromTilesClickListener);
            this.fromTilesImageViews.add(iv);
            tr.addView(iv);
        }
        
    }

    /**
     * 選択後牌エリアのイメージIDリストを設定します。
     * 
     * @param isReverse イメージリソースが反転しているか
     * @param resourceId リソースID
     */
    private void setToTilesResourceIds(boolean isReverse, int resourceId) {

        if (isReverse) {
            this.toTilesResourceIds.remove((Object)ResourceUtil
                            .getReversedResourceId(resourceId));
        } else {
            this.toTilesResourceIds.add(resourceId);
        }

        Collections.sort(this.toTilesResourceIds);

    }

    /**
     * 
     * 牌選択エリア{@link ImageView}リストを設定します。
     * 
     */
    public void setFromTilesImageViews() {

        this.setImageViews(this.fromTilesImageViews, this.fromTilesResourceIds);
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
    public void clearToTilesResources() {
        this.cleanImageViews(this.toTilesImageViews);
        this.toTilesResourceIds.clear();
    }

    /**
     * 
     * 選択後牌エリアリソースIDリストを返却します。
     * 
     * @return 選択後牌エリアリソースIDリスト
     */
    public List<Integer> getToTilesResourceIds() {
        return this.toTilesResourceIds;
    }

    /**
     * 
     * 選択後牌エリア{@link ImageView}リストを返却します。
     * 
     * @return 選択後牌エリア{@link ImageView}リスト
     */
    public List<ImageView> getToTilesImageViews() {
        return this.toTilesImageViews;
    }

    /**
     * 
     * 選択後牌が規定されたサイズかを判定します。
     * 
     * @return 判定結果
     */
    public boolean isSpecifiedSize() {
        return this.toTilesResourceIds.size() == TO_TILES_AREA_IAMGE_RESOURCE_SIZE;
    }

}
