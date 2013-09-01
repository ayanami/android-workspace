/**
 * 
 */
package jp.co.headwaters.jacpot.mahjong.activity;

import jp.co.headwaters.jacpot.R;
import jp.co.headwaters.jacpot.mahjong.entity.E001StatusEntity;
import jp.co.headwaters.jacpot.mahjong.entity.service.E001StatusService;
import jp.co.headwaters.jacpot.mahjong.util.ResourceUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * <p>
 * トップ画面用<code>Activity</code>クラスです。
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
public class MainActivity extends Activity {

    /**
     * +@id/btnMainStartの{@link OnClickListener}匿名クラスです。
     */
    private OnClickListener startClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, MakeReadyHandsActivity.class));
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
        setContentView(R.layout.ac_main);
        // ---------------------------------------------
        // (2) スタートボタン設定
        // ---------------------------------------------
        ((Button)findViewById(R.id.btnMainStart)).setOnClickListener(startClickListener);
        // ---------------------------------------------
        // (3) リソース設定
        // ---------------------------------------------
        TypedArray resourceIds = getResources().obtainTypedArray(R.array.tiles);
        TypedArray grayscaleIds = getResources().obtainTypedArray(R.array.tiles_grayscale);
        ResourceUtil.createResources(resourceIds, grayscaleIds);
        // ---------------------------------------------
        // (4) DB設定
        // ---------------------------------------------
        E001StatusService service = new E001StatusService(this);
        service.open();
        if (service.getCount() == 0) {
            E001StatusEntity entity = new E001StatusEntity();
            entity.init();
            service.insert(entity);
        }
        service.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuDirection:
                Intent intent = new Intent(this, DirectionActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    };

}
