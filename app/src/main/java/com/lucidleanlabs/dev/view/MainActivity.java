package com.lucidleanlabs.dev.view;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Display display = getWindowManager().getDefaultDisplay();
        final Drawable droid = ContextCompat.getDrawable(this, R.mipmap.ic_fav);
        final Drawable droid2 = ContextCompat.getDrawable(this, R.mipmap.ic_menu_share);
        final Drawable droid3 = ContextCompat.getDrawable(this, R.mipmap.ic_3d);
        final Drawable droid4 = ContextCompat.getDrawable(this, R.mipmap.ic_download);

        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        final Rect droidTarget2 = new Rect(2, 2, droid2.getIntrinsicWidth() * 2, droid2.getIntrinsicHeight() * 2);
        final Rect droidTarget3 = new Rect(2, 4, droid3.getIntrinsicWidth() * 2, droid3.getIntrinsicHeight() * 2);
        final Rect droidTarget4 = new Rect(0, 0, droid4.getIntrinsicWidth() * 2, droid4.getIntrinsicHeight() * 2);

        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
        droidTarget2.offset(display.getWidth() / 2, display.getHeight() / 2);
        droidTarget3.offset(display.getWidth() / 2, display.getHeight() / 2);
        droidTarget4.offset(display.getWidth() / 2, display.getHeight() / 2);


        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forBounds(droidTarget2, "welcome", "hello how are u man")
                                .cancelable(false)
                                .icon(droid2)
                                .id(1),
                        TapTarget.forBounds(droidTarget3, "nice to see u", "yup it is true")
                                .cancelable(false)
                                .icon(droid3)
                                .id(2),
                        TapTarget.forBounds(droidTarget4, "qwertyuiop", "asdfghjkl")
                                .cancelable(false)
                                .icon(droid4)
                                .id(3)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });


        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.article_fav_icon), "Hello, world!")
                .cancelable(false)
                .drawShadow(true)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                // .. which evidently starts the sequence we defined earlier
                sequence.start();
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
            }
        });


    }
}
