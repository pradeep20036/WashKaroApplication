package inspire2connect.inspire2connect.NlpChatbot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import inspire2connect.inspire2connect.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.RecycleViewholder>
{
    Context context;
    ArrayList<MessageClass> messageClasses ;
    private int USER_LAYOUT = 0;
    private int BOT_LAYOUT = 1;

    public MessageAdapter(Context context, ArrayList<MessageClass> messageClasses) {
        this.context = context;
        this.messageClasses = messageClasses;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public RecycleViewholder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        if(viewType == USER_LAYOUT) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.user_message_box, parent, false);
            return new RecycleViewholder(view);
        }
        else{
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.bot_message_box, parent, false);
            return new RecycleViewholder(view);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MessageAdapter.RecycleViewholder holder, int position) {
        MessageClass currentmessage = messageClasses.get(position);
        FrameLayout frameLayout = getUserlayout();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa",
                Locale.ENGLISH);
        String time = dateFormat.format(date);

        if(currentmessage.sender == 0)
        {
            holder.message.setText(currentmessage.message);
            holder.time.setText(time.toString());
        }
        else if(currentmessage.sender == 1)
        {
            holder.message.setText(currentmessage.message);
            holder.time.setText(time.toString());

        }



    }

    @Override
    public int getItemCount() {
        return messageClasses.size();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        MessageClass msg = messageClasses.get(position);
        if(msg.sender == USER_LAYOUT)
        {
            return USER_LAYOUT;
        }
        else
        {
            return BOT_LAYOUT;
        }
    }

    public FrameLayout getUserlayout()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        FrameLayout frameLayout = (FrameLayout) layoutInflater.inflate(R.layout.user_message_box,null,false);
        return frameLayout;
    }
    public FrameLayout getBotlayout()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        FrameLayout frameLayout = (FrameLayout) layoutInflater.inflate(R.layout.bot_message_box,null,false);
        return frameLayout;
    }

    public class RecycleViewholder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView message,time;
        public RecycleViewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            message = itemView.findViewById(R.id.chat_msg);
            time = itemView.findViewById(R.id.message_time);
        }
    }
}
