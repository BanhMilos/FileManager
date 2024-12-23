package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filemanagement.FileViewerActivity
import com.example.filemanagement.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fileAdapter: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check and request permission
        requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)

        setupRecyclerView()

        val rootPath = intent.getStringExtra("path")
        val root = if (rootPath != null) File(rootPath) else Environment.getExternalStorageDirectory()
        loadFiles(root)
    }

    private fun setupRecyclerView() {
        fileAdapter = FileAdapter { file ->
            if (file.isDirectory) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("path", file.absolutePath)
                startActivity(intent)
            } else {
                if (file.extension == "txt") {
                    val intent = Intent(this, FileViewerActivity::class.java)
                    intent.putExtra("path", file.absolutePath)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Cannot open this file type", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = fileAdapter
    }

    private fun loadFiles(directory: File) {
        val files = directory.listFiles()
        if (files != null) {
            fileAdapter.submitList(files.toList())
        } else {
            Toast.makeText(this, "Cannot access this folder", Toast.LENGTH_SHORT).show()
        }
    }
}
