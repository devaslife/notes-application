package com.example.note.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.Models.Notes;
import com.example.note.NotesClickListener;
import com.example.note.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> list;
    NotesClickListener listener;
    private int lastPos = -1;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override

    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
//        Notes notes = list.get(position);
        holder.textTitle.setText(list.get(position).getTitle());
        holder.textTitle.setSelected(true); // to make horizontal scroll

        holder.textNotes.setText(list.get(position).getNotes());

        holder.textDate.setText(list.get(position).getDate());
        holder.textDate.setSelected(true); // to make horizontal scroll

        setAnimation(holder.itemView, position);

        if (list.get(position).isPinned()) { // i need to test the image pin
            holder.imgPin.setImageResource(R.drawable.pin);
        } else {
            holder.imgPin.setImageResource(0);
        }

        int color_code = getRandomColor();

        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onclick(list.get(holder.getAdapterPosition()));

            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor() { // this method to add the color and refresh color
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());

        return colorCode.get(random_color);
    }


    private void setAnimation(View itemView, int position) { // this method to make animation
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    CardView notes_container;
    TextView textTitle, textNotes, textDate;
    ImageView imgPin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notes_container = itemView.findViewById(R.id.notes_container);
        textTitle = itemView.findViewById(R.id.textView_title);
        textNotes = itemView.findViewById(R.id.textView_notes);
        textDate = itemView.findViewById(R.id.textView_date);
        imgPin = itemView.findViewById(R.id.imageView_pin);
    }

//    public void bind(Notes notes) {
//        textTitle.setText(notes.getTitle());
//        textNotes.setText(notes.getNotes());
//        textDate.setText(notes.getDate());
//
//    }
}
