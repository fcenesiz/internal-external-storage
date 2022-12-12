package com.fcenesiz.external_storage

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import com.fcenesiz.external_storage.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : RuntimePermissionActivity() {

    lateinit var binding: ActivityMainBinding

    private companion object {
        const val EXTERNAL_STORAGE_PERMISSIONS = 50
        var IGNORED = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveExternalPrivate.setOnClickListener { if (!IGNORED) saveToExternalPrivate() }
        binding.buttonRetrieveExternalPrivate.setOnClickListener { if (!IGNORED) retrieveFromExternalPrivate() }
        binding.buttonSaveExternalPublic.setOnClickListener { if (!IGNORED) saveToExternalPublic() }
        binding.buttonRetrieveExternalPublic.setOnClickListener { if (!IGNORED) retrieveFromExternalPublic() }

        askPermissions()
    }

    fun askPermissions() {
        val askedPermissions: Array<String?> = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        super.askPermission(askedPermissions, EXTERNAL_STORAGE_PERMISSIONS)
    }

    override fun permissionGranted(requestCode: Int) {
        if (requestCode == EXTERNAL_STORAGE_PERMISSIONS) {
            IGNORED = false
        }
    }

    fun saveToExternalPrivate() {

        val data = binding.editTextExternalPrivateData.text.toString()
        val fileDir = getExternalFilesDir("myFolder")
        val file = File(fileDir, "myExternalPrivate.txt")
        this.saveToFile(file, data)
    }

    fun retrieveFromExternalPrivate() {

        val fileDir = getExternalFilesDir("myFolder")
        val file = File(fileDir, "myExternalPrivate.txt")

        retrieveFileContent(file, binding.textViewExternalPrivateContent)
    }

    fun saveToExternalPublic() {
        if (!isExternalStorageWritable())
            return
        val data = binding.editTextExternalPublicData.text.toString()
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, "myExternalPublic.txt")

        saveToFile(file, data)
    }

    fun retrieveFromExternalPublic() {
        if (!isExternalStorageReadable())
            return
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(filePath, "myExternalPublic.txt")
        retrieveFileContent(file, binding.textViewExternalPublicContent)
    }

    fun saveToFile(file: File, data: String) {

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            fos.write(data.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
                Toast.makeText(this, "Saved to file", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun retrieveFileContent(file: File, textView: TextView) {

        var fis: FileInputStream? = null
        val buffer = StringBuffer()
        try {
            fis = FileInputStream(file)
            var read: Int = 0
            while (read != -1) {
                read = fis.read()
                buffer.append(read.toChar())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fis?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        textView.text = buffer
    }

    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    fun isExternalStorageReadable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
    }
}