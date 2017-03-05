package ng.com.techdepo.andelachallenge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ng.com.techdepo.andelachallenge.adapter.UserAdapter;
import ng.com.techdepo.andelachallenge.adapter.UserDivider;
import ng.com.techdepo.andelachallenge.model.UserList;
import ng.com.techdepo.andelachallenge.service.RestApiBuilder;
import ng.com.techdepo.andelachallenge.service.RestApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Please Wait..." );
        mProgress.show();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_user_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchUsers();


    }

    private void fetchUsers() {
        String searchParams = "language:java location:lagos";
        RestApiService apiService = new RestApiBuilder().getService();
        Call<UserList> userListCall = apiService.getUserList(searchParams);
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    UserList userList = response.body();
                    setAdapterData(userList);
                    mProgress.dismiss();
                } else {
                    mProgress.dismiss();
                    Toast.makeText(MainActivity.this,
                            "Bad request",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                mProgress.dismiss();
                Toast.makeText(MainActivity.this,
                        "Request failed. Check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterData(UserList userList) {
        UserAdapter adapter = new UserAdapter(userList.getItems());
        mRecyclerView.addItemDecoration(new UserDivider());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
