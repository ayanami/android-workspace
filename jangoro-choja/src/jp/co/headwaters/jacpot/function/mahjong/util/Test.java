/**
 * 
 */
package jp.co.headwaters.jacpot.function.mahjong.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.headwaters.jacpot.R;


/**
 * <p>
 * テスト用ユーティリティクラスです。
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
public class Test {
    
    /**
     * ユーティリティクラスのため、コンストラクタをプロテクトします。
     */
    protected Test() {
        
    }

    /**
     * 
     * 任意のリソースIDリストを返却します。
     * 
     * @param size リソースIDリストのサイズ
     * @return リソースIDリスト
     */
    @SuppressWarnings("serial")
    public static List<Integer> getJustAsWellResourceIds(int size) {
        
        List<Integer> especiallyResourceIds = new ArrayList<Integer>() {
            
            {
                add(R.drawable.a_ms1_1);
                add(R.drawable.a_ms2_1);
                add(R.drawable.a_ms3_1);
                add(R.drawable.a_ms1_1);
                add(R.drawable.a_ms2_1);
                add(R.drawable.a_ms3_1);
            }
        };
        
        return ImageResourceUtil.getSpecifiedResourceIds(especiallyResourceIds, size);
        
    }

}
