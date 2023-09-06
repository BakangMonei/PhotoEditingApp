package com.neizathedev.photovideoapplimegig.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.neizathedev.photovideoapplimegig.Model.UserAccount;
import com.neizathedev.photovideoapplimegig.R;

public class UsersAdapter extends FirestoreRecyclerAdapter<UserAccount, UsersAdapter.personsViewholder> {
    public static final int MULTIPLE_PERMISSIONS = 101;

    public UsersAdapter(@NonNull FirestoreRecyclerOptions<UserAccount> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull UserAccount model) {
        // Bind the data to the views
        holder.textEmail.setText(model.getEmail());
        holder.textUsername.setText(model.getUsername());
        holder.textFirstName.setText(model.getFirstName());
        holder.textLastName.setText(model.getLastName());
        holder.textOmang.setText(model.getOmang());
        holder.textPostalAddress.setText(model.getPostalAddress());
        holder.textPhoneNumber.setText(model.getPhone());
        holder.textPhysicalAddress.setText(model.getPhysicalAddress());
        holder.textGender.setText(model.getGender());
        holder.textCountry.setText(model.getCountry());
        holder.textOccupation.setText(model.getOccupation());

        final String phoneNumber = model.getPhone();
        final String email = model.getEmail();

        holder.buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phoneNumber, view);
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord(view, email);
            }
        });

        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall(phoneNumber, view);
            }
        });
    }

    private void sendSMS(final String phoneNumber, final View view) {
        // Request the SEND_SMS permission
        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.SEND_SMS}, MULTIPLE_PERMISSIONS);
            return;
        }

        // Get the default SMS manager
        SmsManager smsManager = SmsManager.getDefault();

        try {
            // Define the SMS message content
            String message = "Hi There";

            // Send the SMS message
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

            // If the SMS is sent successfully, delete the record
            deleteRecordSMS(view, phoneNumber);

            // Display a toast message
            Toast.makeText(view.getContext(), "SMS sent and record deleted successfully!", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            // SMS sending permission is not granted
            e.printStackTrace();
            Toast.makeText(view.getContext(), "SMS sending permission denied!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Failed to send the SMS
            e.printStackTrace();
            Toast.makeText(view.getContext(), "Failed to send SMS!", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete Button
    private void deleteRecord(View view, String email) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("user").whereEqualTo("email", email);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (UserAccount user : task.getResult().toObjects(UserAccount.class)) {
                    firestore.collection("user").document(user.getEmail()).delete();
                }
                Toast.makeText(view.getContext(), "Record deleted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Failed to delete record!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Delete for 'APPROVED BUTTON'
    private void deleteRecordSMS(View view, String phone) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Query query = firestore.collection("user").whereEqualTo("phone", phone);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (UserAccount user : task.getResult().toObjects(UserAccount.class)) {
                    firestore.collection("user").document(user.getEmail()).delete();
                }
                Toast.makeText(view.getContext(), "Record deleted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Failed to delete record!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeCall(String phoneNumber, View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            view.getContext().startActivity(intent);
        } else {
            ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.CALL_PHONE}, MULTIPLE_PERMISSIONS);
        }
    }

    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userentry, parent, false);
        return new UsersAdapter.personsViewholder(view);
    }

    class personsViewholder extends RecyclerView.ViewHolder {
        TextView textEmail, textUsername, textFirstName, textLastName, textOmang, textPhoneNumber, textPhysicalAddress, textGender, textPostalAddress, textCountry, textOccupation;
        Button buttonApprove, buttonDelete, buttonCall, sendEmailBtn;

        public personsViewholder(@NonNull View itemView) {
            super(itemView);
            textEmail = itemView.findViewById(R.id.textEmail);
            textUsername = itemView.findViewById(R.id.textUsername);
            textFirstName = itemView.findViewById(R.id.textFirstName);
            textLastName = itemView.findViewById(R.id.textLastName);
            textOmang = itemView.findViewById(R.id.textOmang);
            textPhoneNumber = itemView.findViewById(R.id.textPhoneNumber);
            textPhysicalAddress = itemView.findViewById(R.id.textPhysicalAddress);
            textGender = itemView.findViewById(R.id.textGender);
            textPostalAddress = itemView.findViewById(R.id.textPostalAddress);
            textCountry = itemView.findViewById(R.id.textCountry);
            textOccupation = itemView.findViewById(R.id.textOccupation);

            buttonApprove = itemView.findViewById(R.id.buttonApprove);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonCall = itemView.findViewById(R.id.buttonCall);
            sendEmailBtn = itemView.findViewById(R.id.sendEmailBtn);
        }
    }
}
