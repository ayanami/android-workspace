/**
 * 
 */
package jp.co.headwaters.jacpot.function.mahjong.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.R;
import android.content.res.TypedArray;
import android.util.SparseArray;
import android.util.SparseIntArray;

/**
 * <p>
 * 麻雀牌リソースユーティリティクラスです。
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
public class ResourceUtil {

    /** 利用回数デフォルト */
    private static final int USE_CNT_DEFAULT = 0;

    /** 利用回数デフォルト(5) */
    private static final int USE_CNT_DEFAULT_FOR_5 = 1;

    /** 利用回数デフォルト(赤5) */
    private static final int USE_CNT_DEFAULT_FOR_RED_5 = 3;

    /** リソースIDリスト(5) */
    private static final List<Integer> FIVE_IDS = new ArrayList<Integer>();

    /** リソースIDリスト(赤5) */
    private static final List<Integer> RED_FIVE_IDS = new ArrayList<Integer>();

    /** 麻雀牌ハッシュ {key:リソースID, value:牌インデックス} */
    private static SparseIntArray tiles;

    /** 麻雀牌ステータスハッシュ{key:インデックス, value:利用回数ハッシュ{key:リソースID(麻雀牌画像), value:利用回数}} */
    private static SparseArray<SparseIntArray> tilesStatus;

    /** リソースIDハッシュ(ノーマル->グレースケール){key:ノーマルリソースID, value:グレースケールリソースID} */
    private static SparseIntArray normalToGrayscale;

    /** リソースIDハッシュ(グレースケール->ノーマル){key:グレースケールリソースID, value:ノーマルリソースID} */
    private static SparseIntArray grayscaleToNormal;

    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected ResourceUtil() {

    }

    /**
     * リソースIDリストを生成します。
     */
    static {

        FIVE_IDS.add(R.drawable.a_ms5_1);
        FIVE_IDS.add(R.drawable.b_ps5_1);
        FIVE_IDS.add(R.drawable.c_ss5_1);

        RED_FIVE_IDS.add(R.drawable.a_ms5_1_red);
        RED_FIVE_IDS.add(R.drawable.b_ps5_1_red);
        RED_FIVE_IDS.add(R.drawable.c_ss5_1_red);
    }

    /**
     * リソースを生成します。
     * 
     * @param resourceIds リソースID(麻雀牌画像)
     * @param grayscaleIds リソースID(グレースケール麻雀牌画像)
     */
    public static void createResources(TypedArray resourceIds, TypedArray grayscaleIds) {

        tiles = new SparseIntArray();
        tilesStatus = new SparseArray<SparseIntArray>();
        normalToGrayscale = new SparseIntArray();
        grayscaleToNormal = new SparseIntArray();

        int tilesIdx = 0;
        for (int i = 0; i < resourceIds.length(); i++) {

            int resourceId = resourceIds.getResourceId(i, -1);
            int grayscaleId = grayscaleIds.getResourceId(i, -1);

            // ---------------------------------------------
            // (1) 麻雀牌ハッシュの生成
            // ---------------------------------------------
            if (RED_FIVE_IDS.contains(resourceId)) {
                tilesIdx--;
            }
            tiles.put(resourceId, tilesIdx);
            tilesIdx++;

            // ---------------------------------------------
            // (2) 麻雀牌ステータスハッシュの生成
            // ---------------------------------------------
            SparseIntArray useCnts = new SparseIntArray();
            useCnts.put(resourceId, USE_CNT_DEFAULT);
            tilesStatus.put(i, useCnts);

            // ---------------------------------------------
            // (3) リソースIDハッシュの生成
            // ---------------------------------------------
            normalToGrayscale.put(resourceId, grayscaleId);
            grayscaleToNormal.put(grayscaleId, resourceId);
        }
        // ---------------------------------------------
        // (4) 麻雀牌ステータスハッシュの初期化
        // ---------------------------------------------
        initTilesStatus();
    }

    /**
     * 麻雀牌ハッシュを返却します。
     * 
     * @return 麻雀牌ハッシュ
     */
    public static SparseIntArray getTiles() {
        return tiles;
    }

    /**
     * 麻雀牌ステータスハッシュを返却します。
     * 
     * @return 麻雀牌ステータスハッシュ
     */
    public static SparseArray<SparseIntArray> getTilesStatus() {
        return tilesStatus;
    }

    /**
     * リソースIDハッシュ(ノーマル->グレースケール)を返却します。
     * 
     * @return リソースIDハッシュ(ノーマル->グレースケール)
     */
    public static SparseIntArray getNormalToGrayscale() {
        return normalToGrayscale;
    }

    /**
     * リソースIDハッシュ(グレースケール->ノーマル)を返却します。
     * 
     * @return リソースIDハッシュ(グレースケール->ノーマル)
     */
    public static SparseIntArray getGrayscaleToNormal() {
        return grayscaleToNormal;
    }

    /**
     * 
     * 麻雀牌ハッシュの利用回数を初期化します。
     * 
     */
    public static void initTilesStatus() {

        for (int i = 0; i < tilesStatus.size(); i++) {

            SparseIntArray useCnts = tilesStatus.get(i);

            int resourceId = useCnts.keyAt(0);

            useCnts.put(resourceId, USE_CNT_DEFAULT);

            if (FIVE_IDS.contains(resourceId)) {
                useCnts.put(resourceId, USE_CNT_DEFAULT_FOR_5);
            }

            if (RED_FIVE_IDS.contains(resourceId)) {
                useCnts.put(resourceId, USE_CNT_DEFAULT_FOR_RED_5);
            }

            tilesStatus.put(i, useCnts);
        }
    }

}
