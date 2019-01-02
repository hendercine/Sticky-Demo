/*
 * Created by James Henderson on 2018
 * Copyright (c) Hendercine Productions and James Henderson 2018.
 * All rights reserved.
 *
 * Last modified 12/28/18 2:28 PM
 */

package com.mobileprogramming.android.stickydemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArFragment mArFragment;
    PointerDrawable mPointer = new PointerDrawable();
    private boolean mIsTracking; // ARCore is tracking
    private boolean mIsHitting; // User is looking at a plane.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar
                .make(view, "Nothing here yet...", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        mArFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
            mArFragment
                    .getArSceneView()
                    .getScene()
                    .addOnUpdateListener(frameTime -> mArFragment.onUpdate(frameTime));
        onUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Update the tracking state. If not tracking, remove pointer until
    // restored. If Tracking, check for plane hit and enable pointer.
    private void onUpdate() {
        boolean trackingChanged = updateTracking();
        View contentView = findViewById(android.R.id.content);
        if (trackingChanged) {
            if (mIsTracking) {
                contentView.getOverlay().add(mPointer);
            } else {
                contentView.getOverlay().remove(mPointer);
            }
            contentView.invalidate();
        }

        if (mIsTracking) {
            boolean hitTestChanged = updateHitTest();
            if (hitTestChanged) {
                mPointer.setEnabled(mIsHitting);
                contentView.invalidate();
            }
        }
    }

    // Use ARCore camera state to check if state has changed
    private boolean updateTracking() {
        Frame frame = mArFragment.getArSceneView().getArFrame();
        boolean wasTracking = mIsTracking;
        mIsTracking = frame != null &&
                frame.getCamera().getTrackingState() == TrackingState.TRACKING;
        return mIsTracking != wasTracking;
    }

    // Detect hits
    private boolean updateHitTest() {
        Frame frame = mArFragment.getArSceneView().getArFrame();
        android.graphics.Point pt = getScreenCenter();
        List<HitResult> hits;
        boolean wasHitting = mIsHitting;
        mIsHitting = false;
        if (frame != null) {
            hits = frame.hitTest(pt.x, pt.y);
            for (HitResult hit : hits) {
                Trackable trackable = hit.getTrackable();
                if (trackable instanceof Plane &&
                        ((Plane) trackable).isPoseInPolygon(hit.getHitPose())) {
                    mIsHitting = true;
                    break;
                }
            }
        }
        return wasHitting != mIsHitting;
    }

    private android.graphics.Point getScreenCenter() {
        View vw = findViewById(android.R.id.content);
        return new android.graphics.Point(vw.getWidth()/2, vw.getHeight()/2);
    }
}
