package com.example.filemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanagement.R
import com.example.filemanagement.databinding.ItemFileBinding
import java.io.File

class FileAdapter(private val onClick: (File) -> Unit) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    private var files: List<File> = emptyList()

    fun submitList(newFiles: List<File>) {
        files = newFiles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files[position])
    }

    override fun getItemCount(): Int = files.size

    inner class FileViewHolder(private val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(file: File) {
            binding.fileName.text = file.name
            binding.fileIcon.setImageResource(if (file.isDirectory) R.drawable.ic_folder else R.drawable.ic_file)
            binding.root.setOnClickListener { onClick(file) }
        }
    }
}
