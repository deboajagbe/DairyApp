package unicornheight.dairyapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ItemViewHolder> {

    private List<DiaryEntry> entries;
    private AdapterClickListener clickListener;

    public CustomAdapter(List<DiaryEntry> entries,
                         AdapterClickListener clickListener) {
        this.clickListener = clickListener;
        replaceData(entries);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(entries.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (entries == null) {
            return 0;
        }
        return entries.size();
    }

    public void replaceData(List<DiaryEntry> entries) {
        this.entries = entries;
        this.notifyDataSetChanged();
    }

    public interface AdapterClickListener {
        void onEntryClicked(DiaryEntry entry);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView detailTextView;
        TextView messageTextView;


        ItemViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            detailTextView = itemView.findViewById(R.id.details);
            messageTextView = itemView.findViewById(R.id.message);
        }

        void bind(final DiaryEntry entry, final int position) {
            titleTextView.setText(entry.getTitle());
            detailTextView.setText(entry.getDetails());
            messageTextView.setText(entry.getSomeMessage());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onEntryClicked(entries.get(position));
                }
            });
        }
    }
}