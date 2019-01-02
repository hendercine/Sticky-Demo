/*
 * Created by James Henderson on 2019
 * Copyright (c) Hendercine Productions and James Henderson 2019.
 * All rights reserved.
 *
 * Last modified 1/2/19 3:40 PM
 */

package com.mobileprogramming.android.stickydemo;

import android.Manifest;

import com.google.ar.sceneform.ux.ArFragment;

/**
 * StickyDemo created by jamesmobileprogramming on 1/2/19.
 */
public class WritingArFragment extends ArFragment {

    @Override
    public String[] getAdditionalPermissions() {
        String[] additionalPermissions = super.getAdditionalPermissions();
        int permissionLength = additionalPermissions != null ? additionalPermissions.length : 0;
        String[] permissions = new String[permissionLength + 1];
        permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (permissionLength > 0) {
            System.arraycopy(additionalPermissions, 0, permissions, 1, additionalPermissions.length);
        }
        return permissions;
    }

}
