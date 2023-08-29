package com.example.android_storage_file_read

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity Log"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: Build.VERSION.SDK_INT : ${Build.VERSION.SDK_INT}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }
        } else {
            checkStoragePermissions()
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            try {
                ReadDeviceFile.apply {
                    val files = getFiles("/sdcard/Download")
                    Log.d(TAG, "onCreate: files : $files")
                    val file = getFileOne("/sdcard/Download", "readFile")
                    val result = readTextFile(file)
                    Log.d(TAG, "onCreate: result : $result")
                }
            } catch (e: Exception) {
                Log.e(TAG, "read error",e)
            }
        }

    }


    private fun checkStoragePermissions() {

        try {
            val permission1 = ContextCompat.checkSelfPermission(
                this,
                WRITE_EXTERNAL_STORAGE
            )

            val permission2 = ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            )

            if (permission1 == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
                        1000
                    )
                }
                return
            }
        } catch (e: Exception) {
            Log.d(TAG, "checkStoragePermissions error : $e")
        }

    }

    // 권한 체크 이후로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grandResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults)
        if (requestCode == 1000) {
            var checkResult = true

            for (result in grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }
        }
    }


}