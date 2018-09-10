package app.bowling.bowlingapp.bowling.core.customviews;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class BowlingDialog extends BottomSheetDialog {

    public BowlingDialog(@NonNull Context context) {
        super(context);
    }

    public BowlingDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BowlingDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
