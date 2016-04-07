package com.example.deeknut.buzzmovie;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.deeknut.buzzmovie.models.User;

/**
 * Displays all the users for the application to the admin, and provides options for
 * blocking/unblocking and locking/unlocking the user.
 */
public class BMManageUsersActivity extends BMModelActivity {

    /**
     *
     */
    private ArrayAdapter<Object> userAdapter;
    /**
     *
     */
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmmanage_users);
        userAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                getModel().listUsers().toArray());

        final ListView accountsListView = (ListView) findViewById(R.id.accounts_listView);
        accountsListView.setAdapter(userAdapter);
        accountsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                selectedItem = position;
            }
        });

        //TODO reload the user's toString into the adapter
        final Button banAccountButton = (Button) findViewById(R.id.button_ban_unban_account);
        banAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User u = (User) accountsListView.getItemAtPosition(selectedItem);
                u.setIsBanned(!u.isBanned());
                userAdapter.notifyDataSetChanged();
            }
        });

        final Button unlockAccountButton = (Button) findViewById(R.id.button_unlock_account);
        unlockAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User u = (User) accountsListView.getItemAtPosition(selectedItem);
                u.unlock();
                userAdapter.notifyDataSetChanged();
            }
        });

    }
}
