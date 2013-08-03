/**
 * 
 */
package jp.co.headwaters.jacpot.function.mahjong.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.R;
import android.util.SparseIntArray;

/**
 * <p>
 * 麻雀牌イメージリソースユーティリティクラスです。
 * </p>
 * 
 * 作成日：2013/07/22<br>
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
 * <td>2013/07/22</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class ImageResourceUtil {

    /** 利用回数上限 */
    private static final int USE_CNT_LIMIT = 4;

    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected ImageResourceUtil() {

    }

    /**
     * ランダムに取得したリソースIDを返却します。
     * 
     * @return リソースID
     */
    public static int getRandomResourceId() {
        return getRandomResourceIds(1).get(0);
    }

    /**
     * ランダムに取得したリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    public static List<Integer> getRandomResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>();

        for (int i = 0; i < size; i++) {

            // 無限ループ
            while (true) {

                // ランダムなインデックスを生成
                int j = (int)(Math.floor(Math.random() * ResourceUtil.tilesStatus.size()));

                int resourceId = getAvailableResourceId(j);

                if (resourceId != -1) {

                    resourceIds.add(resourceId);

                    // 無限ループを抜ける
                    break;
                }
            }
        }

        return resourceIds;
    }

    /**
     * 指定したリソースIDリストを含むリソースIDリストを返却します。
     * 
     * @param specifiedResourceIds 指定リソースIDリスト
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    public static List<Integer>
                    getSpecifiedResourceIds(List<Integer> specifiedResourceIds, int size) {

        List<Integer> resourceIds = new ArrayList<Integer>();

        for (Integer resourceId : specifiedResourceIds) {
            for (int i = 0; i < ResourceUtil.tilesStatus.size(); i++) {
                SparseIntArray useCnts = ResourceUtil.tilesStatus.get(i);
                if (resourceId == useCnts.keyAt(0)) {
                    getAvailableResourceId(i);
                    resourceIds.add(resourceId);
                }
            }
        }

        int diff = size - specifiedResourceIds.size();
        if (diff > 0) {
            resourceIds.addAll(getRandomResourceIds(diff));
        }

        return resourceIds;
    }

    /**
     * 国士無双のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getThirteenOrphansResourceIds(int size) {

        List<Integer> thirteenOrphans = new ArrayList<Integer>() {

            {
                add(R.drawable.a_ms1_1);
                add(R.drawable.a_ms9_1);
                add(R.drawable.b_ps1_1);
                add(R.drawable.b_ps9_1);
                add(R.drawable.c_ss1_1);
                add(R.drawable.c_ss9_1);
                add(R.drawable.d_ji_e_1);
                add(R.drawable.e_ji_s_1);
                add(R.drawable.f_ji_w_1);
                add(R.drawable.g_ji_n_1);
                add(R.drawable.h_no_1);
                add(R.drawable.i_ji_h_1);
                add(R.drawable.j_ji_c_1);
            }
        };

        return getSpecifiedResourceIds(thirteenOrphans, size);
    }

    /**
     * 利用可能なリソースIDを返却します。
     * 
     * @param idx 麻雀牌ステータスハッシュの添え字
     * @return リソースID
     */
    private static Integer getAvailableResourceId(int idx) {

        // 生成したインデックスで利用回数ハッシュを取得
        SparseIntArray useCnts = ResourceUtil.tilesStatus.get(idx);

        int resourceId = useCnts.keyAt(0);
        int useCnt = useCnts.get(resourceId);

        // 利用回数が上限に達していないかを判定
        if (useCnt < USE_CNT_LIMIT) {

            // 利用回数をインクリメント
            useCnts.put(resourceId, useCnt + 1);
            ResourceUtil.tilesStatus.put(idx, useCnts);

            return resourceId;
        }

        return -1;
    }

    /**
     * イメージリソースが反転前かを判定します。
     * 
     * @param resourceId リソースID
     * @return 判定結果
     */
    public static boolean isReverse(int resourceId) {

        if (ResourceUtil.grayscaleToNormal.indexOfKey(resourceId) >= 0) {
            return true;
        }

        return false;
    }

    /**
     * 反転後のリソースIDを返却します。
     * 
     * @param resourceId 反転前のリソースID
     * @return 反転後のリソースID
     */
    public static int getReversedResourceId(int resourceId) {

        if (isReverse(resourceId)) {
            return ResourceUtil.grayscaleToNormal.get(resourceId);
        }

        return ResourceUtil.normalToGrayscale.get(resourceId);
    }

}
