package com.neizathedev.photovideoapplimegig.Administrator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.neizathedev.photovideoapplimegig.AdministratorTools.AddAdminsActivity;
import com.neizathedev.photovideoapplimegig.R;
import com.neizathedev.photovideoapplimegig.AdministratorTools.ViewAdminsActivity;
import com.neizathedev.photovideoapplimegig.AdministratorTools.ViewClientsActivity;
import com.neizathedev.photovideoapplimegig.AdministratorTools.ViewPaidClientsActivity;

public class AdminDashBoardActivity extends AppCompatActivity {

    Button viewClientsButton, paidClientsButton, viewAdminsButton, addAdminsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);

        viewClientsButton = (Button) findViewById(R.id.viewClientsButton); // Done
        paidClientsButton = (Button) findViewById(R.id.paidClientsButton); // Done
        viewAdminsButton = (Button) findViewById(R.id.viewAdminsButton); // Done
        addAdminsButton = (Button) findViewById(R.id.addAdminsButton); // Done

        viewClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAllClients();
            }
        });

        paidClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPaidClients();
            }
        });

        viewAdminsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAllAdmin();
            }
        });

        addAdminsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAdmin();
            }
        });
    }

    public void goToAllAdmin(){
        Intent a = new Intent(AdminDashBoardActivity.this, ViewAdminsActivity.class);
        Toast.makeText(AdminDashBoardActivity.this, "View All Administrators", Toast.LENGTH_SHORT).show();
        startActivity(a);
    }

    public void goToAddAdmin(){
        Intent d = new Intent(AdminDashBoardActivity.this, AddAdminsActivity.class);
        Toast.makeText(AdminDashBoardActivity.this, "Add new admin here", Toast.LENGTH_SHORT).show();
        startActivity(d);
    }

    public void goToAllClients(){
        Intent b = new Intent(AdminDashBoardActivity.this, ViewClientsActivity.class);
        Toast.makeText(AdminDashBoardActivity.this, "View All Clients Here", Toast.LENGTH_SHORT).show();
        startActivity(b);
    }

    public void goToPaidClients(){
        Intent c = new Intent(AdminDashBoardActivity.this, ViewPaidClientsActivity.class);
        Toast.makeText(AdminDashBoardActivity.this, "View Paid Clients Here", Toast.LENGTH_SHORT).show();
        startActivity(c);
    }


}