package inspire2connect.inspire2connect.aqi_cough;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import inspire2connect.inspire2connect.R;

public class TbUserLeader extends AppCompatActivity {
    private RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager userLayoutManager;
    private UserAdapter userAdapter;

    public String[] userNames = {"Raunak Mokhasi", "Vihaan Misra", "Mehrab Gill", "Arshita Bhatt"};
    public String[] userRolls = {"RANK 1 -> 309 Points (Influencer)", "RANK 2 -> 242 Points (Influencer)", "RANK 3 -> 201 Points", "RANK 4 -> 182 Points"};
    public String[] userBranches = {"Mumbai", "Noida", "Chandigarh", "Bangalore"};

    //TEMP TESTING DATA ^ ADD FIREBASE DATA INSTEAD OF THIS.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb_user_leader);
        ArrayList<UserDetails> usersArray = new ArrayList<>();
        try {
            if (getIntent().getExtras().getString("savedName") != null) {
                userNames = getIntent().getExtras().getStringArray("studNameArr");
                userRolls = getIntent().getExtras().getStringArray("studRollArr");
                userBranches = getIntent().getExtras().getStringArray("studBranchArr");
                int cPos = getIntent().getExtras().getInt("savedVal");
                userNames[cPos] = getIntent().getExtras().getString("savedName");
                userRolls[cPos] = getIntent().getExtras().getString("savedRoll");
                userBranches[cPos] = getIntent().getExtras().getString("savedBranch");
            }
        }
        catch (Exception e){
            Log.e("Intent Error", e.getMessage());
        }
        for(int i = 0; i < userNames.length; i++){
            String currName = userNames[i];
            String currRoll = userRolls[i];
            String currBranch = userBranches[i];
            usersArray.add(new UserDetails(currName, currRoll, currBranch, R.drawable.ic_usericon));
        }
        userRecyclerView = findViewById(R.id.userRecyclerView);
        userLayoutManager = new LinearLayoutManager(this);
        userAdapter = new UserAdapter(usersArray);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setAdapter(userAdapter);
        userRecyclerView.setLayoutManager(userLayoutManager);
        userAdapter.setOnItemClickListener((int position) -> {
            String posName = userNames[position];
            String posRoll = userRolls[position];
            String posBranch = userBranches[position];
            Intent userIntent = new Intent(TbUserLeader.this, UserInfoScreen.class);
            userIntent.putExtra("studName", posName);
            userIntent.putExtra("studRoll", posRoll);
            userIntent.putExtra("studBranch", posBranch);
            userIntent.putExtra("indVal", position);
            userIntent.putExtra("studNameArr", userNames);
            userIntent.putExtra("studRollArr", userRolls);
            userIntent.putExtra("studBranchArr", userBranches);
            startActivity(userIntent);
        });
    }

}