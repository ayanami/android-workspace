/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.entity.adapter;

import java.util.List;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.common.AbstractEntityAdapter;
import jp.co.headwaters.jacpot.mahjong.entity.dto.E001StatusDto;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <p>
 * ステータス<code>Adapter</code>クラスです。
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
 */
public class E001StatusAdapter extends AbstractEntityAdapter<E001StatusDto> {

    /**
     * コンストラクタ。
     * 
     * @param layoutInflater {@link LayoutInflater}
     * @param dataList データリスト
     */
    public E001StatusAdapter(LayoutInflater layoutInflater, List<E001StatusDto> dataList) {
        super(layoutInflater, dataList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        // ---------------------------------------------
        // (1) Viewの取得
        // ---------------------------------------------
        View v = convertView;
        if (v == null) {
            v = super.layoutInflater.inflate(R.layout.ac_data_access_list, null);
        }
        TextView header = (TextView)v.findViewById(R.id.textViewDataAccessListHeader);
        TextView contents = (TextView)v.findViewById(R.id.textViewDataAccessListContents);
        TextView desc = (TextView)v.findViewById(R.id.textViewDataAccessListDesc);
        // ---------------------------------------------
        // (2) データの設定
        // ---------------------------------------------
        E001StatusDto dto = (E001StatusDto)getItem(position);
        if (dto != null) {
            header.setText(dto.header);
            contents.setText(dto.contents);
            desc.setText(dto.desc);
        }
        return v;
    }
}
