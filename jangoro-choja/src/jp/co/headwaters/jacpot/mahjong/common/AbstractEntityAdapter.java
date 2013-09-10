/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.common;

import java.util.List;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;


/**
 * <p>
 * {@link BaseAdapter}クラスをラップした<code>Entity</code><code>Adapter</code>抽象クラスです。
 * </p>
 * 
 * 作成日：2013/09/04<br>
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
 * <td>2013/09/04</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 * @param <D> 画面表示用<code>Dto</code>クラス。
 */
public abstract class AbstractEntityAdapter<D> extends BaseAdapter {
    
    /** {@link LayoutInflater} */
    protected LayoutInflater layoutInflater;
    
    /** データリスト */
    private List<D> dataList;

    /**
     * コンストラクタです。
     * @param layoutInflater {@link LayoutInflater}
     * @param dataList データリスト
     */
    public AbstractEntityAdapter(LayoutInflater layoutInflater, List<D> dataList) {
        this.layoutInflater = layoutInflater;
        this.dataList = dataList;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getCount() {
        return this.dataList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getItem(int position) {
        return this.dataList.get(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
}
