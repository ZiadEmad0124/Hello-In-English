package com.ziad_emad_dev.hello_in_english.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ziad_emad_dev.hello_in_english.R
import com.ziad_emad_dev.hello_in_english.database.Word
import com.ziad_emad_dev.hello_in_english.database.WordDB

class WordAdapter(private val context: Context, private val words: List<Word>) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    class WordViewHolder(itemView: View) : ViewHolder(itemView) {
        val englishWord: TextView = itemView.findViewById(R.id.englishTextView)
        val arabicWord: TextView = itemView.findViewById(R.id.arabicTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]

        holder.englishWord.text = word.englishWord
        holder.arabicWord.text = word.arabicWord

        holder.itemView.setOnLongClickListener {
            showAlertDialog(holder, word.englishWord)
            false
        }
    }

    private fun showAlertDialog(holder: WordViewHolder, englishWord: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Are you sure?")
            .setMessage("If you delete ${englishWord}, you can't use it again")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->

                val wordDB = WordDB(context)
                wordDB.deleteWord(englishWord)
                holder.itemView.findNavController()
                    .navigate(R.id.action_dictionaryFragment_to_mainFragment)
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { materialAlertDialogBuilder, _ ->
                materialAlertDialogBuilder.dismiss()
            }
            .show()
    }
}