package com.vertical.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author Ralken Liao
 * @date 2016-11-18
 */
// tmp
public abstract class BaseFragment extends Fragment {

	protected View findViewById(int res) {
		return getView().findViewById(res);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		if (arguments != null) {
			onHandleArguments(arguments);
		}
	}

	/** Called when this activity receives bundle arguments from other pages. */
	protected void onHandleArguments(final Bundle extras) {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(inflateContentView(), container, false);
		attachAbsInHeritedView(inflater,container,root);
		ButterKnife.bind(this, root);
		onInitializeView();
		onInitializeView(root);
		return root;
	}

	protected void attachAbsInHeritedView(LayoutInflater inflater, ViewGroup container, View root) {

	}


	/** Provide content view for your fragment. */
	protected abstract int inflateContentView();
	
	protected abstract void onInitializeView();

	protected void onInitializeView(View view){

	}

	public boolean isUIActive() {
		return isAdded() && !isDetached() && !isRemoving();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	public void launchScreen(Class<? extends Activity> target, Bundle... bd) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), target);
		if (bd != null && bd.length > 0) {
			intent.putExtras(bd[0]);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}

	private void replace(int targetFrame,BaseFragment fragment) {
		getChildFragmentManager().beginTransaction().
				replace(targetFrame, fragment).
				commit();
	}

}
