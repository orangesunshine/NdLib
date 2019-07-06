package com.orange.ndlib.activity.base;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.constance.IFinalConst;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.pull.callback.IPullNetCallback;
import com.orange.lib.pull.pagestatus.IPullPageStatus;
import com.orange.lib.pull.pagestatus.LoadingDialogPullPageStatus;
import com.orange.lib.pull.request.IPageNetRequest;
import com.orange.lib.utils.NetUtils;
import com.orange.ndlib.R;
import com.orange.ndlib.response.PullDemoData;
import com.orange.thirdparty.retrofit.api.pull.RetrofitPullUrlApi;

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
        IPullPageStatus pullPageStatus = new LoadingDialogPullPageStatus(mLoading, mHolder);
        RecyclerView recyclerView = mHolder.getView(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        NetUtils.swipePullAdapterNetData(mHolder, android.R.layout.activity_list_item, new IPageNetRequest<PullDemoData>() {
            @Override
            public INetCancel request(int pageIndex, Type type, IPullNetCallback<PullDemoData> callback) {
                HashMap<String, String> params = new HashMap<>();
                params.put("count", "30");
                params.put("pageIndex", String.valueOf(pageIndex));
                return RetrofitPullUrlApi.getInstance().postPull(IFinalConst.sBaseUrl + "/ifc/pull", params, type, callback);
            }
        }, (IConvertRecyclerView<String>) (holder, item, selected) -> {
            holder.setImageResource(android.R.id.icon, R.drawable.ic_launcher_background);
            holder.setText(android.R.id.text1, item);
        });

    }
}
