package com.fcenesiz.internalexternalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fcenesiz.internalexternalstorage.databinding.ActivityMainBinding
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSaveToFile.setOnClickListener { saveToFile() }
        binding.buttonInternalStorageFilePath.setOnClickListener { showInternalStorageFilePath() }
        binding.buttonShowFiles.setOnClickListener { showFiles() }
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

    fun showInternalStorageFilePath(){

    }

    fun showFiles(){

    }

    fun deleteFileByName(){

    }

}