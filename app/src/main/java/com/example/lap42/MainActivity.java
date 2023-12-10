package com.example.lap42;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    private List<Course> courseList;
    private Button addButton;
    private String imagePath = ""; // Variable to store the selected image path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseList = new ArrayList<>();

        courseList.add(new Course("CMP354", "Lập trình di động", "Nguyen Huy", "didong"));

        adapter = new CourseAdapter(this, courseList);
        recyclerView.setAdapter(adapter);

        // Button initialization
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the dialog to add a new Course
                addNewCourseDialog();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // Move onActivityResult outside of the addNewCourseDialog method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the selected image path from the gallery
            imagePath = data.getData().toString();
        }
    }

    private void addNewCourseDialog() {
        // AlertDialog creation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Course");

        // Use LayoutInflater to create the layout for the dialog
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_course, null);
        builder.setView(view);

        // Initialize EditText fields in the layout
        final EditText codeEditText = view.findViewById(R.id.editTextCode);
        final EditText nameEditText = view.findViewById(R.id.editTextName);
        final EditText lecturerEditText = view.findViewById(R.id.editTextLecturer);
        final ImageView imageCourseImageView = view.findViewById(R.id.editImageCourse);
        final Button pickImageButton = view.findViewById(R.id.btnPickImage);

        // Set the click event for the ImageView and Button to open the gallery
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Set the "Add" button and handle the event when it is clicked
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Get data from EditText fields
                String code = codeEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String lecturer = lecturerEditText.getText().toString();

                // Create a new Course and add it to the list
                Course newCourse = new Course(code, name, lecturer, imagePath);
                courseList.add(newCourse);

                // Update the Adapter
                adapter.notifyDataSetChanged();
            }
        });

        // Set the "Cancel" button
        builder.setNegativeButton("Cancel", null);

        // Show the dialog
        builder.create().show();
    }
}
