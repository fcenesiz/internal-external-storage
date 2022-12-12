package com.fcenesiz.internalexternalstorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fcenesiz.internalexternalstorage.databinding.ActivityMainBinding
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveToFile.setOnClickListener { saveToFile() }
        binding.buttonShowFileContent.setOnClickListener { showFileContent() }
        binding.buttonInternalStorageFilePath.setOnClickListener { showInternalStorageFilePath() }
        binding.buttonInternalStorageFileList.setOnClickListener { showFileList() }
        binding.buttonDeleteFileByName.setOnClickListener { deleteFileByName() }
    }

    fun saveToFile(){
        val filename = binding.editTextTextFilename.text.toString()
        val data = binding.editTextTextMessage.text.toString()

        var fos : FileOutputStream? = null
        try {
            fos = openFileOutput(filename, MODE_PRIVATE)
            fos.write(data.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            try {
                fos?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun showFileContent(){
        startActivity(Intent(this, ShowStorageActivity::class.java))
    }

    fun showInternalStorageFilePath(){
        val filePath = "" + this.filesDir
        binding.textViewInternalStorageFilePath.text = filePath
    }

    fun showFileList(){
        val fileList = fileList()
        val builder = StringBuilder()
        fileList.forEach { builder.append(it).append(", ") }
        binding.textViewFileNames.text = builder
    }

    fun deleteFileByName(){
        val filename = binding.editTextFileNameToBeDeleted.text.toString()
        val deleted = deleteFile(filename)
        if (deleted) {
            Toast.makeText(this, "File Deleted", Toast.LENGTH_SHORT).show()
        }else
        {
            Toast.makeText(this, "File Not Found!", Toast.LENGTH_SHORT).show()
        }
    }

}