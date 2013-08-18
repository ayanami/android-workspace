/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.mahjong.constant.MahjongConst;

/**
 * <p>
 * 手牌生成用ユーティリティクラスです。
 * </p>
 * 
 * 作成日：2013/07/27<br>
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
 * <td>2013/07/27</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class HandCreateUtil {

    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected HandCreateUtil() {

    }

    /**
     * 国士無双のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getThirteenOrphansResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.EAST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOUTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.WEST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.NORTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.WHITE));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.GREEN));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.RED));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }

    /**
     * 九蓮宝燈のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getNineTreasuresResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU4));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU5));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU6));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU7));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU8));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU9));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }

    /**
     * 四暗刻のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getFourConcealedTriplesResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.EAST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.EAST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.EAST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOUTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOUTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOUTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.WEST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.WEST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.WEST));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.NORTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.NORTH));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.NORTH));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }

    /**
     * 三色同順のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getThreeColorRunsResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU3));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }
    
    /**
     * 三色同刻のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getThreeColorTriplesResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.PIN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU1));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }
    
    /**
     * 純全帯のリソースIDを含むリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getPureOutsideHandResourceIds(int size) {

        List<Integer> resourceIds = new ArrayList<Integer>() {

            {
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN1));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN2));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN3));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN7));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN8));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.MAN9));
                add(ResourceUtil.idxToResourceId.get(MahjongConst.SOU9));
            }
        };

        return ResourceUtil.getSpecifiedResourceIds(resourceIds, size);
    }
}
