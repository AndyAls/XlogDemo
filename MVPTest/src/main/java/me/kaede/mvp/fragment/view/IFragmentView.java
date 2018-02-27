package me.kaede.mvp.fragment.view;

import android.app.Activity;
public interface IFragmentView {
	public Activity getActivity();
	public void onItemClick(int position);
}
