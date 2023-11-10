package capps.jotpad.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import capps.jotpad.R
import capps.jotpad.databinding.NoteItemBinding
import capps.jotpad.others.Object
import capps.jotpad.room.Note
import capps.jotpad.room.NotePriority
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class NoteAdapter(private val context: Context) :
    RecyclerView.Adapter<NoteViewHolder>() {

    private val notes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<NoteItemBinding>(
            layoutInflater, R.layout.note_item, parent, false
        )

        return NoteViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(noteList: List<Note>) {
        notes.clear()
        notes.addAll(noteList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(context, notes[position])
    }
}

class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, note: Note) {
        val drawable = binding.tag!!.background as GradientDrawable
        drawable.mutate()

        val isColorBright = Object.isColorBright(note.color)
        if (isColorBright) {
            binding.title.setTextColor(Color.BLACK)
            binding.body.setTextColor(Color.BLACK)
            binding.date.setTextColor(Color.BLACK)
            Object.textViewDrawableColor(
                binding.date, 0, ContextCompat.getColor(context, R.color.black)
            )
        } else {
            binding.title.setTextColor(Color.WHITE)
            binding.body.setTextColor(Color.WHITE)
            binding.date.setTextColor(Color.WHITE)
            Object.textViewDrawableColor(
                binding.date, 0, ContextCompat.getColor(context, R.color.white)
            )
        }

        when (note.priority) {
            NotePriority.LOW -> {
                drawable.setColor(Color.parseColor(NotePriority.LOW.colorCode))
            }

            NotePriority.Medium -> {
                drawable.setColor(Color.parseColor(NotePriority.Medium.colorCode))
            }

            NotePriority.High -> {
                drawable.setColor(Color.parseColor(NotePriority.High.colorCode))
            }
        }

        if (note.title.isNotBlank()) {
            binding.title.text = note.title
            binding.body.text = note.body
        } else {
            val extraction = extractFirstSentence(note.body)
            binding.title.text = extraction.first
            binding.body.text = extraction.second
        }

        binding.relativeLayout.setBackgroundColor(Color.parseColor(note.color))
        binding.date.text = calculatedDate(note.lastUpdated)

        binding.relativeLayout.setOnClickListener {
            val bundle = Bundle()
            val noteJsonString = Object.MyGsonUtil().getGsonParser().toJson(note)

            bundle.putBoolean("editing", true)
            bundle.putString("note", noteJsonString)
            binding.root.findNavController()
                .navigate(R.id.action_homeFragment_to_editFragment, bundle)
        }
    }

    private fun calculatedDate(time: Long): CharSequence {
        val timeDiff: String
        val currentTime = Calendar.getInstance().timeInMillis
        val timeDifference = currentTime - time

        when {
            timeDifference > 604800000 -> {
                //>week
                val simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                timeDiff = simpleDateFormat.format(time)
            }

            timeDifference > 86400000 -> {
                //day
                timeDiff = StringBuilder(
                    (TimeUnit.MILLISECONDS.toDays(timeDifference)).toString()
                ).append("d ago").toString()
            }

            timeDifference > 3600000 -> {
                //hour
                timeDiff = StringBuilder(
                    (TimeUnit.MILLISECONDS.toHours(timeDifference)).toString()
                ).append("h ago").toString()
            }

            timeDifference > 60000 -> {
                //minutes
                timeDiff = StringBuilder(
                    (TimeUnit.MILLISECONDS.toMinutes(timeDifference)).toString()
                ).append("m ago").toString()
            }

            timeDifference > 0 -> {
                timeDiff = "just now"
            }

            else -> {
                throw IllegalArgumentException("Unknown Time")
            }
        }

        return timeDiff
    }

    private fun extractFirstSentence(input: String): Pair<String, String> {
        val sentenceEnd = "[.!?\\,]| {2,}"
        val sentenceRegex = "(.*?)(?=$sentenceEnd)".toRegex()

        val matchResult = sentenceRegex.find(input)
        val firstSentence = matchResult?.value ?: input

        val remainingText = matchResult?.let { input.substring(it.range.last + 1).trim() } ?: ""

        return Pair(firstSentence, remainingText)
    }
}