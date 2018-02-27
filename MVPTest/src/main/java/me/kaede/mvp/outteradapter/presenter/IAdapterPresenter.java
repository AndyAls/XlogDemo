package me.kaede.mvp.outteradapter.presenter;

import android.app.Activity;

public interface IAdapterPresenter {
	public void loadDatas();
	public void loadMoreData(String item);
	public Activity getActivity();
	public void showFooterView(Boolean isShow);
}
