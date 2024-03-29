package com.orange.ndlib.activity.demo.swip;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.common.convert.IPullConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.contact.presenter.PullPresenter;
import com.orange.lib.mvp.contact.view.PullActivity;
import com.orange.lib.mvp.model.net.callback.pull.IPullNetCallback;
import com.orange.lib.mvp.view.page.pull.LoadingDialogPullPage;
import com.orange.lib.mvp.model.net.request.IPageNetRequest;
import com.orange.lib.utils.net.NetUtils;
import com.orange.ndlib.R;
import com.orange.ndlib.response.PullDemoData;
import com.orange.thirdparty.retrofit.api.pull.RetrofitPullApi;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SwipePullPageDemoActivity extends PullActivity {
    private INetCancel mNetCancel;

    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_pull_demo;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        LoadingDialogPullPage pageStatus = new LoadingDialogPullPage(mLoading, mHolder);
        RecyclerView recyclerView = mHolder.getView(R.id.id_recyclerview_orange);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_pull:
                        mNetCancel = NetUtils.swipePullPageAdapterNetData(new IPageNetRequest<PullDemoData>() {
                            @Override
                            public INetCancel request(int pageIndex, Type type, IPullNetCallback<PullDemoData> callback) {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("count", "30");
                                params.put("pageIndex", String.valueOf(pageIndex));
                                return RetrofitPullApi.getInstance().post(IConst.sBaseUrl + "/ifc/pull", params, type, callback);
                            }
                        }, pageStatus, mHolder.getView(R.id.id_refreshlayout_orange), mHolder.getView(R.id.id_recyclerview_orange), mHolder.getView(R.id.id_empty_orange), android.R.layout.activity_list_item, (IConvertRecyclerView<String>) (holder, item, selected) -> {
                            holder.setImageResource(android.R.id.icon, R.drawable.ic_launcher_background);
                            holder.setText(android.R.id.text1, item);
                        });
                        break;
                    case R.id.btn_pull_convert:
                        mNetCancel = NetUtils.swipePullPageAdapterNetData(new IPageNetRequest<PullDemoData>() {
                            @Override
                            public INetCancel request(int pageIndex, Type type, IPullNetCallback<PullDemoData> callback) {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("count", "30");
                                params.put("pageIndex", String.valueOf(pageIndex));
                                return RetrofitPullApi.getInstance().post(IConst.sBaseUrl + "/ifc/pull", params, type, callback);
                            }
                        }, pageStatus, mHolder.getView(R.id.id_refreshlayout_orange), mHolder.getView(R.id.id_recyclerview_orange), new IPullConvert<String>() {
                            @Override
                            public void convert(PullData<String> pullResponse) {
                                Toast.makeText(mActivity, "pullResponse", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.btn_pull_holder:
                        mNetCancel = NetUtils.swipePullPageAdapterNetData(new IPageNetRequest<PullDemoData>() {
                            @Override
                            public INetCancel request(int pageIndex, Type type, IPullNetCallback<PullDemoData> callback) {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("count", "30");
                                params.put("pageIndex", String.valueOf(pageIndex));
                                return RetrofitPullApi.getInstance().post(IConst.sBaseUrl + "/ifc/pull", params, type, callback);
                            }
                        }, pageStatus, mHolder, android.R.layout.activity_list_item, (IConvertRecyclerView<String>) (holder, item, selected) -> {
                            holder.setImageResource(android.R.id.icon, R.drawable.ic_launcher_background);
                            holder.setText(android.R.id.text1, item);
                        });
                        break;
                    case R.id.btn_pull_holder_convert:
                        mNetCancel = NetUtils.swipePullPageAdapterNetData(new IPageNetRequest<PullDemoData>() {
                            @Override
                            public INetCancel request(int pageIndex, Type type, IPullNetCallback<PullDemoData> callback) {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("count", "30");
                                params.put("pageIndex", String.valueOf(pageIndex));
                                return RetrofitPullApi.getInstance().post(IConst.sBaseUrl + "/ifc/pull", params, type, callback);
                            }
                        }, pageStatus, mHolder, new IPullConvert<String>() {
                            @Override
                            public void convert(PullData<String> pullResponse) {
                                Toast.makeText(mActivity, "pullResponse", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        }, R.id.btn_pull, R.id.btn_pull_convert, R.id.btn_pull_holder, R.id.btn_pull_holder_convert);
    }

    @Override
    protected PullPresenter getPresenter() {
        return null;
    }

    /**
     * onDestory生命周期调用
     */
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (null != mNetCancel)
            mNetCancel.cancel();
    }

    @Override
    public boolean isRefreshing() {
        return false;
    }

    @Override
    public boolean isLoadmore() {
        return false;
    }
}
