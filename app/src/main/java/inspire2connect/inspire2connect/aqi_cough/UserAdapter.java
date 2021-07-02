package inspire2connect.inspire2connect.aqi_cough;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import inspire2connect.inspire2connect.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private OnItemClickListener userListener;
    private ArrayList<UserDetails> userArray;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImageView;
        public TextView nameTextView;
        public TextView rollTextView;

        public UserViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.userImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            rollTextView = itemView.findViewById(R.id.rollTextView);

            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int itemIndex = getAdapterPosition();
                    if(itemIndex != RecyclerView.NO_POSITION){
                        listener.onItemClick(itemIndex);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        userListener = listener;
    }

    public UserAdapter(ArrayList<UserDetails> userArray) {
        this.userArray = userArray;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        UserViewHolder studViewHold = new UserViewHolder(studView, userListener);
        return studViewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserDetails currentUser = userArray.get(position);
        holder.userImageView.setImageResource(currentUser.getUserImage());
        holder.nameTextView.setText(currentUser.getUserName());
        holder.rollTextView.setText(currentUser.getUserRollNumber() + " | " + currentUser.getUserBranch() + "");
    }

    @Override
    public int getItemCount() {
        return userArray.size();
    }

    public ArrayList<UserDetails> getUserArray() {
        return userArray;
    }

    public void setUserArray(ArrayList<UserDetails> userArray) {
        this.userArray = userArray;
    }

}