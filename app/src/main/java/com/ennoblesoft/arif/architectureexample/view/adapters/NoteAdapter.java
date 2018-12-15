package com.ennoblesoft.arif.architectureexample.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ennoblesoft.arif.architectureexample.R;
import com.ennoblesoft.arif.architectureexample.model.room.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {
    private OnItemClickListener mListener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
            return oldNote.getId() == newNote.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
            return oldNote.getTitle().equals(newNote.getTitle()) && oldNote.getDescription().equals(newNote.getDescription()) && oldNote.getPriority() == newNote.getPriority();
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        Note note = getItem(i);
        noteViewHolder.setNote(note.getTitle(), note.getDescription(), note.getPriority());
    }

    public Note NotePositionAt(int position) {
        return getItem(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription, tvPriority;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPriority = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClicked(getItem(position));
                    }
                }
            });
        }

        private void setNote(String title, String description, int priority) {
            tvTitle.setText(title);
            tvDescription.setText(description);
            tvPriority.setText(String.valueOf(priority));
        }
    }

    public interface OnItemClickListener {
        void OnItemClicked(Note note);
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
