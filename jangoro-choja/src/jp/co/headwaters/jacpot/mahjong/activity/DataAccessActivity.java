/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.activity;

import java.util.List;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.constant.CommonConst;
import jp.co.headwaters.jacpot.mahjong.entity.E001StatusEntity;
import jp.co.headwaters.jacpot.mahjong.entity.adapter.E001StatusAdapter;
import jp.co.headwaters.jacpot.mahjong.entity.dto.E001StatusDto;
import jp.co.headwaters.jacpot.mahjong.entity.service.E001StatusService;
import jp.co.headwaters.jacpot.mahjong.message.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * <p>
 * データアクセス画面用{@link Activity}クラスです。
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
public class DataAccessActivity extends Activity {

    /**
     * +@id/btnDataAccessGotoMenuの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener gotoMenuClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {

            startActivity(new Intent(DataAccessActivity.this, MenuActivity.class));
            finish();
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_data_access);
        // ---------------------------------------------
        // (2) データの取得
        // ---------------------------------------------
        E001StatusService service = new E001StatusService(this);
        service.open();
        E001StatusEntity entity = service.find();
        service.close();
        // ---------------------------------------------
        // (3) データの設定
        // ---------------------------------------------
        List<E001StatusDto> dataList = service.toDisplay(entity);
        E001StatusAdapter adapter = new E001StatusAdapter(getLayoutInflater(), dataList);
        ((ListView)findViewById(R.id.listViewDataAccess)).setAdapter(adapter);
        // ---------------------------------------------
        // (4) メニューへボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnDataAccessGotoMenu))
                        .setOnClickListener(gotoMenuClickListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 戻るボタン押下時の処理
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // ダイアログの設定
            Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(CommonConst.MENU_TITLE_FINISH);
            builder.setMessage(Message.I_MSG_FINISH);

            // 「はい」が押下された場合の処理
            builder.setPositiveButton(CommonConst.TEXT_YES, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // 「いいえ」が押下された場合の処理
            builder.setNegativeButton(CommonConst.TEXT_NO, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 何もしない。
                }
            });

            builder.show();
            return true;
        }

        return false;
    }

}
