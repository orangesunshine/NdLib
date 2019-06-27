package com.orange.ndlib.activity.base;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.component.recyclerview.RecyclerViewHolder;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;
import com.orange.lib.utils.NetUtils;
import com.orange.ndlib.R;
import com.orange.ndlib.response.PullDemoData;
import com.orange.thirdparty.retrofit.RetrofitUrlApi;

import java.lang.reflect.Type;
import java.util.HashMap;

public class BaseActivitySwipePullDemo extends BaseActivity {
    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_base_pull_demo;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        mActbar.setTitle(TAG);
        NetUtils.swipePullAdapterNetData(mHolder, android.R.layout.activity_list_item, new IPageNetRequest<PullDemoData>() {
            @Override
            public void request(int pageIndex, Type type, INetCallback<PullDemoData> callback) {
                HashMap<String, String> params = new HashMap<>();
                params.put("count", "20");
                params.put("pageIndex", "2");
                RetrofitUrlApi.getInstance().postPull("http://localhost:8080/ifc/pull", params, type, callback);
            }
        }, new IConvertRecyclerView<String>() {
            @Override
            public void convert(RecyclerViewHolder holder, String item, boolean selected) {
                holder.setImageResource(android.R.id.icon, R.drawable.ic_launcher_background);
                holder.setText(android.R.id.text1, item);
            }
        });
    }
}
