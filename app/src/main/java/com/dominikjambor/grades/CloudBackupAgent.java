package com.dominikjambor.grades;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

/**
 * Created by j-dom on 9/5/2015.
 */
public class CloudBackupAgent extends BackupAgentHelper {
    // The names of the SharedPreferences groups that the application maintains.  These

    // An arbitrary string used within the BackupAgentHelper implementation to
    // identify the SharedPreferenceBackupHelper's data.

    // Simply allocate a helper and install it
    public void onCreate() {
        SharedPreferencesBackupHelper helper =
                new SharedPreferencesBackupHelper(this,"com.dominikjambor.grades.data");
        addHelper("gradesdata", helper);
    }
}