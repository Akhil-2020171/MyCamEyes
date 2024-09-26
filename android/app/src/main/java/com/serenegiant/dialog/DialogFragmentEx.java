package com.serenegiant.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.serenegiant.utils.BuildCheck;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public abstract class DialogFragmentEx extends DialogFragment {
	private static final String TAG = DialogFragmentEx.class.getSimpleName();

	protected static final String ARGS_KEY_REQUEST_CODE = "requestCode";
	protected static final String ARGS_KEY_ID_TITLE = "title";
	protected static final String ARGS_KEY_ID_MESSAGE = "message";
	protected static final String ARGS_KEY_TAG = "tag";

	@Override
	public void onSaveInstanceState(@NonNull final Bundle outState) {
		super.onSaveInstanceState(outState);
		final Bundle args = getArguments();
		if (args != null) {
			outState.putAll(args);
		}
	}

//	@NonNull
//	protected Bundle requireArguments() throws IllegalStateException {
//		final Bundle args = getArguments();
//		if (args == null) {
//			throw new IllegalStateException();
//		}
//		return args;
//	}
//
	@Override
	public final void onStart() {
		super.onStart();
		if (BuildCheck.isAndroid7()) {
			internalOnResume();
		}
	}

	@Override
	public final void onResume() {
		super.onResume();
		if (!BuildCheck.isAndroid7()) {
			internalOnResume();
		}
	}

	@Override
	public final void onPause() {
		if (!BuildCheck.isAndroid7()) {
			internalOnPause();
		}
		super.onPause();
	}

	@Override
	public final void onStop() {
		if (BuildCheck.isAndroid7()) {
			internalOnPause();
		}
		super.onStop();
	}
	

	protected void internalOnResume() {
	}

	protected void internalOnPause() {
	}

	protected void popBackStack() {
		final Activity activity = getActivity();
		if ((activity == null) || activity.isFinishing()) return;
		try {
			getFragmentManager().popBackStack();
		} catch (final Exception e) {
			Log.w(TAG, e);
		}
	}
}