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
import android.os.Handler;

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
    
    /** {@link Activity}切替インターバル(ミリ秒) */
    private static final long CHANGE_ACTIVITY_INTERVAL = 2000;
    
    /**
     * 一定時間後にメニュー画面に遷移するための{@link Runnable}匿名クラスです。
     */
    private Runnable changeActivity = new Runnable() {
        
        @Override
        public void run() {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
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
        // (2) リソース設定
        // ---------------------------------------------
        TypedArray resourceIds = getResources().obtainTypedArray(R.array.tiles);
        TypedArray grayscaleIds = getResources().obtainTypedArray(R.array.tiles_grayscale);
        ResourceUtil.createResources(resourceIds, grayscaleIds);
        // ---------------------------------------------
        // (3) DB設定
        // ---------------------------------------------
        E001StatusService service = new E001StatusService(this);
        service.open();
        if (service.getCount() == 0) {
            E001StatusEntity entity = new E001StatusEntity();
            entity.init();
            service.insert(entity);
        }
        service.close();
        // ---------------------------------------------
        // (4) 画面遷移
        // ---------------------------------------------
        new Handler().postDelayed(changeActivity, CHANGE_ACTIVITY_INTERVAL);
    }

}
