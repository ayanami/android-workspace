/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.constant.MahjongConst;
import jp.co.headwaters.jacpot.mahjong.dto.HandsStatusDto;
import android.content.res.TypedArray;
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

    /** 局リスト{value:{場、局、風}} */
    public static List<Object[]> rounds;

    /** 麻雀牌ハッシュ {key:リソースID(麻雀牌画像), value:牌インデックス} */
    public static SparseIntArray resourceIdToIdx;

    /** 麻雀牌リソースIDリスト */
    public static List<Integer> idxToResourceId;

    /** 麻雀牌リソースIDリスト(赤含む) */
    public static List<Integer> idxToResourceIdIncRed;

    /** 利用回数ハッシュ {key:リソースID(麻雀牌画像), value:利用回数} */
    public static SparseIntArray resourceIdIncRedToUseCnt;

    /** リソースIDハッシュ(ノーマル->グレースケール){key:ノーマルリソースID, value:グレースケールリソースID} */
    public static SparseIntArray normalToGrayscale;

    /** リソースIDハッシュ(グレースケール->ノーマル){key:グレースケールリソースID, value:ノーマルリソースID} */
    public static SparseIntArray grayscaleToNormal;

    /** リソースIDハッシュ(5->赤5){key:リソースID(5), value:リソースID(赤5)} */
    public static SparseIntArray fiveToRedFive;

    /** あがり牌リソースIDリスト */
    public static List<Integer> winningResourceIds;

    /** 場配列 */
    private static final String[] ROUNDS = new String[]{"東", "南"};

    /** 風配列 */
    private static final String[] WINDS = new String[]{"東", "南", "西", "北"};

    /** 利用回数デフォルト */
    private static final int USE_CNT_DEFAULT = 0;

    /** 利用回数デフォルト(5) */
    private static final int USE_CNT_DEFAULT_FOR_5 = 1;

    /** 利用回数デフォルト(赤5) */
    private static final int USE_CNT_DEFAULT_FOR_RED_5 = 3;

    /** 利用回数上限 */
    private static final int USE_CNT_LIMIT = 4;

    /** リソースIDリスト(5) */
    private static final List<Integer> FIVE_IDS = new ArrayList<Integer>();

    /** リソースIDリスト(赤5) */
    private static final List<Integer> RED_FIVE_IDS = new ArrayList<Integer>();

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

        rounds = new ArrayList<Object[]>();
        resourceIdToIdx = new SparseIntArray();
        idxToResourceId = new ArrayList<Integer>();
        idxToResourceIdIncRed = new ArrayList<Integer>();
        resourceIdIncRedToUseCnt = new SparseIntArray();
        normalToGrayscale = new SparseIntArray();
        grayscaleToNormal = new SparseIntArray();
        fiveToRedFive = new SparseIntArray();
        winningResourceIds = new ArrayList<Integer>();

        // ---------------------------------------------
        // (1) 局リストの生成
        // ---------------------------------------------
        createRounds();

        int tilesIdx = 0;
        for (int i = 0; i < resourceIds.length(); i++) {

            int resourceId = resourceIds.getResourceId(i, -1);
            int grayscaleId = grayscaleIds.getResourceId(i, -1);

            // ---------------------------------------------
            // (2) 麻雀牌ハッシュの生成
            // ---------------------------------------------
            if (RED_FIVE_IDS.contains(resourceId)) {
                tilesIdx--;
            }
            resourceIdToIdx.put(resourceId, tilesIdx);
            tilesIdx++;
            // ---------------------------------------------
            // (3) 麻雀牌リソースIDリストの生成
            // ---------------------------------------------
            if (!RED_FIVE_IDS.contains(resourceId)) {
                idxToResourceId.add(resourceId);
            }
            // ---------------------------------------------
            // (4) 麻雀牌リソースIDリスト(赤含む)の生成
            // ---------------------------------------------
            idxToResourceIdIncRed.add(resourceId);
            // ---------------------------------------------
            // (5) 麻雀牌ステータスハッシュの生成
            // ---------------------------------------------
            resourceIdIncRedToUseCnt.put(resourceId, USE_CNT_DEFAULT);
            // ---------------------------------------------
            // (6) リソースIDハッシュ(ノーマル->グレースケール, グレースケール->ノーマル)の生成
            // ---------------------------------------------
            normalToGrayscale.put(resourceId, grayscaleId);
            grayscaleToNormal.put(grayscaleId, resourceId);
        }
        // ---------------------------------------------
        // (7) リソースIDハッシュ(5->赤5)の生成
        // ---------------------------------------------
        for (int i = 0; i < FIVE_IDS.size(); i++) {
            fiveToRedFive.put(FIVE_IDS.get(i), RED_FIVE_IDS.get(i));
        }
        // ---------------------------------------------
        // (8) 麻雀牌ステータスハッシュの初期化
        // ---------------------------------------------
        initResourceIdIncRedToUseCnt();
    }

    /**
     * 
     * 利用回数ハッシュを初期化します。
     * 
     */
    public static void initResourceIdIncRedToUseCnt() {

        for (int i = 0; i < resourceIdIncRedToUseCnt.size(); i++) {

            int resourceId = resourceIdIncRedToUseCnt.keyAt(i);

            resourceIdIncRedToUseCnt.put(resourceId, USE_CNT_DEFAULT);

            if (FIVE_IDS.contains(resourceId)) {
                resourceIdIncRedToUseCnt.put(resourceId, USE_CNT_DEFAULT_FOR_5);
            }

            if (RED_FIVE_IDS.contains(resourceId)) {
                resourceIdIncRedToUseCnt.put(resourceId, USE_CNT_DEFAULT_FOR_RED_5);
            }
        }
    }

    /**
     * 局リストを生成します。
     * 
     */
    private static void createRounds() {

        int wind = (int)(Math.floor(Math.random() * WINDS.length));

        for (int i = 0; i < ROUNDS.length; i++) {
            for (int j = 0; j < WINDS.length; j++) {

                if (wind >= WINDS.length) {
                    wind = 0;
                }
                wind++;

                rounds.add(new Object[]{ROUNDS[i], j + 1, WINDS[wind - 1]});
            }
        }
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
                int randomIdx = (int)(Math.floor(Math.random() * resourceIdIncRedToUseCnt.size()));

                int resourceId = getAvailableResourceId(randomIdx);

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
            getAvailableResourceId(idxToResourceIdIncRed.lastIndexOf(resourceId));
            resourceIds.add(resourceId);
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
                add(idxToResourceId.get(MahjongConst.MAN1));
                add(idxToResourceId.get(MahjongConst.MAN9));
                add(idxToResourceId.get(MahjongConst.PIN1));
                add(idxToResourceId.get(MahjongConst.PIN9));
                add(idxToResourceId.get(MahjongConst.SOU1));
                add(idxToResourceId.get(MahjongConst.SOU9));
                add(idxToResourceId.get(MahjongConst.EAST));
                add(idxToResourceId.get(MahjongConst.SOUTH));
                add(idxToResourceId.get(MahjongConst.WEST));
                add(idxToResourceId.get(MahjongConst.NORTH));
                add(idxToResourceId.get(MahjongConst.WHITE));
                add(idxToResourceId.get(MahjongConst.GREEN));
                add(idxToResourceId.get(MahjongConst.RED));
            }
        };

        return getSpecifiedResourceIds(thirteenOrphans, size);
    }

    /**
     * 九蓮宝燈のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getNineTreasuresResourceIds(int size) {

        List<Integer> nineTreasures = new ArrayList<Integer>() {

            {
                add(idxToResourceId.get(MahjongConst.SOU1));
                add(idxToResourceId.get(MahjongConst.SOU1));
                add(idxToResourceId.get(MahjongConst.SOU1));
                add(idxToResourceId.get(MahjongConst.SOU2));
                add(idxToResourceId.get(MahjongConst.SOU3));
                add(idxToResourceId.get(MahjongConst.SOU4));
                add(idxToResourceId.get(MahjongConst.SOU5));
                add(idxToResourceId.get(MahjongConst.SOU6));
                add(idxToResourceId.get(MahjongConst.SOU7));
                add(idxToResourceId.get(MahjongConst.SOU8));
                add(idxToResourceId.get(MahjongConst.SOU9));
                add(idxToResourceId.get(MahjongConst.SOU9));
                add(idxToResourceId.get(MahjongConst.SOU9));
            }
        };

        return getSpecifiedResourceIds(nineTreasures, size);
    }

    /**
     * 四暗刻のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getFourConcealedTriplesResourceIds(int size) {

        List<Integer> nineTreasures = new ArrayList<Integer>() {

            {
                add(idxToResourceId.get(MahjongConst.EAST));
                add(idxToResourceId.get(MahjongConst.EAST));
                add(idxToResourceId.get(MahjongConst.EAST));
                add(idxToResourceId.get(MahjongConst.SOUTH));
                add(idxToResourceId.get(MahjongConst.SOUTH));
                add(idxToResourceId.get(MahjongConst.SOUTH));
                add(idxToResourceId.get(MahjongConst.WEST));
                add(idxToResourceId.get(MahjongConst.WEST));
                add(idxToResourceId.get(MahjongConst.WEST));
                add(idxToResourceId.get(MahjongConst.NORTH));
                add(idxToResourceId.get(MahjongConst.NORTH));
                add(idxToResourceId.get(MahjongConst.NORTH));
            }
        };

        return getSpecifiedResourceIds(nineTreasures, size);
    }
    
    /**
     * 利用可能なリソースIDを返却します。
     * 
     * @param idx 利用回数ハッシュの添え字
     * @return リソースID
     */
    private static Integer getAvailableResourceId(int idx) {

        int resourceId = resourceIdIncRedToUseCnt.keyAt(idx);
        int useCnt = resourceIdIncRedToUseCnt.get(resourceId);

        // 利用回数が上限に達していないかを判定
        if (useCnt < USE_CNT_LIMIT) {

            // 利用回数をインクリメント
            resourceIdIncRedToUseCnt.put(resourceId, useCnt + 1);

            return resourceId;
        }

        return -1;
    }

    /**
     * 利用可能なリソースIDリストを返却します。
     * 
     * @param idx 麻雀牌ハッシュの添え字
     * @return 利用可能なリソースIDリスト
     */
    public static List<Integer> getAvailableResourceIds(int idx) {

        List<Integer> resourceIds = new ArrayList<Integer>();

        int resourceId = idxToResourceId.get(idx);

        // 利用可能かを判定して追加
        if (isAvailable(resourceId)) {
            resourceIds.add(resourceId);
        }

        // 5の場合は、赤5が利用可能かを判定して追加
        if (fiveToRedFive.indexOfKey(resourceId) >= 0) {
            int redFiveResourceId = fiveToRedFive.get(resourceId);
            if (isAvailable(redFiveResourceId)) {
                resourceIds.add(redFiveResourceId);
            }
        }

        return resourceIds;
    }

    /**
     * 
     * イメージリソースが利用可能かを判定します。
     * 
     * @param resourceId リソースID
     * @return 判定結果
     */
    public static boolean isAvailable(int resourceId) {

        if (resourceIdIncRedToUseCnt.get(resourceId) < USE_CNT_LIMIT) {
            return true;
        }
        return false;
    }

    /**
     * イメージリソースが反転前かを判定します。
     * 
     * @param resourceId リソースID
     * @return 判定結果
     */
    public static boolean isReverse(int resourceId) {

        if (grayscaleToNormal.indexOfKey(resourceId) >= 0) {
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
            return grayscaleToNormal.get(resourceId);
        }

        return normalToGrayscale.get(resourceId);
    }

    /**
     * 
     * ドラを設定します。
     * 
     * @param idx ドラ表示牌添え字
     * @param dto {@link HandsStatusDto}
     */
    public static void setDragon(int idx, HandsStatusDto dto) {
        
        int dragon = idx;
        switch (idx) {
            case MahjongConst.MAN9:
                dragon = MahjongConst.MAN1;
                break;
            case MahjongConst.PIN9:
                dragon = MahjongConst.PIN1;
                break;
            case MahjongConst.SOU9:
                dragon = MahjongConst.SOU1;
                break;
            case MahjongConst.NORTH:
                dragon = MahjongConst.EAST;
                break;
            case MahjongConst.RED:
                dragon = MahjongConst.WHITE;
                break;
            default:
                dragon = idx + 1;
                break;
        }
        dto.dragon = dragon;
    }

    /**
     * 
     * 現在の局情報を設定します。
     * 
     * @param idx 局リストの添え字
     * @param dto {@link HandsStatusDto}
     */
    public static void setCurrentRoundInfo(int idx, HandsStatusDto dto) {

        Object[] round = rounds.get(idx);

        for (int i = 0; i < ROUNDS.length; i++) {
            if (ROUNDS[i].equals(round[0])) {
                dto.round = i;
                break;
            }
        }

        for (int i = 0; i < WINDS.length; i++) {
            if (WINDS[i].equals(round[2])) {
                dto.wind = i;
                if (i == 0) {
                    dto.isDealer = true;
                } else {
                    dto.isDealer = false;
                }
                break;
            }
        }
    }

    /**
     * 
     * ドラ数を設定します。
     * 
     * @param resourceIds リソースIDリスト
     * @param dto {@link HandsStatusDto}
     */
    public static void setDragonCnt(List<Integer> resourceIds, HandsStatusDto dto) {

        int cnt = 0;

        for (Integer resourceId : resourceIds) {

            if (RED_FIVE_IDS.contains(resourceId)) {
                cnt++;
            }

            if (dto.dragon == resourceIdToIdx.get(resourceId)) {
                cnt++;
            }
        }

        dto.dragonCnt = cnt;
    }

}
