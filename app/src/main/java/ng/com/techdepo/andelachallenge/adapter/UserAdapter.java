package ng.com.techdepo.andelachallenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ng.com.techdepo.andelachallenge.R;
import ng.com.techdepo.andelachallenge.UserDetails;
import ng.com.techdepo.andelachallenge.model.User;

/**
 * Created by ESIDEM jnr on 3/5/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserAdapter(List<User> userList) {

        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_item_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Picasso.with(context).load(userList.get(position).getAvatarUrl()).into(holder.avatarImageView);
        holder.usernameTextView.setText(userList.get(position).getLogin());
        holder.locationView.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView avatarImageView;
        TextView usernameTextView;
        TextView locationView;


        public UserViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            avatarImageView = (ImageView) itemView.findViewById(R.id.imageview_avatar);
            usernameTextView = (TextView) itemView.findViewById(R.id.textview_username);
            locationView = (TextView) itemView.findViewById(R.id.location_view);


        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, UserDetails.class);
            User user = userList.get(getLayoutPosition());
            intent.putExtra("user", user);
            context.startActivity(intent);
        }
    }
}
