package com.fcenesiz.internalexternalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fcenesiz.internalexternalstorage.databinding.ActivityShowStorageBinding
import java.io.FileInputStream

class ShowStorageActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonShowFile.setOnClickListener { showFileContent() }
    }

    fun showFileContent(){
        val filename = binding.editTextTextFileName.text.toString()
        var fis : FileInputStream? = null
        val buffer = StringBuffer()
        try {
            fis = openFileInput(filename)
            var read : Int = 0
            while (read != -1){
                read = fis.read()
                buffer.append(read.toChar())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            try {
                fis?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.textFileContent.text = buffer
    }
}