package com.example.filemanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filemanagement.databinding.ActivityFileViewerBinding
import java.io.File

class FileViewerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent.getStringExtra("path") ?: return
        val file = File(path)

        if (file.exists() && file.extension == "txt") {
            binding.textView.text = file.readText()
        } else {
            binding.textView.text = "Cannot open this file."
        }
    }
}
