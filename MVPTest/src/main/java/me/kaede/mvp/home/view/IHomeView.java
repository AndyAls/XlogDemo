package me.kaede.mvp.home.view;

import java.util.List;
public interface IHomeView {
	public void onGetDataList(List<String> datas);
	public void toast(String msg);
}
