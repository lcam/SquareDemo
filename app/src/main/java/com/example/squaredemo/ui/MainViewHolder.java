package com.example.squaredemo.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.squaredemo.R;
import com.example.squaredemo.models.Employee;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.employee_uuid)
    TextView uuid;
    @BindView(R.id.employee_name)
    TextView name;
    @BindView(R.id.employee_phone_number)
    TextView phoneNumber;
    @BindView(R.id.employee_email)
    TextView email;
    @BindView(R.id.employee_bio)
    TextView bio;
    @BindView(R.id.employee_team)
    TextView team;
    @BindView(R.id.employee_type)
    TextView type;
    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Employee employee) {
        uuid.setText(employee.getUuid());
        name.setText(employee.getFullName());
        phoneNumber.setText(employee.getPhoneNumber());
        email.setText(employee.getEmailAddress());
        bio.setText(employee.getBiography());
        team.setText(employee.getTeam());
        type.setText(employee.getEmployeeType());

        Picasso picasso = Picasso.with(imageThumbnail.getContext());
        //picasso.setIndicatorsEnabled(true); //debug cache
        picasso.load(employee.getPhotoUrlSmall())
                .placeholder(R.drawable.square_placeholder)
                .error(R.drawable.square_placeholder)
                //.resize(500, 500)
                .fit()
                .centerCrop()
                .into(imageThumbnail);
    }
}
