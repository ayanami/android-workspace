/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.activity;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.constant.CommonConst;
import jp.co.headwaters.jacpot.mahjong.message.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/**
 * <p>
 * メニュー画面用<code>Activity</code>クラスです。
 * </p>
 * 
 * 作成日：2013/09/03<br>
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
 * <td>2013/09/03</td>
 * <td>－</td>
 * <td>HWS 鈴木</td>
 * <td>新規作成</td>
 * </tr>
 * </table>
 * 
 * @author HWS 鈴木
 */
public class MenuActivity extends Activity {

    /**
     * +@id/btnMenuStartの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener startClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MenuActivity.this, ReadyHandsMakeActivity.class));
            finish();
        }
    };

    /**
     * +@id/btnMenuDataAccessの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener dataAccessClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MenuActivity.this, DataAccessActivity.class));
            finish();
        }
    };
    
    /**
     * 
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ---------------------------------------------
        // (1) レイアウトの設定
        // ---------------------------------------------
        setContentView(R.layout.ac_menu);
        // ---------------------------------------------
        // (2) スタートボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMenuStart)).setOnClickListener(startClickListener);
        // ---------------------------------------------
        // (3) ステータスを確認ボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMenuDataAccess)).setOnClickListener(dataAccessClickListener);
    };

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
