package chvck.colourMate.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;

public class Utilities {

	public static void setupActivity(Context contextParam, Class<?> classParam, HashMap<String, ArrayList<?>> extras) {
		Intent intent = new Intent();
		intent.setClass(contextParam, classParam);
		for (HashMap.Entry<String, ArrayList<?>> entry : extras.entrySet()) {
		    intent.putExtra(entry.getKey(), entry.getValue());
		}
		contextParam.startActivity(intent);
	}
}
